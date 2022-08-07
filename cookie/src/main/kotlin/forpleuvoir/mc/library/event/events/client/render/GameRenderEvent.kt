package forpleuvoir.mc.library.event.events.client.render

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.event.Event
import forpleuvoir.mc.library.gui.foundation.Drawable

/**
 * 游戏渲染事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.client.render

 * 文件名 GameRenderEvent

 * 创建时间 2022/8/7 13:17

 * @author forpleuvoir

 */
class GameRenderEvent(
	@JvmField val poseStack: PoseStack,
	@JvmField val tickDelta: Float,
	@JvmField val startTime: Long,
	@JvmField val tick: Boolean,
) : Event(), Drawable {
	override val render: (poseStack: PoseStack, delta: Double) -> Unit = { _: PoseStack, _: Double -> }

	override fun onRender(poseStack: PoseStack, delta: Double) {}
}