package forpleuvoir.mc.library.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigBoolean
import net.minecraft.network.chat.MutableComponent

/**
 * 布尔配置实现

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options.impl

 * 文件名 ConfigBoolean

 * 创建时间 2022/7/5 0:19

 * @author forpleuvoir

 */
open class ConfigBoolean(
	override val key: String,
	override val displayName: MutableComponent,
	override val description: MutableComponent,
	final override val defaultValue: Boolean,
) : ConfigBase<Boolean>(), IConfigBoolean {

	override val type: ConfigType
		get() = ConfigTypes.BOOLEAN

	override var configValue: Boolean = defaultValue

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonPrimitive) return false
		this.configValue = jsonElement.asBoolean
		return true
	}

	override val serialize: JsonElement
		get() = JsonPrimitive(getValue())


}