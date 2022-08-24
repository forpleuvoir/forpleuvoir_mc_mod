package forpleuvoir.mc.library.config.modconfig.impl

import forpleuvoir.mc.library.api.Option
import forpleuvoir.mc.library.config.Config
import forpleuvoir.mc.library.config.modconfig.ConfigCategory
import forpleuvoir.mc.library.config.modconfig.ModConfig
import forpleuvoir.mc.library.config.options.impl.*
import forpleuvoir.mc.library.input.KeyBind
import forpleuvoir.mc.library.utils.color.Color
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
@Suppress("SameParameterValue")
open class ConfigCategoryImpl(final override val name: String, modConfig: ModConfig) : ConfigCategory {

	private val modId = modConfig.modId

	private val configs = ConcurrentLinkedQueue<Config<*>>()

	override val allConfigs: Collection<Config<*>> = configs

	override fun init() {
		configs.forEach { it.init() }
	}

	fun <C : Config<*>> addConfig(config: C): C {
		config.apply {
			configs.add(this)
			return this
		}
	}

	fun displayName(key: String): Text {
		return translatable("$modId.config.$name.$key")
	}

	fun description(key: String): Text {
		return translatable("$modId.config.$name.description.$key")
	}

	protected fun configBoolean(key: String, defaultValue: Boolean): ConfigBoolean =
		addConfig(ConfigBoolean(key, displayName(key), description(key), defaultValue))

	protected fun configBooleanWithKeyBind(key: String, defaultValue: Boolean, defaultKeyBind: KeyBind): ConfigBooleanWithKeyBind =
		addConfig(ConfigBooleanWithKeyBind(key, displayName(key), description(key), defaultValue, defaultKeyBind))

	protected fun configColor(key: String, defaultValue: Color): ConfigColor =
		addConfig(ConfigColor(key, displayName(key), description(key), defaultValue))

	protected fun configDouble(
		key: String,
		defaultValue: Double,
		minValue: Double = Double.MIN_VALUE,
		maxValue: Double = Double.MAX_VALUE,
	): ConfigDouble =
		addConfig(ConfigDouble(key, displayName(key), description(key), defaultValue, minValue, maxValue))

	protected fun configGroup(key: String, defaultValue: Set<Config<*>>): ConfigGroup =
		addConfig(ConfigGroup(key, displayName(key), description(key), defaultValue))

	protected fun configInteger(
		key: String,
		defaultValue: Int,
		minValue: Int = Int.MIN_VALUE,
		maxValue: Int = Int.MAX_VALUE,
	): ConfigInteger =
		addConfig(ConfigInteger(key, displayName(key), description(key), defaultValue, minValue, maxValue))

	protected fun configKeyBind(key: String, defaultValue: KeyBind): ConfigKeyBind =
		addConfig(ConfigKeyBind(key, displayName(key), description(key), defaultValue))

	protected fun configOption(key: String, options: LinkedHashSet<Option>, defaultValue: Option = options.first()): ConfigOption =
		addConfig(ConfigOption(key, displayName(key), description(key), options, defaultValue))

	protected fun configString(key: String, defaultValue: String): ConfigString =
		addConfig(ConfigString(key, displayName(key), description(key), defaultValue))

	protected fun configStringList(key: String, defaultValue: List<String>): ConfigStringList =
		addConfig(ConfigStringList(key, displayName(key), description(key), defaultValue))

	protected fun configStringMap(key: String, defaultValue: Map<String, String>): ConfigStringMap =
		addConfig(ConfigStringMap(key, displayName(key), description(key), defaultValue))

	protected fun configStringPairs(key: String, defaultValue: List<Pair<String, String>>): ConfigStringPairs =
		addConfig(ConfigStringPairs(key, displayName(key), description(key), defaultValue))

}


