package forpleuvoir.mc.library.config.options.impl

import com.google.gson.JsonElement
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigColor
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.text.Text

/**
 * 颜色配置实现

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options.impl

 * 文件名 ConfigColor

 * 创建时间 2022/7/5 0:29

 * @author forpleuvoir

 */
open class ConfigColor(
	override val key: String,
	override val displayName: Text,
	override val description: Text,
	final override val defaultValue: Color,
) : ConfigBase<Color>(), IConfigColor {

	override val type: ConfigType
		get() = ConfigTypes.COLOR

	override var configValue: Color = defaultValue.copy()

	override fun setValue(value: Color) {
		if (configValue == value) return
		configValue = value.copy()
		onChanged()
	}

	override fun getValue(): Color = configValue.copy()

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		configValue.deserialize(jsonElement)
		return true
	}

	override val serialization: JsonElement
		get() = getValue().serialization
}