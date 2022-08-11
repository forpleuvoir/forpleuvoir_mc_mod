package forpleuvoir.mc.library.config

import forpleuvoir.mc.library.config.options.impl.ConfigBoolean
import forpleuvoir.mc.library.utils.text.Text
import forpleuvoir.mc.library.utils.text.translatable

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config

 * 文件名 AbstractConfigCategory

 * 创建时间 2022/8/12 0:36

 * @author forpleuvoir

 */
open class ConfigCategoryImpl(final override val name: String, modConfig: ModConfig) : ConfigCategory {

	private val modId = modConfig.modId

	private val configs = ArrayList<Config<*>>()

	override val allConfigs: List<Config<*>> = configs

	override fun init() {}

	protected fun <C : Config<*>> addConfig(config: C): C {
		config.apply {
			configs.add(this)
			return this
		}
	}

	protected fun displayName(key: String): Text {
		return translatable("$modId.$name.$key")
	}

	protected fun description(key: String): Text {
		return translatable("$modId.$name.description.$key")
	}

	protected fun configBoolean(key: String, defaultValue: Boolean): ConfigBoolean =
		addConfig(ConfigBoolean(key, displayName(key), description(key), defaultValue))


}


