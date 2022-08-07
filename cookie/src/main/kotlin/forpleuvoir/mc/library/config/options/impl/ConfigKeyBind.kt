package forpleuvoir.mc.library.config.options.impl

import com.google.gson.JsonElement
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigKeyBind
import forpleuvoir.mc.library.input.KeyBind
import forpleuvoir.mc.library.utils.text.Text

/**
 * 热键配置实现

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options.impl

 * 文件名 ConfigKeyBind

 * 创建时间 2022/7/16 12:13

 * @author forpleuvoir

 */
class ConfigKeyBind(
	override val key: String,
	override val displayName: Text,
	override val description: Text,
	override val defaultValue: KeyBind,
) : ConfigBase<KeyBind>(), IConfigKeyBind {

	override val type: ConfigType
		get() = ConfigTypes.KEY_BIND

	override var configValue: KeyBind = KeyBind {}.apply { copyOf(defaultValue) }

	override fun init() {
		super<IConfigKeyBind>.init()
		configValue.subscribeChange(this) {
			this@ConfigKeyBind.onChanged()
		}
	}

	override fun setValue(value: KeyBind) {
		if (this.configValue.copyOf(value)) {
			onChanged()
		}
	}

	override fun matched(regex: Regex): Boolean {
		return if (regex.run { configValue matched regex }) true
		else super.matched(regex)
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonObject) return false
		configValue.deserialize(jsonElement)
		return true
	}

	override val serialization: JsonElement
		get() = configValue.serialization


}