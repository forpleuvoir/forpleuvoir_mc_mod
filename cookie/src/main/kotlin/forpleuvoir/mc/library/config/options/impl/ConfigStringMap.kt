package forpleuvoir.mc.library.config.options.impl

import com.google.common.collect.ImmutableMap
import com.google.gson.JsonElement
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigMap
import forpleuvoir.mc.library.utils.ifc
import forpleuvoir.mc.library.utils.toJsonObject
import net.minecraft.network.chat.MutableComponent

/**
 * 字符串映射

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options.impl

 * 文件名 ConfigStringMap

 * 创建时间 2022/7/6 12:55

 * @author forpleuvoir

 */
class ConfigStringMap(
	override val key: String,
	override val displayName: MutableComponent,
	override val description: MutableComponent,
	override val defaultValue: Map<String, String>,
) : ConfigBase<Map<String, String>>(), IConfigMap<String, String> {

	override val type: ConfigType
		get() = ConfigTypes.STRING_MAP

	override var configValue: Map<String, String> = LinkedHashMap(defaultValue)

	override fun setValue(value: Map<String, String>) {
		if (this.configValue != value) {
			(configValue as LinkedHashMap).run {
				this.clear()
				this.putAll(value)
				onChanged()
			}
		}
	}

	override fun getValue(): Map<String, String> {
		return ImmutableMap.copyOf(configValue)
	}

	override fun resetDefValue() {
		setValue(LinkedHashMap(defaultValue))
	}

	override fun set(key: String, value: String) {
		val oldValue: String? = this.configValue[key]
		(this.configValue as LinkedHashMap)[key] = value
		if (oldValue != value) {
			this.onChanged()
		}
	}

	override fun get(key: String): String? {
		return configValue[key]
	}

	override fun remove(key: String): String? {
		if (key.contains(key)) {
			val value = (this.configValue as LinkedHashMap).remove(key)
			this.onChanged()
			return value
		}
		return null
	}

	override fun rename(origin: String, current: String) {
		if (this.configValue.containsKey(origin)) {
			val value = this.configValue[origin]
			value?.let {
				(this.configValue as LinkedHashMap).remove(origin)
				(this.configValue as LinkedHashMap)[current] = it
				this.onChanged()
			}
		}
	}

	override fun rest(originKey: String, currentKey: String, value: String) {
		if (this.configValue.containsKey(originKey)) {
			if (originKey != currentKey) rename(originKey, currentKey)
			(this.configValue as LinkedHashMap)[currentKey] = value
			this.onChanged()
		}
	}

	override fun clear() {
		(this.configValue as LinkedHashMap).clear()
	}

	override fun matched(regex: Regex): Boolean {
		getValue().forEach { (k, v) -> (regex.containsMatchIn(k) || regex.containsMatchIn(v)).ifc { return true } }
		return super.matched(regex)
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonObject) return false
		(configValue as LinkedHashMap).run {
			this.clear()
			jsonElement.asJsonObject.entrySet().forEach {
				this[it.key] = it.value.asString
			}
		}
		return true
	}

	override val serialize: JsonElement
		get() = getValue().toJsonObject()

}