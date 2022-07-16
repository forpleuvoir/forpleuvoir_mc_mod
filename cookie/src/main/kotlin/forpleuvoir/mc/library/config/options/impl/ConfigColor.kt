package forpleuvoir.mc.library.config.options.impl

import com.google.gson.JsonElement
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigColor
import forpleuvoir.mc.library.utils.color.Color
import net.minecraft.network.chat.MutableComponent

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
	override val displayName: MutableComponent,
	override val description: MutableComponent,
	final override val defaultValue: Color<out Number>,
) : ConfigBase<Color<out Number>>(), IConfigColor {

	override val type: ConfigType
		get() = ConfigTypes.COLOR

	override var configValue: Color<out Number> = Color.copy(defaultValue)

	override fun setValue(value: Color<out Number>) {
		if (configValue == value) return
		configValue = Color.copy(value)
		onChanged()
	}

	override fun getValue(): Color<out Number> = Color.copy(configValue)

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		configValue.deserialize(jsonElement)
		return true
	}

	override val serialize: JsonElement
		get() = getValue().serialize
}