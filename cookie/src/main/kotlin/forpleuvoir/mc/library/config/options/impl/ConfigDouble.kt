package forpleuvoir.mc.library.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigDouble
import net.minecraft.network.chat.MutableComponent

/**
 * 浮点配置实现

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options.impl

 * 文件名 ConfigDouble

 * 创建时间 2022/7/5 0:39

 * @author forpleuvoir

 */
open class ConfigDouble(
	override val key: String,
	override val displayName: MutableComponent,
	override val description: MutableComponent,
	final override val defaultValue: Double,
	override val minValue: Double,
	override val maxValue: Double,
) : ConfigBase<Double>(), IConfigDouble {

	override val type: ConfigType
		get() = ConfigTypes.DOUBLE

	override var configValue: Double = defaultValue

	override fun setValue(value: Double) {
		super.setValue(fixValue(value))
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonPrimitive) return false
		this.configValue = jsonElement.asDouble
		return true
	}

	override val serialize: JsonElement
		get() = JsonPrimitive(getValue())
}