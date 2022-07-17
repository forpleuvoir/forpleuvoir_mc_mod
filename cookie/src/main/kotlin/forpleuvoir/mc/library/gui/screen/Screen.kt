package forpleuvoir.mc.library.gui.screen

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.ParentElement
import forpleuvoir.mc.library.utils.mc
import net.minecraft.client.MouseHandler

/**
 * 屏幕

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.screen

 * 文件名 Screen

 * 创建时间 2022/7/17 23:56

 * @author forpleuvoir

 */
interface Screen : ParentElement {

	val mouse: MouseHandler get() = mc.mouseHandler

	var parentScreen: Screen?

	/**
	 * 打开Screen时是否会暂停游戏
	 */
	val pauseScreen: Boolean

	/**
	 * 是否应该在按下ESC时关闭屏幕
	 */
	val shouldCloseOnEsc: Boolean

	/**
	 * 关闭当前屏幕
	 */
	fun close()

	/**
	 * 渲染tip
	 * @param poseStack PoseStack
	 * @param delta Number
	 */
	fun renderTip(poseStack: PoseStack, delta: Number)

	/**
	 * 当重新调整屏幕大小时
	 */
	var resize: (width: Number, height: Number) -> Unit

}