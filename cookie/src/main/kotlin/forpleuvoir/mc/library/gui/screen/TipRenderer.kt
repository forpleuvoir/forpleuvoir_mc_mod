package forpleuvoir.mc.library.gui.screen

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.*
import forpleuvoir.mc.library.gui.texture.COOKIE_WIDGET_TEXTURE
import forpleuvoir.mc.library.utils.*
import forpleuvoir.mc.library.utils.Direction.*
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.text.maxWidth
import forpleuvoir.mc.library.utils.text.wrapToLines
import net.minecraft.client.renderer.texture.Tickable

/**
 * tip渲染器

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.screen

 * 文件名 TipRenderer

 * 创建时间 2022/7/22 0:44

 * @author forpleuvoir

 */
class TipRenderer(
	private val screen: Screen,
	private val tipColdDown: Int = 12,
) : Drawable, Tickable {

	private var tipColdDownCounter: Int = 0
		set(value) {
			field = value.coerceAtMost(tipColdDown)
		}
	override val render: (poseStack: PoseStack, delta: Double) -> Unit
		get() = this::onRender

	override fun onRender(poseStack: PoseStack, delta: Double) {
		if (tipColdDownCounter < tipColdDown) return
		screen.hovered?.let {
			it.tip?.invoke()?.let { tips ->
				it.isEmptyTip().ifc { return }
				val bgCornerSize = 2
				val lineSpacing = 1.0f
				val padding = 2
				val margin = 2
				val lines = tips.wrapToLines(screen.width.i - (padding + bgCornerSize) * 2)
				val textWidth = lines.maxWidth()
				val textHeight = lines.size * textRenderer.lineHeight
				val width = textWidth + bgCornerSize * 2 + padding * 2
				val height = textHeight + bgCornerSize * 2 + padding * 2 + lineSpacing * (lines.size - 1)
				val canPlace: (Direction) -> Boolean = { dir ->
					when (dir) {
						Left  -> it.left.i - (width + 3 + margin) > 0
						Right -> it.right.i + (width + 3 + margin) < screen.width
						Up    -> it.top.i - (height + 3 + margin) > 0
						Down  -> it.bottom.i + (height + 3 + margin) < screen.height
					}
				}
				val direction: Direction =
					if (it.tipDirection?.invoke() != null) it.tipDirection!!.invoke()
					else if (canPlace(Up)) Up
					else if (canPlace(Right)) Right
					else if (canPlace(Down)) Down
					else Left
				setShaderTexture(COOKIE_WIDGET_TEXTURE)
				enableBlend()
				defaultBlendFunc()
				enableDepthTest()
				val textColor: Color = Color.BLACK
				val color = Color(255, 182, 185)
				val shadowColor: Color = Color.BLACK.alpha(0.3f)
				//render
				when (direction) {
					Left -> {
						//draw shadow
						setShaderColor(shadowColor)
						draw9Texture(
							poseStack,
							it.left.i - (width + 3 + margin),
							((it.bottom.i - (it.height / 2)) - height / 2).clamp(0, screen.height - height) + 2,
							bgCornerSize,
							width,
							height,
							32,
							48,
							32,
							32
						)
						drawTexture(poseStack, it.left.i - (margin + 4), it.bottom.i - (it.height / 2) - 3 + 2, 4, 7, 67, 48, 4, 7)
						setShaderColor(color)
						//draw background
						draw9Texture(
							poseStack,
							it.left.i - (width + 3 + margin),
							((it.bottom.i - (it.height / 2)) - height / 2).clamp(0, screen.height - height),
							bgCornerSize,
							width,
							height,
							32,
							48,
							32,
							32
						)
						//draw arrow
						drawTexture(poseStack, it.left.i - (margin + 4), it.bottom.i - (it.height / 2) - 3, 4, 7, 67, 48, 4, 7)
						drawTextLines(
							poseStack,
							lines,
							it.left.i - (width + 3 + margin) + padding + bgCornerSize,
							((it.bottom.i - (it.height / 2)) - height / 2).clamp(0, screen.height - height) + padding + bgCornerSize,
							color = textColor,
							lineSpacing = lineSpacing
						)
					}

					Right -> {
						setShaderColor(shadowColor)
						draw9Texture(
							poseStack,
							it.right.i + margin + 3,
							((it.bottom.i - (it.height / 2)) - height / 2).clamp(0, screen.height - height) + 2,
							bgCornerSize,
							width,
							height,
							32,
							48,
							32,
							32
						)
						drawTexture(poseStack, it.right.i + margin, it.bottom.i - (it.height / 2) - 3 + 2, 4, 7, 64, 48, 4, 7)
						setShaderColor(color)
						draw9Texture(
							poseStack, it.right.i + margin + 3, ((it.bottom.i - (it.height / 2)) - height / 2).clamp(0, screen.height - height),
							bgCornerSize, width, height, 32, 48, 32, 32
						)
						drawTexture(poseStack, it.right.i + margin, it.bottom.i - (it.height / 2) - 3, 4, 7, 64, 48, 4, 7)
						drawTextLines(
							poseStack,
							lines,
							it.right.i + margin + 3 + padding + bgCornerSize,
							((it.bottom.i - (it.height / 2)) - height / 2).clamp(0, screen.height - height) + padding + bgCornerSize,
							color = textColor,
							lineSpacing = lineSpacing
						)
					}

					Up    -> {
						setShaderColor(shadowColor)
						draw9Texture(
							poseStack,
							(it.right.i - (it.width / 2) - (width / 2)).clamp(0, screen.width - width),
							it.top.i - (height + 3 + margin) + 2,
							bgCornerSize,
							width,
							height,
							32,
							48,
							32,
							32
						)
						drawTexture(poseStack, (it.right.i - (it.width / 2) - 3), it.top.i - (margin + 4) + 2 + 1, 7, 3, 64, 52, 7, 3)
						setShaderColor(color)
						draw9Texture(
							poseStack,
							(it.right.i - (it.width / 2) - (width / 2)).clamp(0, screen.width - width),
							it.top.i - (height + 3 + margin),
							bgCornerSize,
							width,
							height,
							32,
							48,
							32,
							32
						)
						drawTexture(poseStack, (it.right.i - (it.width / 2) - 3), it.top.i - (margin + 4), 7, 4, 64, 51, 7, 4)
						drawTextLines(
							poseStack,
							lines,
							(it.right.i - (it.width / 2) - (width / 2)).clamp(0, screen.width - width) + padding + bgCornerSize,
							it.top.i - (height + 3 + margin) + padding + bgCornerSize,
							color = textColor,
							lineSpacing = lineSpacing
						)
					}

					Down  -> {
						setShaderColor(shadowColor)
						draw9Texture(
							poseStack,
							(it.right.i - (it.width / 2) - (width / 2)).clamp(0, screen.width - width),
							it.bottom.i + (3 + margin) + 2,
							bgCornerSize,
							width,
							height,
							32,
							48,
							32,
							32
						)
						drawTexture(poseStack, (it.right.i - (it.width / 2) - 3), it.bottom.i + margin + 2, 7, 4, 64, 48, 7, 4)
						setShaderColor(color)
						draw9Texture(
							poseStack, (it.right.i - (it.width / 2) - (width / 2)).clamp(0, screen.width - width), it.bottom.i + (3 + margin),
							bgCornerSize, width, height, 32, 48, 32, 32
						)
						drawTexture(poseStack, (it.right.i - (it.width / 2) - 3), it.bottom.i + margin, 7, 4, 64, 48, 7, 4)
						drawTextLines(
							poseStack,
							lines,
							(it.right.i - (it.width / 2) - (width / 2)).clamp(0, screen.width - width) + padding + bgCornerSize,
							it.bottom.i + (3 + margin) + padding + bgCornerSize,
							color = textColor,
							lineSpacing = lineSpacing
						)
					}
				}
				setShaderColor(Color.WHITE)
				disableBlend()
			}
		}
	}

	override fun tick() {
		if (screen.hovered != null) {
			tipColdDownCounter++
		} else {
			tipColdDownCounter = 0
		}
	}

}