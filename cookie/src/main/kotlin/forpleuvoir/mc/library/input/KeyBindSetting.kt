package forpleuvoir.mc.library.input

import forpleuvoir.mc.library.api.Matchable
import forpleuvoir.mc.library.api.serialization.JsonSerializer
import forpleuvoir.mc.library.gui.foundation.HandleStatus
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Interrupt
import forpleuvoir.mc.library.input.KeyEnvironment.InGame
import forpleuvoir.mc.library.input.KeyTriggerMode.OnRelease

/**
 * 按键设置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.input

 * 文件名 KeyBindSetting

 * 创建时间 2022/7/15 21:34

 * @author forpleuvoir

 */
interface KeyBindSetting : JsonSerializer, Matchable {
	/**
	 * 按键触发环境
	 */
	var environment: KeyEnvironment

	/**
	 * 是否取消之后的操作
	 */
	var cancelFurtherProcess: HandleStatus

	/**
	 * 只有指定顺序的按键才会触发
	 */
	var ordered: Boolean

	/**
	 * 按键触发模式
	 */
	var triggerMode: KeyTriggerMode

	/**
	 * 按下多久触发长按
	 */
	var longPressTime: Long

	/**
	 * 从其他按键设置复制
	 * @param target KeyBindSetting
	 * @return Boolean
	 */
	fun copyOf(target: KeyBindSetting): Boolean

}

fun keyBindSetting(
	environment: KeyEnvironment = InGame,
	cancelFurtherProcess: HandleStatus = Interrupt,
	ordered: Boolean = true,
	triggerMode: KeyTriggerMode = OnRelease,
	longPressTime: Long = 20,
): KeyBindSetting {
	return KeyBindSettingImpl(environment, cancelFurtherProcess, ordered, triggerMode, longPressTime)
}