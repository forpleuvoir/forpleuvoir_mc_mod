package forpleuvoir.mc.library.config.options

import com.google.gson.JsonElement
import forpleuvoir.mc.library.api.serialization.JsonSerializer
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
				// TODO: 2022/7/4  打印异常信息
			}
		} catch (e: Exception) {
			// TODO: 2022/7/4 打印异常信息
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