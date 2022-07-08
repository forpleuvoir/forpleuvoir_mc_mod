package forpleuvoir.mc.library.config.options.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import forpleuvoir.mc.library.api.Option
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigOption
import forpleuvoir.mc.library.utils.ifc
import net.minecraft.network.chat.MutableComponent

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
	override val displayName: MutableComponent,
	override val description: MutableComponent,
	private val options: Set<Option>,
	final override val defaultValue: Option = options.first(),
) : ConfigBase<Option>(), IConfigOption {

	override val type: ConfigType
		get() = ConfigTypes.OPTIONS

	override var configValue: Option = defaultValue

	override fun setValue(value: Option) {
		if (options.contains(value))
			super.setValue(value)
	}

	override fun getOptions(): Set<Option> = options

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

	override val serialize: JsonElement
		get() = JsonPrimitive(configValue.key)


}