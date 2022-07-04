package forpleuvoir.mc.library.config.options

import com.google.gson.JsonElement
import forpleuvoir.mc.library.api.serialization.JsonSerializer
import forpleuvoir.mc.library.config.Config

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 ConfigBase

 * 创建时间 2022/7/4 13:18

 * @author forpleuvoir

 */
abstract class ConfigBase<T> : Config<T>, JsonSerializer {

	protected abstract var configValue: T

	override fun setValue(value: T) {
		val oldValue = configValue
		if (value != oldValue) {
			configValue = value
			onChanged()
		}
	}

	private var onChangedCallback: T.() -> Unit = {}

	override fun getValue(): T = configValue

	override val isDefault: Boolean get() = getValue() == defaultValue

	override fun resetDefValue() = setValue(defaultValue)

	override fun matched(regex: Regex): Boolean {
		return regex.run {
			containsMatchIn(displayName.string)
					|| containsMatchIn(description.string)
					|| containsMatchIn(key)
					|| containsMatchIn(getValue().toString())
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

	override fun setOnChangedCallback(callback: T.() -> Unit) {
		onChangedCallback = callback
	}

	override fun onChanged() {
		onChangedCallback(getValue())
	}

	override fun equals(other: Any?): Boolean {
		return if (other is ConfigBase<*>) {
			this.key == other.key && this.getValue() == other.getValue()
		} else false
	}

	override fun hashCode(): Int {
		var result = getValue().hashCode()
		result = 31 * result + key.hashCode()
		return result
	}

}