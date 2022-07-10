package forpleuvoir.mc.library.config.options

import com.google.gson.JsonElement
import forpleuvoir.mc.cookie.util.logger
import forpleuvoir.mc.library.config.Config
import java.util.concurrent.ConcurrentHashMap

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 ConfigBase

 * 创建时间 2022/7/4 13:18

 * @author forpleuvoir

 */
abstract class ConfigBase<T> : Config<T> {

	private val log = logger()

	protected abstract var configValue: T

	override fun setValue(value: T) {
		val oldValue = configValue
		if (value != oldValue) {
			configValue = value
			onChanged()
		}
	}

	override fun init() {}

	private var onChangedCallbacks: MutableMap<Any, T.() -> Unit> = ConcurrentHashMap()

	override fun getValue(): T = configValue

	override val isDefault: Boolean get() = getValue() == defaultValue

	override fun resetDefValue() = setValue(defaultValue)

	override fun matched(regex: Regex): Boolean {
		return regex.run {
			containsMatchIn(displayName.string)
					|| containsMatchIn(description.string)
					|| containsMatchIn(key)
					|| containsMatchIn(getValue().toString())
					|| containsMatchIn(type.type)
		}
	}

	override fun toString(): String {
		return "${type.type}{${key}:${getValue()}}"
	}

	protected abstract fun setFromJson(jsonElement: JsonElement): Boolean

	override fun JsonElement.deserialize() {
		try {
			if (!setFromJson(this)) {
				log.error("Failed to set config value '$key' from the JSON element ${this.asString}")
			}
		} catch (e: Exception) {
			log.error("Failed to set config value '$key' from the JSON element ${this.asString}")
			log.error(e)
		}
	}

	override fun Any.subscribeChange(callback: T.() -> Unit) {
		onChangedCallbacks[this] = callback
	}

	override fun onChanged() {
		onChangedCallbacks.forEach { (k, v) ->
			v.invoke(getValue())
		}
	}

	override fun equals(other: Any?): Boolean {
		return if (other is ConfigBase<*>) {
			this.key == other.key && this.getValue() == other.getValue()
		} else false
	}

	override fun hashCode(): Int {
		return key.hashCode()
	}

}