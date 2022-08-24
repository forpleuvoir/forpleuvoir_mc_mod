package forpleuvoir.mc.library.gui.widget.button

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.drawCenteredText
import forpleuvoir.mc.library.gui.foundation.drawTexture
import forpleuvoir.mc.library.gui.texture.BUTTON_1
import forpleuvoir.mc.library.gui.texture.BUTTON_1_HOVERED
import forpleuvoir.mc.library.gui.texture.BUTTON_1_PRESSED
import forpleuvoir.mc.library.gui.widget.ClickableElement
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.text.Text
import forpleuvoir.mc.library.utils.text.literal
import forpleuvoir.mc.library.utils.textRenderer
import java.util.function.Supplier

/**
 * 按钮

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.widget.button

 * 文件名 Button

 * 创建时间 2022/7/21 1:21

 * @author forpleuvoir

 */
open class Button() : ClickableElement() {

	constructor(text: () -> String) : this() {
		this.text = { literal(text()) }
	}

	constructor(text: Supplier<Text>) : this() {
		this.text = { text.get() }
	}


	var text: () -> Text = { literal() }

	override var width: Double = textRenderer.width(text()) + 12.0
		set(value) {
			field = value.coerceAtLeast(6.0)
		}

	override var height: Double = 20.0
		set(value) {
			field = value.coerceAtLeast(7.0)
		}

	var buttonColor: Color = Color.WHITE

	override fun onRender(poseStack: PoseStack, delta: Double) {
		drawBackground(poseStack, delta)
		val textY = status(y - 1, y - 2, y)
		drawCenteredText(
			poseStack,
			text(),
			x,
			textY,
			width,
			height,
			color = Color.RED.alpha(if (active) 1.0f else 0.7f),
			shadow = false
		)
	}

	protected open fun drawBackground(poseStack: PoseStack, delta: Number) {
		val texture = status(BUTTON_1, BUTTON_1_HOVERED, BUTTON_1_PRESSED)
		drawTexture(poseStack, x, y, width, height, texture, buttonColor)
	}
}