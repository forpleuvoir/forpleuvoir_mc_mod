package forpleuvoir.mc.library.event.events.client.input

import com.mojang.blaze3d.platform.InputConstants
import forpleuvoir.mc.library.event.CancellableEvent
import forpleuvoir.mc.library.input.KeyEnvironment

/**
 * 按键释放事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.client.input

 * 文件名 KeyReleaseEvent

 * 创建时间 2022/8/7 13:13

 * @author forpleuvoir

 */
class KeyReleaseEvent(
	@JvmField val keyCode: Int,
	scancode: Int,
	@JvmField val modifiers: Int,
	@JvmField val environment: KeyEnvironment,
) : CancellableEvent() {

	@JvmField
	val key: InputConstants.Key

	init {
		this.key = InputConstants.getKey(keyCode, scancode)
	}
}