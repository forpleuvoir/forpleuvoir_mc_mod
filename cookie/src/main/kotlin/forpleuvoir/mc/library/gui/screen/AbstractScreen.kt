package forpleuvoir.mc.library.gui.screen

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.AbstractParentElement
import forpleuvoir.mc.library.gui.foundation.HandleStatus
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Continue
import forpleuvoir.mc.library.gui.foundation.ParentElement
import forpleuvoir.mc.library.gui.foundation.drawRect
import forpleuvoir.mc.library.input.KEY_ESCAPE
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.d

/**
 * 屏幕基础实现

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.screen

 * 文件名 AbstractScreen

 * 创建时间 2022/7/18 0:18

 * @author forpleuvoir

 */
abstract class AbstractScreen : AbstractParentElement(), Screen {

	override var parentScreen: Screen? = null

	override var parent: ParentElement?
		get() = parentScreen
		set(value) {
			if (value is Screen) parentScreen = value
		}

	override var pauseScreen: Boolean = false

	override var shouldCloseOnEsc: Boolean = true

	/**
	 * 背景颜色
	 */
	var backgroundColor: Color = Color.BLACK.alpha(0.5f)

	internal val tipRenderer: TipRenderer = TipRenderer(this)

	protected open fun renderBackground(poseStack: PoseStack, delta: Number) {
		drawRect(poseStack, this.x, this.y, this.width, this.height, backgroundColor)
	}

	override fun onRender(poseStack: PoseStack, delta: Double) {
		renderBackground(poseStack, delta)
		super.onRender(poseStack, delta)
		renderTip(poseStack, delta)
	}

	override fun close() {
		ScreenHandler.setCurrent(parentScreen)
	}

	override fun tick() {
		tipRenderer.tick()
	}

	override fun renderTip(poseStack: PoseStack, delta: Number) {
		tipRenderer.render(poseStack, delta.d)
	}

	override var resize: (width: Number, height: Number) -> Unit = { _: Number, _: Number -> }

	override fun onKeyPress(keyCode: Int, modifiers: Int): HandleStatus {
		if (keyCode == KEY_ESCAPE && shouldCloseOnEsc) {
			close()
			return Continue
		}
		return super.keyPress(keyCode, modifiers)
	}
}