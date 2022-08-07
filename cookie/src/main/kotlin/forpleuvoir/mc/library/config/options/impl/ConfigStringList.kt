package forpleuvoir.mc.library.config.options.impl

import com.google.gson.JsonElement
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigList
import forpleuvoir.mc.library.utils.ifc
import forpleuvoir.mc.library.utils.jsonArray
import forpleuvoir.mc.library.utils.text.Text

/**
 * 字符串列表

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options.impl

 * 文件名 ConfigStringList

 * 创建时间 2022/7/6 13:20

 * @author forpleuvoir

 */
open class ConfigStringList(
	override val key: String,
	override val displayName: Text,
	override val description: Text,
	final override val defaultValue: List<String>,
) : ConfigBase<List<String>>(), IConfigList<String> {

	override val type: ConfigType
		get() = ConfigTypes.STRING_LIST

	override var configValue: List<String> = ArrayList(defaultValue)

	private inline fun valueAsMutable(action: MutableList<String>.() -> Unit) {
		action(configValue as ArrayList<String>)
	}

	override fun setValue(value: List<String>) {
		if (this.configValue != value) {
			valueAsMutable {
				this.clear()
				this.addAll(value)
			}
			this.onChanged()
		}
	}

	override fun getValue(): List<String> = ArrayList(configValue)

	override fun add(element: String): String? {
		valueAsMutable {
			if (add(element)) {
				onChanged()
				return element
			}
		}
		return null
	}

	override fun get(index: Int): String? = configValue[index]

	override fun set(index: Int, element: String) {
		valueAsMutable {
			if (this[index] != element) {
				this[index] = element
				onChanged()
			}
		}
	}

	override fun remove(index: Int): String? {
		valueAsMutable {
			if (index >= 0 && index < this.size) {
				onChanged()
				return this.removeAt(index)
			}
		}
		return null
	}

	override fun remove(element: String): String? {
		valueAsMutable {
			if (remove(element)) {
				onChanged()
				return element
			}
		}
		return null
	}

	override fun clear() {
		valueAsMutable { clear() }
	}

	override fun matched(regex: Regex): Boolean {
		getValue().forEach { regex.containsMatchIn(it).ifc { return true } }
		return super.matched(regex)
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonArray) return false
		valueAsMutable {
			val jsonArray = jsonElement.asJsonArray
			clear()
			jsonArray.forEach { add(it.asString) }
		}
		return true
	}

	override val serialization: JsonElement
		get() = jsonArray(getValue())
}