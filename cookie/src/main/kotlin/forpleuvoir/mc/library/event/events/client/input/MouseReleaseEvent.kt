package forpleuvoir.mc.library.event.events.client.input

import com.mojang.blaze3d.platform.InputConstants
import forpleuvoir.mc.library.event.CancellableEvent
import forpleuvoir.mc.library.input.KeyEnvironment

/**
 * 鼠标按键释放事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.client.input

 * 文件名 MouseReleaseEvent

 * 创建时间 2022/8/7 13:15

 * @author forpleuvoir

 */
class MouseReleaseEvent(
	@JvmField val mouseCode: Int,
	@JvmField val modifiers: Int,
	@JvmField val keyEnvironment: KeyEnvironment,
) : CancellableEvent() {

	@JvmField
	val code: InputConstants.Key = InputConstants.Type.MOUSE.getOrCreate(mouseCode)

}