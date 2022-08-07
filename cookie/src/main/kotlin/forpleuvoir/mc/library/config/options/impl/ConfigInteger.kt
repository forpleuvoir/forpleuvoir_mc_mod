package forpleuvoir.mc.library.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigInteger
import forpleuvoir.mc.library.utils.text.Text

/**
 * 整数配置实现

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options.impl

 * 文件名 ConfigInteger

 * 创建时间 2022/7/5 13:25

 * @author forpleuvoir

 */
open class ConfigInteger(
	override val key: String,
	override val displayName: Text,
	override val description: Text,
	final override val defaultValue: Int,
	override val minValue: Int = Int.MIN_VALUE,
	override val maxValue: Int = Int.MAX_VALUE,
) : ConfigBase<Int>(), IConfigInteger {

	override val type: ConfigType
		get() = ConfigTypes.INTEGER

	override var configValue: Int = defaultValue

	override fun setValue(value: Int) {
		super.setValue(fixValue(value))
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonPrimitive) return false
		this.configValue = jsonElement.asInt
		return true
	}

	override val serialization: JsonElement
		get() = JsonPrimitive(getValue())

}