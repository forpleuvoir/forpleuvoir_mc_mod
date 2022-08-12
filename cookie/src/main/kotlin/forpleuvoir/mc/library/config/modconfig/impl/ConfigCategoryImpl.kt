package forpleuvoir.mc.library.config.modconfig.impl

import forpleuvoir.mc.library.config.Config
import forpleuvoir.mc.library.config.modconfig.ConfigCategory
import forpleuvoir.mc.library.config.modconfig.ModConfig
import forpleuvoir.mc.library.config.options.impl.ConfigBoolean
import forpleuvoir.mc.library.utils.text.Text
import forpleuvoir.mc.library.utils.text.translatable
import java.util.concurrent.ConcurrentLinkedQueue

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig.impl

 * 文件名 AbstractConfigCategory

 * 创建时间 2022/8/12 0:36

 * @author forpleuvoir

 */
open class ConfigCategoryImpl(final override val name: String, modConfig: ModConfig) : ConfigCategory {

	private val modId = modConfig.modId

	private val configs = ConcurrentLinkedQueue<Config<*>>()

	override val allConfigs: Collection<Config<*>> = configs

	override fun init() {}

	fun <C : Config<*>> addConfig(config: C): C {
		config.apply {
			configs.add(this)
			return this
		}
	}

	fun displayName(key: String): Text {
		return translatable("$modId.$name.$key")
	}

	fun description(key: String): Text {
		return translatable("$modId.$name.description.$key")
	}

	protected fun configBoolean(key: String, defaultValue: Boolean): ConfigBoolean =
		addConfig(ConfigBoolean(key, displayName(key), description(key), defaultValue))


}


