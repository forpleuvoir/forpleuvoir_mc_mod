package forpleuvoir.mc.library.gui.text

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import forpleuvoir.mc.library.gui.foundation.AbstractElement
import forpleuvoir.mc.library.gui.foundation.Align
import forpleuvoir.mc.library.gui.foundation.Align.*
import forpleuvoir.mc.library.gui.foundation.drawOutlinedBox
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.color.Color4i
import forpleuvoir.mc.library.utils.d
import forpleuvoir.mc.library.utils.f
import forpleuvoir.mc.library.utils.text.literal
import forpleuvoir.mc.library.utils.textRenderer
import net.minecraft.client.renderer.LightTexture.FULL_BRIGHT
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.network.chat.MutableComponent
import java.util.function.Supplier

/**
 * 文本标签

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.text

 * 文件名 TextLabel

 * 创建时间 2022/7/22 1:03

 * @author forpleuvoir

 */
class TextLabel() : AbstractElement() {

	var text: () -> MutableComponent = { literal() }
	override var width: Double = textRenderer.width(text()).d
	override var height: Double = textRenderer.lineHeight.d

	constructor(
		text: String,
		width: Double = textRenderer.width(text).d,
		height: Double = textRenderer.lineHeight.d,
	) : this() {
		this.text = { literal(text) }
		this.width = width
		this.height = height
	}

	constructor(
		text: () -> String,
		width: Double = textRenderer.width(text()).d,
		height: Double = textRenderer.lineHeight.d,
	) : this(
		text(),
		width,
		height
	)

	constructor(
		text: Supplier<MutableComponent>,
		width: Double = textRenderer.width(text.get()).d,
		height: Double = textRenderer.lineHeight.d,
	) : this() {
		this.text = { text.get() }
		this.width = width
		this.height = height
	}

	var align: Align = Center

	private val textWidth: Int get() = textRenderer.width(this.text())
	private val textHeight: Int get() = textRenderer.lineHeight

	private val centerX: Double get() = (this.x + this.width / 2) + padding.left
	private val centerY: Double get() = (this.y + this.height / 2) + padding.top

	var textColor: Color<out Number> = Color4i().fromInt(Color4i.WHITE.rgba)
	var rightToLeft: Boolean = false
	var shadow: Boolean = false
	var backgroundColor: Color<out Number> = Color4i.BLACK.apply { alpha = 0 }
	var bordColor: Color<out Number> = Color4i.BLACK.apply { alpha = 0 }

	private val renderText: String
		get() {
			return if (this.width - padding.horizontal >= textRenderer.width(text())) {
				text().string
			} else {
				textRenderer.substrByWidth(text(), (this.width - padding.horizontal).toInt()).string
			}
		}

	override fun onRender(poseStack: PoseStack, delta: Double) {
		drawOutlinedBox(poseStack, x, y, width, height, backgroundColor, bordColor, 1, false)
		renderText(poseStack)
	}

	private fun renderText(poseStack: PoseStack) {
		val textX: Double
		val textY: Double
		when (align) {
			TopLeft      -> {
				textX = this.x + padding.left
				textY = this.y + padding.top
			}

			TopCenter    -> {
				textX = centerX - textWidth / 2
				textY = this.y + padding.top
			}

			TopRight     -> {
				textX = this.x + this.width - textWidth - padding.right
				textY = this.y + padding.top
			}

			CenterLeft   -> {
				textX = this.x + padding.left
				textY = centerY - textHeight / 2
			}

			Center       -> {
				textX = centerX - textWidth / 2
				textY = centerY - textHeight / 2
			}

			CenterRight  -> {
				textX = this.x + this.width - textWidth - padding.right
				textY = centerY - textHeight / 2
			}

			BottomLeft   -> {
				textX = this.x + padding.left
				textY = this.y - textHeight - padding.bottom
			}

			BottomCenter -> {
				textX = centerX - textWidth / 2
				textY = this.y - textHeight - padding.bottom
			}

			BottomRight  -> {
				textX = this.x + this.width - textWidth - padding.right
				textY = this.y - textHeight - padding.bottom
			}
		}
		val immediate = MultiBufferSource.immediate(Tesselator.getInstance().builder)
		textRenderer.drawInBatch(
			renderText,
			textX.f,
			textY.f,
			textColor.rgba,
			shadow,
			poseStack.last().pose(),
			immediate,
			false,
			0,
			FULL_BRIGHT,
			rightToLeft
		)
		immediate.endBatch()
	}

}