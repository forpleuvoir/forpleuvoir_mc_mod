package forpleuvoir.mc.library.input

import com.google.gson.JsonElement
import forpleuvoir.mc.cookie.util.logger
import forpleuvoir.mc.library.gui.foundation.HandleStatus
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Interrupt
import forpleuvoir.mc.library.input.KeyEnvironment.InGame
import forpleuvoir.mc.library.input.KeyTriggerMode.OnRelease
import forpleuvoir.mc.library.utils.jsonObject
import forpleuvoir.mc.library.utils.toJsonStr

/**
 * 按键设置默认实现

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.input

 * 文件名 KeyBindSettingImpl

 * 创建时间 2022/7/15 21:45

 * @author forpleuvoir

 */
class KeyBindSettingImpl(
	override var environment: KeyEnvironment = InGame,
	override var cancelFurtherProcess: HandleStatus = Interrupt,
	override var ordered: Boolean = true,
	override var triggerMode: KeyTriggerMode = OnRelease,
	longPressTime: Long = 20,
) : KeyBindSetting {

	private val log = logger()

	override var longPressTime: Long = longPressTime.coerceAtLeast(0)
		set(value) {
			field = value.coerceAtLeast(0)
		}

	override val serialization: JsonElement
		get() = jsonObject {
			"key_environment" at environment.key
			"cancel_further_process" at cancelFurtherProcess.name
			"ordered" at ordered
			"trigger_mode" at triggerMode.key
			"long_press_time" at longPressTime
		}

	override fun deserialize(serializedObject: JsonElement) {
		try {
			val jsonObject = serializedObject.asJsonObject
			environment = environment.fromKey(jsonObject["key_environment"].asString)
			cancelFurtherProcess = HandleStatus.valueOf(jsonObject["cancel_further_process"].asString)
			ordered = jsonObject["ordered"].asBoolean
			triggerMode = triggerMode.fromKey(jsonObject["trigger_mode"].asString)
			longPressTime = jsonObject["long_press_time"].asLong
		} catch (e: Exception) {
			log.error(e)
		}
	}

	override fun matched(regex: Regex): Boolean {
		return regex.run {
			containsMatchIn(serialization.toJsonStr())
		}
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as KeyBindSetting

		if (environment != other.environment) return false
		if (cancelFurtherProcess != other.cancelFurtherProcess) return false
		if (ordered != other.ordered) return false
		if (triggerMode != other.triggerMode) return false
		if (longPressTime != other.longPressTime) return false

		return true
	}

	override fun hashCode(): Int {
		var result = environment.hashCode()
		result = 31 * result + cancelFurtherProcess.hashCode()
		result = 31 * result + ordered.hashCode()
		result = 31 * result + triggerMode.hashCode()
		result = 31 * result + longPressTime.hashCode()
		return result
	}

	override fun copyOf(target: KeyBindSetting): Boolean {
		var valueChange = false
		if (this.cancelFurtherProcess != target.cancelFurtherProcess) {
			this.cancelFurtherProcess = target.cancelFurtherProcess
			valueChange = true
		}
		if (this.ordered != target.ordered) {
			this.ordered = target.ordered
			valueChange = true
		}
		if (this.triggerMode != target.triggerMode) {
			this.triggerMode = target.triggerMode
			valueChange = true
		}
		if (this.environment != target.environment) {
			this.environment = target.environment
			valueChange = true
		}
		if (this.longPressTime != target.longPressTime) {
			this.longPressTime = target.longPressTime
			valueChange = true
		}
		return valueChange
	}
}