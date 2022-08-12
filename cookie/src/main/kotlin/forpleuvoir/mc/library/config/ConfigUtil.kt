package forpleuvoir.mc.library.config

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import forpleuvoir.mc.cookie.util.logger
import forpleuvoir.mc.library.config.modconfig.ConfigCategory
import forpleuvoir.mc.library.utils.getNestedObject
import forpleuvoir.mc.library.utils.getOr
import forpleuvoir.mc.library.utils.gson
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer
import java.io.*
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.util.*

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config

 * 文件名 ConfigUtil

 * 创建时间 2022/8/12 0:07

 * @author forpleuvoir

 */
object ConfigUtil {

	private val log = logger()

	fun getServerConfigPath(modId: String, server: MinecraftServer): Path {
		val serverPath = server.storageSource.levelDirectory.path
		return File(serverPath.toFile(), modId).apply {
			if (!exists()) {
				this.mkdir()
			}
		}.toPath()
	}

	fun getClientConfigPath(modId: String): Path {
		val file = File(FabricLoader.getInstance().configDir.toFile(), modId)
		if (!file.exists())
			file.mkdir()
		return file.toPath()
	}

	fun configFile(configFileName: String, path: Path, create: Boolean = true): File {
		val file = File(path.toFile(), "${configFileName}.json")
		if (!file.exists() && create) {
			file.createNewFile()
		}
		return file
	}

	fun writeJsonToFile(root: JsonObject, file: File): Boolean {
		var fileTmp = File(file.parentFile, file.name + ".tmp")
		if (fileTmp.exists()) {
			fileTmp = File(file.parentFile, UUID.randomUUID().toString() + ".tmp")
		}
		try {
			OutputStreamWriter(FileOutputStream(fileTmp), StandardCharsets.UTF_8).use { writer ->
				writer.write(gson.toJson(root))
				writer.close()
				if (file.exists() && file.isFile && !file.delete()) {
					log.warn("Failed to delete file '{}'", file.absolutePath)
				}
				return fileTmp.renameTo(file)
			}
		} catch (e: Exception) {
			log.warn("Failed to write JSON data to file '{}'", fileTmp.absolutePath, e)
		}
		return false
	}

	fun writeConfigCategory(root: JsonObject, category: ConfigCategory) {
		root.getNestedObject(category.name, true)?.let {
			for (config in category.allConfigs) {
				it.add(config.key, config.serialization)
			}
		}
	}

	fun readConfigCategory(root: JsonObject, category: ConfigCategory) {
		val obj = root.getOr(category.name, JsonObject())
		if (!obj.entrySet().isNullOrEmpty()) {
			category.allConfigs.forEach {
				if (obj.has(it.key)) {
					it.deserialize(obj[it.key])
				}
			}
		}
	}

	fun paresJsonFile(file: File, action: (JsonElement) -> Unit) {
		if (file.exists() && file.isFile && file.canRead()) {
			val fileName = file.absolutePath
			try {
				InputStreamReader(FileInputStream(file), StandardCharsets.UTF_8).use { reader ->
					JsonParser.parseReader(reader).run {
						if (!this.isJsonNull)
							action(this)
					}
				}
			} catch (e: Exception) {
				log.error("Failed to parse the JSON file '{}'", fileName, e)
			}
		}
	}


}