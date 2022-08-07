package forpleuvoir.mc.library.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigString
import forpleuvoir.mc.library.utils.text.Text

/**
 * 字符串配置实现

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options.impl

 * 文件名 ConfigString

 * 创建时间 2022/7/8 13:07

 * @author forpleuvoir

 */
class ConfigString(
	override val key: String,
	override val displayName: Text,
	override val description: Text,
	override val defaultValue: String,
) : ConfigBase<String>(), IConfigString {

	override val type: ConfigType
		get() = ConfigTypes.STRING

	override var configValue: String = defaultValue

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonPrimitive) return false
		configValue = jsonElement.asJsonPrimitive.asString
		return true
	}

	override val serialization: JsonElement
		get() = JsonPrimitive(getValue())
}