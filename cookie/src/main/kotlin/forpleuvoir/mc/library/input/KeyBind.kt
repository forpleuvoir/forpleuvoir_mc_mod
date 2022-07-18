package forpleuvoir.mc.library.input

import com.google.common.collect.Lists
import com.google.gson.JsonElement
import com.mojang.blaze3d.platform.InputConstants
import forpleuvoir.mc.cookie.util.logger
import forpleuvoir.mc.library.api.Matchable
import forpleuvoir.mc.library.api.Notifiable
import forpleuvoir.mc.library.api.Resettable
import forpleuvoir.mc.library.api.serialization.JsonSerializer
import forpleuvoir.mc.library.input.KeyTriggerMode.*
import forpleuvoir.mc.library.utils.jsonArray
import forpleuvoir.mc.library.utils.jsonObject
import forpleuvoir.mc.library.utils.text.literal
import net.minecraft.client.renderer.texture.Tickable
import net.minecraft.network.chat.MutableComponent
import java.util.*

/**
 * 按键绑定

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.input

 * 文件名 KeyBind

 * 创建时间 2022/7/15 21:32

 * @author forpleuvoir

 */
class KeyBind(
	vararg keyCodes: Int,
	private val defaultSetting: KeyBindSetting = keyBindSetting(),
	var action: KeyBind.() -> Unit,
) : Tickable, Resettable, Notifiable<KeyBind>, Matchable, JsonSerializer {

	private val log = logger()

	val uuid: UUID = UUID.randomUUID()

	private val defaultKeys: LinkedHashSet<Int> = LinkedHashSet(keyCodes.toSet())

	var setting: KeyBindSetting = keyBindSetting().apply { copyOf(defaultSetting) }

	val keys: LinkedHashSet<Int> = LinkedHashSet(defaultKeys)

	var tickCounter: Long = 0
		private set(value) {
			field = value.coerceAtLeast(0)
		}

	var isPressed: Boolean = false
		private set

	/**
	 * 更新状态
	 * @param key Set<Int>
	 * @return Boolean 是否取之后的操作
	 */
	internal fun update(key: LinkedHashSet<Int>): Boolean {
		if (key.isEmpty()) {
			isPressed = false
			return false
		}
		var isMatched = true
		if (key.size == keys.size && keys.containsAll(key) && setting.ordered) {
			isMatched = false
			key.forEachIndexed { index, code ->
				isMatched = keys.indexOf(code) == index
			}
		}

		if (key.size == keys.size && keys.containsAll(key) && setting.environment.envMatch() && isMatched) {
			isPressed = true
			return setting.cancelFurtherProcess
		} else if (isPressed) return setting.cancelFurtherProcess
		isPressed = false
		return false
	}

	override fun tick() {
		if (isPressed) tickCounter++
		when (setting.triggerMode) {
			OnPress     -> {
				if (isPressed && tickCounter == 1L) action.invoke(this)
			}

			OnLongPress -> {
				if (isPressed && tickCounter == setting.longPressTime) action.invoke(this)
			}

			OnRelease   -> {
				if (tickCounter != 0L && !isPressed) action.invoke(this)
			}

			Pressed     -> {
				if (isPressed) action.invoke(this)
			}

			BOTH        -> {
				if (isPressed && tickCounter == 1L) action.invoke(this)
				else if (tickCounter != 0L && !isPressed) action.invoke(this)
			}
		}
		if (!isPressed) tickCounter = 0L
	}

	override val isDefault: Boolean
		get() = defaultKeys == keys && setting == defaultSetting

	override fun resetDefValue() {
		setting = defaultSetting
		setKey(*defaultKeys.toIntArray())
	}

	fun addKey(keyCode: Int): Boolean {
		if (keys.add(keyCode)) {
			onChanged()
			return true
		}
		return false
	}

	fun setKey(vararg keyCodes: Int) {
		keys.clear()
		keys.addAll(keyCodes.toSet())
		onChanged()
	}

	fun copyOf(target: KeyBind): Boolean {
		var valueChange = false
		if (this.keys != target.keys) {
			this.keys.clear()
			this.keys.addAll(target.keys)
			valueChange = true
		}
		if (this.setting.copyOf(target.setting)) {
			valueChange = true
		}
		if (this.action != target.action) {
			this.action = target.action
			valueChange = true
		}
		if (valueChange) onChanged()
		return valueChange
	}

	inline fun setting(setting: KeyBindSetting.() -> Unit): KeyBind {
		this.setting.setting()
		onChanged()
		return this
	}


	val asTexts: List<MutableComponent>
		get() {
			val list = LinkedList<MutableComponent>()
			keys.forEach {
				if (it > 8)
					list.addLast(literal("").append(InputConstants.getKey(it, 0).displayName))
				else
					list.addLast(literal("").append(InputConstants.Type.MOUSE.getOrCreate(it).displayName))
			}
			return list
		}

	val asTranslatableKey: List<String>
		get() {
			val list = LinkedList<String>()
			keys.forEach {
				if (it > 8)
					list.addLast(InputConstants.getKey(it, 0).name)
				else
					list.addLast(InputConstants.Type.MOUSE.getOrCreate(it).name)
			}
			return list
		}

	private val onChangedCallback: MutableList<KeyBind.() -> Unit> = Lists.newArrayList()

	override fun onChanged() {
		onChangedCallback.forEach { it() }
	}

	override fun subscribeChange(obj: Any, callback: KeyBind.() -> Unit) {
		onChangedCallback.add(callback)
	}

	override fun matched(regex: Regex): Boolean {
		return regex.run {
			asTexts.forEach { if (this.containsMatchIn(it.string)) return@run true }
			asTranslatableKey.forEach { if (this.containsMatchIn(it)) return@run true }
			setting matched regex
		}
	}

	override val serialization: JsonElement
		get() = jsonObject {
			"keys" at jsonArray(asTranslatableKey)
			"setting" at setting.serialization
		}

	override fun deserialize(serializedObject: JsonElement) {
		try {
			val jsonObject = serializedObject.asJsonObject
			keys.clear()
			jsonObject["keys"].asJsonArray.forEach {
				keys.add(InputConstants.getKey(it.asString).value)
			}
			setting.deserialize(jsonObject["setting"])
			onChanged()
		} catch (e: Exception) {
			keys.clear()
			setting = defaultSetting
			log.error(e)
		}
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as KeyBind

		if (setting != other.setting) return false
		if (keys != other.keys) return false

		return true
	}

	override fun hashCode(): Int {
		var result = setting.hashCode()
		result = 31 * result + keys.hashCode()
		return result
	}

}