package forpleuvoir.mc.library.event.events.client.input

import com.mojang.blaze3d.platform.InputConstants
import forpleuvoir.mc.library.event.CancellableEvent
import forpleuvoir.mc.library.input.KeyEnvironment

/**
 * 鼠标按下事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.client.input

 * 文件名 MousePressEvent

 * 创建时间 2022/8/7 13:14

 * @author forpleuvoir

 */
class MousePressEvent(
	@JvmField val mouseCode: Int,
	@JvmField val modifiers: Int,
	@JvmField val keyEnvironment: KeyEnvironment,
) : CancellableEvent() {

	@JvmField
	val code: InputConstants.Key = InputConstants.Type.MOUSE.getOrCreate(mouseCode)

}