package forpleuvoir.mc.library.config.options.impl

import com.google.gson.JsonElement
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigBooleanWithKeyBind
import forpleuvoir.mc.library.input.KeyBind
import forpleuvoir.mc.library.utils.jsonObject
import net.minecraft.network.chat.MutableComponent

/**
 * 带热荐的布尔配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options.impl

 * 文件名 ConfigBooleanWithKeyBind

 * 创建时间 2022/7/16 12:48

 * @author forpleuvoir

 */
class ConfigBooleanWithKeyBind(
	override val key: String,
	override val displayName: MutableComponent,
	override val description: MutableComponent,
	override val defaultValue: Boolean,
	val defaultKeyBind: KeyBind,
) : ConfigBase<Boolean>(), IConfigBooleanWithKeyBind {

	override val type: ConfigType
		get() = ConfigTypes.BOOLEAN_WITH_KEY_BIND

	override var configValue: Boolean = defaultValue

	override val isDefault: Boolean
		get() = super.isDefault && keyBind == defaultKeyBind

	private var keyBind: KeyBind = KeyBind {}.apply {
		copyOf(defaultKeyBind)
		action = {
			this@ConfigBooleanWithKeyBind.setValue(!getValue())
		}
	}

	override fun init() {
		super<IConfigBooleanWithKeyBind>.init()
		keyBind.subscribeChange(this) {
			this@ConfigBooleanWithKeyBind.onChanged()
		}
	}

	override fun getKeyBind(): KeyBind = keyBind

	override fun setKeyBind(keyBind: KeyBind) {
		if (this.keyBind.copyOf(keyBind)) {
			onChanged()
		}
	}

	override fun resetDefValue() {
		var valueChanged = false
		val oldValue = configValue
		if (defaultValue != oldValue) {
			configValue = defaultValue
			valueChanged = true
		}
		if (keyBind != defaultKeyBind) {
			if (keyBind.copyOf(defaultKeyBind)) {
				valueChanged = true
			}
		}
		if (valueChanged) onChanged()
	}

	override fun matched(regex: Regex): Boolean {
		return if (regex.run { keyBind matched regex || this.containsMatchIn(configValue.toString()) }) true
		else super.matched(regex)
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonObject) return false
		jsonElement.asJsonObject.apply {
			configValue = this["value"].asBoolean
			keyBind.deserialize(this["key_bind"])
		}
		return true
	}

	override val serialize: JsonElement
		get() = jsonObject {
			"value" at configValue
			"key_bind" at keyBind.serialize
		}
}