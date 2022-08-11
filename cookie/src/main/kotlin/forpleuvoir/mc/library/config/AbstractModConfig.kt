package forpleuvoir.mc.library.config

import com.google.gson.JsonObject
import java.nio.file.Path

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config

 * 文件名 AbstractModConfig

 * 创建时间 2022/8/12 0:46

 * @author forpleuvoir

 */
abstract class AbstractModConfig(override val modId: String) : ModConfig {

	private val categories = ArrayList<ConfigCategory>()

	override val allCategory: List<ConfigCategory> = categories

	override fun addCategory(category: ConfigCategory) {
		categories.add(category)
	}

	override fun init() {
		allCategory.forEach {
			it.init()
			it.allConfigs.forEach { c ->
				c.subscribeChange(this) {
					isChanged = true
				}
			}
		}
	}

	abstract fun getConfigPath(): Path

	override fun save(path: Path) {
		ConfigUtil.run {
			val configFile = configFile(modId, getConfigPath())
			val json = JsonObject()
			allCategory.forEach {
				writeConfigCategory(json, it)
			}
			writeJsonToFile(json, configFile)
			isChanged = false
		}

	}

	override fun load(path: Path) {
		ConfigUtil.run {
			val configFile = configFile(modId, getConfigPath())
			paresJsonFile(configFile) { json ->
				json as JsonObject
				allCategory.forEach {
					readConfigCategory(json, it)
				}
			}
		}
	}

	override var isChanged: Boolean = false

}