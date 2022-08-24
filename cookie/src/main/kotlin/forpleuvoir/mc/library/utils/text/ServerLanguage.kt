package forpleuvoir.mc.library.utils.text

import com.google.common.collect.Lists
import com.google.gson.JsonObject
import com.ibm.icu.lang.UCharacter
import com.ibm.icu.text.ArabicShaping
import com.ibm.icu.text.Bidi
import forpleuvoir.mc.cookie.config.CookieServerConfigs
import forpleuvoir.mc.cookie.util.logger
import forpleuvoir.mc.library.event.EventSubscriber
import forpleuvoir.mc.library.event.Subscriber
import forpleuvoir.mc.library.event.events.ModInitializerEvent
import forpleuvoir.mc.library.utils.getOr
import forpleuvoir.mc.library.utils.gson
import forpleuvoir.mc.library.utils.resources
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.locale.Language
import net.minecraft.network.chat.FormattedText
import net.minecraft.network.chat.SubStringSource
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.Resource
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.FormattedCharSequence
import net.minecraft.util.GsonHelper
import org.spongepowered.include.com.google.common.collect.Maps
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.regex.Pattern

/**
 * 服务端语言

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.text

 * 文件名 ServerLanguage

 * 创建时间 2022/8/24 16:36

 * @author forpleuvoir

 */
@EventSubscriber
object ServerLanguage : Language() {
	private val log = logger()
	private val UNSUPPORTED_FORMAT_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]")
	private var map: MutableMap<String, MutableMap<String, String>> = HashMap()
	private var languageRightToLft: MutableMap<String, Boolean> = HashMap()
	private var defaultRightToLeft: Boolean = false

	private val current: String
		get() {
			val c = CookieServerConfigs.Setting.language.getValue()
			return if (map.containsKey(c)) {
				return c
			} else "en_us"
		}

	@Subscriber
	fun onModInitializerEvent(event: ModInitializerEvent) {
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(object : SimpleSynchronousResourceReloadListener {
			override fun getFabricId(): ResourceLocation = resources(event.meta.id, "server_lang")

			override fun onResourceManagerReload(resourceManager: ResourceManager) {
				println("加载服务器语言")
				loadForm(resourceManager)
			}
		})
	}

	fun loadForm(resourceManager: ResourceManager) {
		map.clear()
		val languageName = CookieServerConfigs.Setting.language.getValue()
		val name = String.format(Locale.ROOT, "server_lang/%s.json", languageName)
		resourceManager.namespaces.forEach { nameSpace ->
			resourceManager
				.listResources("server_lang")
				{ it.path.endsWith(".json") }
				.forEach { (resourceLocation, resource) ->
					try {
						appendFrom(resourceLocation.path, resource)
					} catch (e: Exception) {
						log.error("Skipped language file: {}:{} ({})", nameSpace, name, e.toString())
					}
				}
		}
	}

	private fun appendFrom(languageName: String, resource: Resource) {
		val path = languageName.replace(".json", "").replace("server_lang/", "")
		try {
			resource.open().use {
				val json = gson.fromJson(InputStreamReader(it, StandardCharsets.UTF_8) as Reader, JsonObject::class.java)
				this.languageRightToLft[path] = json.getOr("rightToLft", false)
				val map = Maps.newHashMap<String, String>()

				json.entrySet().forEach { entry ->
					map[entry.key] =
						UNSUPPORTED_FORMAT_PATTERN.matcher(GsonHelper.convertToString(entry.value, entry.key)).replaceAll("%$1s")
				}
				if (this.map.containsKey(path)) {
					this.map[path]!!.putAll(map)
				} else {
					this.map[path] = map
				}
			}
		} catch (e: IOException) {
			log.error("Failed to load translations for {} from pack {}", path, resource.sourcePackId(), e)
		}
	}


	override fun getOrDefault(string: String): String {
		return if (map.containsKey(current)) map[current]!![string] ?: string
		else string
	}

	override fun has(string: String): Boolean = if (map.containsKey(current)) map[current]!!.containsKey(string) else false

	override fun isDefaultRightToLeft(): Boolean = defaultRightToLeft


	override fun getVisualOrder(formattedText: FormattedText): FormattedCharSequence = reorder(
		formattedText, languageRightToLft.getOrDefault(
			current, false
		)
	)


	private fun reorder(formattedText: FormattedText, bl: Boolean): FormattedCharSequence {
		val subStringSource = SubStringSource.create(formattedText, UCharacter::getMirror, this::shape)
		val bidi = Bidi(subStringSource.plainText, if (bl) 127 else 126)
		bidi.reorderingMode = 0
		val list = Lists.newArrayList<FormattedCharSequence>()
		val i = bidi.countRuns()
		for (j in 0 until i) {
			val bidiRun = bidi.getVisualRun(j)
			list.addAll(subStringSource.substring(bidiRun.start, bidiRun.length, bidiRun.isOddRun))
		}
		return FormattedCharSequence.composite(list)
	}

	private fun shape(string: String): String? {
		return try {
			ArabicShaping(8).shape(string)
		} catch (exception: java.lang.Exception) {
			string
		}
	}
}