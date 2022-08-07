package forpleuvoir.mc.library.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import forpleuvoir.mc.library.api.Option
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigOption
import forpleuvoir.mc.library.utils.ifc
import forpleuvoir.mc.library.utils.text.Text

/**
 * 选项配置实现

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options.impl

 * 文件名 ConfigOption

 * 创建时间 2022/7/7 12:43

 * @author forpleuvoir

 */
open class ConfigOption(
	override val key: String,
	override val displayName: Text,
	override val description: Text,
	private val options: LinkedHashSet<Option>,
	final override val defaultValue: Option = options.first(),
) : ConfigBase<Option>(), IConfigOption {

	override val type: ConfigType
		get() = ConfigTypes.OPTIONS

	override var configValue: Option = defaultValue

	override fun setValue(value: Option) {
		if (options.contains(value))
			super.setValue(value)
	}

	override fun getOptions(): LinkedHashSet<Option> = options

	override fun switch() {
		val index = options.indexOf(configValue)
		val size = options.size
		if (index < size - 1) {
			setValue(options.toTypedArray()[index + 1])
		} else {
			setValue(options.toTypedArray()[0])
		}
	}

	override fun matched(regex: Regex): Boolean {
		options.forEach {
			(regex.containsMatchIn(it.key) || regex.containsMatchIn(it.description.string) || regex.containsMatchIn(it.displayName.string)).ifc { return true }
		}
		return super.matched(regex)
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonPrimitive) return false
		jsonElement.asJsonPrimitive.apply {
			options.find { it.key == this.asString }?.let {
				configValue = it
				return true
			}
		}
		return false
	}

	override val serialization: JsonElement
		get() = JsonPrimitive(configValue.key)


}