package forpleuvoir.mc.library.event.events.client.input

import com.mojang.blaze3d.platform.InputConstants
import forpleuvoir.mc.library.event.CancellableEvent
import forpleuvoir.mc.library.input.KeyEnvironment

/**
 * 按键按下事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.client.input

 * 文件名 KeyPressEvent

 * 创建时间 2022/8/7 13:08

 * @author forpleuvoir

 */
class KeyPressEvent(
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