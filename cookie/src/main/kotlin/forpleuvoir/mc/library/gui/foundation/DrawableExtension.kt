package forpleuvoir.mc.library.gui.foundation

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import forpleuvoir.mc.library.gui.foundation.HorizontalAlign.*
import forpleuvoir.mc.library.gui.texture.GuiTexture
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.d
import forpleuvoir.mc.library.utils.f
import forpleuvoir.mc.library.utils.textRenderer
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.LightTexture.FULL_BRIGHT
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import java.util.function.Supplier

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.foundation

 * 文件名 DrawableExtension

 * 创建时间 2022/7/17 18:51

 * @author forpleuvoir

 */


fun setShader(shaderSupplier: Supplier<ShaderInstance>) = RenderSystem.setShader(shaderSupplier)

fun setShader(shaderSupplier: (() -> ShaderInstance)) = RenderSystem.setShader(shaderSupplier)

fun setShaderTexture(texture: ResourceLocation) = RenderSystem.setShaderTexture(0, texture)

fun enableTexture() = RenderSystem.enableTexture()

fun disableTexture() = RenderSystem.disableTexture()

fun setShaderColor(color: Color) = RenderSystem.setShaderColor(color.redF, color.greenF, color.blueF, color.alphaF)

fun enablePolygonOffset() = RenderSystem.enablePolygonOffset()

fun polygonOffset(factor: Number, units: Number) = RenderSystem.polygonOffset(factor.f, units.f)

fun disablePolygonOffset() = RenderSystem.disablePolygonOffset()

fun enableBlend() = RenderSystem.enableBlend()

fun defaultBlendFunc() = RenderSystem.defaultBlendFunc()

fun disableBlend() = RenderSystem.disableBlend()

fun enableDepthTest() = RenderSystem.enableDepthTest()

fun disableDepthTest() = RenderSystem.disableDepthTest()

/**
 * 绘制矩形
 * @receiver Drawable
 * @param poseStack PoseStack
 * @param x Number
 * @param y Number
 * @param width Number
 * @param height Number
 * @param color IColor
 */
fun Drawable.drawRect(poseStack: PoseStack, x: Number, y: Number, width: Number, height: Number, color: Color) {
	setShader { GameRenderer.getPositionColorShader()!! }
	enableBlend()
	defaultBlendFunc()
	disableTexture()
	val buffer = Tesselator.getInstance().builder
	val matrix4f = poseStack.last().pose()
	buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)
	buffer.vertex(matrix4f, x.f, y.f, zOffset.f).color(color.argb).endVertex()
	buffer.vertex(matrix4f, x.f, (y.f + height.f), zOffset.f).color(color.argb).endVertex()
	buffer.vertex(matrix4f, (x.f + width.f), (y.f + height.f), zOffset.f).color(color.argb).endVertex()
	buffer.vertex(matrix4f, (x.f + width.f), y.f, zOffset.f).color(color.argb).endVertex()
	BufferUploader.drawWithShader(buffer.end())
	enableTexture()
	disableBlend()
}

/**
 * 绘制边框线条
 * @receiver Drawable
 * @param poseStack PoseStack
 * @param x Number
 * @param y Number
 * @param width Number
 * @param height Number
 * @param color IColor
 * @param borderWidth Number
 */
fun Drawable.drawOutline(
	poseStack: PoseStack,
	x: Number,
	y: Number,
	width: Number,
	height: Number,
	color: Color,
	borderWidth: Number = 1,
) {
	drawRect(poseStack, x, y, borderWidth, height, color)
	drawRect(poseStack, x.d + width.d - borderWidth.d, y, borderWidth, height, color)
	drawRect(poseStack, x.d + borderWidth.d, y, width.d - 2 * borderWidth.d, borderWidth, color)
	drawRect(
		poseStack,
		x.d + borderWidth.d,
		y.d + height.d - borderWidth.d,
		width.d - 2 * borderWidth.d,
		borderWidth,
		color
	)
}

/**
 *
 * 绘制带边框线条的矩形
 *
 * @receiver Drawable
 * @param poseStack PoseStack
 * @param x Number
 * @param y Number
 * @param width Number
 * @param height Number
 * @param color IColor
 * @param outlineColor IColor
 * @param borderWidth Number
 * @param innerOutline Boolean
 */
fun Drawable.drawOutlinedBox(
	poseStack: PoseStack,
	x: Number,
	y: Number,
	width: Number,
	height: Number,
	color: Color,
	outlineColor: Color,
	borderWidth: Number = 1,
	innerOutline: Boolean = true,
) {
	if (innerOutline) {
		drawRect(poseStack, x, y, width, height, color)
		drawOutline(
			poseStack,
			x.d - borderWidth.d,
			y.d - borderWidth.d,
			width.d + borderWidth.d * 2,
			height.d + borderWidth.d * 2,
			outlineColor,
			borderWidth
		)
	} else {
		drawRect(poseStack, x.d + borderWidth.d, y.d + borderWidth.d, width.d - 2 * borderWidth.d, height.d - 2 * borderWidth.d, color)
		drawOutline(poseStack, x.d, y.d, width.d, height.d, outlineColor, borderWidth)
	}
}

fun Drawable.drawGradient(
	poseStack: PoseStack,
	startX: Number,
	startY: Number,
	endX: Number,
	endY: Number,
	startColor: Color,
	endColor: Color,
) {
	disableTexture()
	enableBlend()
	setShader { GameRenderer.getPositionColorShader()!! }
	val tess = Tesselator.getInstance()
	val buffer = tess.builder
	val matrix4f = poseStack.last().pose()
	buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)
	buffer.vertex(matrix4f, endX.f, startY.f, zOffset.f).color(startColor.argb).endVertex()
	buffer.vertex(matrix4f, startX.f, startY.f, zOffset.f).color(startColor.argb).endVertex()
	buffer.vertex(matrix4f, startX.f, endY.f, zOffset.f).color(endColor.argb).endVertex()
	buffer.vertex(matrix4f, endX.f, endY.f, zOffset.f).color(endColor.argb).endVertex()
	tess.end()
	disableBlend()
	enableTexture()
}

fun Drawable.drawTexture(
	poseStack: PoseStack,
	x: Number,
	y: Number,
	width: Number,
	height: Number,
	u: Number,
	v: Number,
	regionWidth: Int,
	regionHeight: Int,
	textureWidth: Int = 256,
	textureHeight: Int = 256,
) {
	val matrix4f = poseStack.last().pose()
	val bufferBuilder = Tesselator.getInstance().builder
	setShader { GameRenderer.getPositionTexShader()!! }
	bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX)
	bufferBuilder.vertex(matrix4f, x.f, y.f + height.f, zOffset.f).uv(u.f / textureWidth, (v.f + regionHeight) / textureHeight).endVertex()
	bufferBuilder.vertex(matrix4f, x.f + width.f, y.f + height.f, zOffset.f)
		.uv((u.f + regionWidth) / textureWidth, (v.f + regionHeight) / textureHeight).endVertex()
	bufferBuilder.vertex(matrix4f, x.f + width.f, y.f, zOffset.f).uv((u.f + regionWidth) / textureWidth, v.f / textureHeight).endVertex()
	bufferBuilder.vertex(matrix4f, x.f, y.f, zOffset.f).uv(u.f / textureWidth, v.f / textureHeight).endVertex()
	BufferUploader.drawWithShader(bufferBuilder.end())
}

/**
 * 渲染.9 格式的材质
 * 只适用于边角为相同大小的正方形的材质
 * @receiver Drawable
 */
fun Drawable.draw9Texture(
	poseStack: PoseStack,
	x: Number,
	y: Number,
	/**
	 * 边角大小
	 */
	cornerSize: Int,
	width: Number,
	height: Number,
	u: Number,
	v: Number,
	regionWidth: Int,
	regionHeight: Int,
	textureWidth: Int = 256,
	textureHeight: Int = 256,
) {
	draw9Texture(
		poseStack,
		x,
		y,
		cornerSize,
		cornerSize,
		cornerSize,
		cornerSize,
		width,
		height,
		u,
		v,
		regionWidth,
		regionHeight,
		textureWidth,
		textureHeight
	)
}

/**
 * 渲染.9 格式的材质
 * @receiver Drawable
 */
fun Drawable.draw9Texture(
	poseStack: PoseStack,
	x: Number,
	y: Number,
	cornerLeftWidth: Int,
	cornerRightWidth: Int,
	cornerTopHeight: Int,
	cornerBottomHeight: Int,
	width: Number,
	height: Number,
	u: Number,
	v: Number,
	regionWidth: Int,
	regionHeight: Int,
	textureWidth: Int = 256,
	textureHeight: Int = 256,
) {
	if (cornerLeftWidth == 0 &&
		cornerRightWidth == 0 &&
		cornerTopHeight == 0 &&
		cornerBottomHeight == 0
	) {
		drawTexture(poseStack, x, y, width, height, u, v, regionWidth, regionHeight, textureWidth, textureHeight)
	}

	/**
	 * centerWidth
	 */
	val cw = width.d - (cornerLeftWidth + cornerRightWidth)

	/**
	 * centerHeight
	 */
	val ch = height.d - (cornerTopHeight + cornerBottomHeight)

	/**
	 * centerRegionWidth
	 */
	val crw = regionWidth - (cornerLeftWidth + cornerRightWidth)

	/**
	 *  centerRegionHeight
	 */
	val crh = regionHeight - (cornerTopHeight + cornerBottomHeight)

	val centerU = u.d + cornerLeftWidth
	val rightU = u.d + (regionWidth - cornerRightWidth)
	val centerV = v.d + cornerTopHeight
	val bottomV = v.d + (regionHeight - cornerBottomHeight)
	val centerX = x.d + cornerLeftWidth
	val rightX = x.d + (width.d - cornerRightWidth)
	val centerY = y.d + cornerTopHeight
	val bottomY = y.d + (height.d - cornerBottomHeight)
	//top left
	drawTexture(poseStack, x, y, cornerLeftWidth, cornerTopHeight, u, v, cornerLeftWidth, cornerTopHeight, textureWidth, textureHeight)
	//top center
	drawTexture(poseStack, centerX, y, cw, cornerTopHeight, centerU, v, crw, cornerTopHeight, textureWidth, textureHeight)
	//top right
	drawTexture(
		poseStack,
		rightX,
		y,
		cornerRightWidth,
		cornerTopHeight,
		rightU,
		v,
		cornerRightWidth,
		cornerTopHeight,
		textureWidth,
		textureHeight
	)
	//center left
	drawTexture(poseStack, x, centerY, cornerLeftWidth, ch, u, centerV, cornerLeftWidth, crh, textureWidth, textureHeight)
	//center
	drawTexture(poseStack, centerX, centerY, cw, ch, centerU, centerV, crw, crh, textureWidth, textureHeight)
	//center right
	drawTexture(poseStack, rightX, centerY, cornerRightWidth, ch, rightU, centerV, cornerRightWidth, crh, textureWidth, textureHeight)
	//bottom left
	drawTexture(
		poseStack,
		x,
		bottomY,
		cornerLeftWidth,
		cornerBottomHeight,
		u,
		bottomV,
		cornerLeftWidth,
		cornerBottomHeight,
		textureWidth,
		textureHeight
	)
	//bottom center
	drawTexture(poseStack, centerX, bottomY, cw, cornerBottomHeight, centerU, bottomV, crw, cornerBottomHeight, textureWidth, textureHeight)
	//bottom right
	drawTexture(
		poseStack,
		rightX,
		bottomY,
		cornerRightWidth,
		cornerBottomHeight,
		rightU,
		bottomV,
		cornerRightWidth,
		cornerBottomHeight,
		textureWidth,
		textureHeight
	)
}

fun Drawable.drawTexture(
	poseStack: PoseStack,
	x: Number,
	y: Number,
	width: Number,
	height: Number,
	texture: GuiTexture,
	shaderColor: Color = Color.WHITE,
) {
	setShaderTexture(texture.texture)
	enableBlend()
	defaultBlendFunc()
	enableDepthTest()
	setShaderColor(shaderColor)
	draw9Texture(
		poseStack,
		x,
		y,
		texture.corner.left,
		texture.corner.right,
		texture.corner.top,
		texture.corner.bottom,
		width,
		height,
		texture.u,
		texture.v,
		texture.regionWidth,
		texture.regionHeight,
		texture.textureWidth,
		texture.textureHeight
	)
	disableBlend()
	setShaderColor(Color.WHITE)
}


fun drawCenteredText(
	poseStack: PoseStack,
	text: Component,
	x: Number,
	y: Number,
	width: Number,
	height: Number,
	shadow: Boolean = true,
	rightToLeft: Boolean = false,
	color: Color = Color.WHITE,
	backgroundColor: Color = Color.WHITE.alpha(0.5f),
) {
	setShaderColor(Color.WHITE)
	val centerX = x.f + width.f / 2
	val centerY = y.f + height.f / 2
	val textWidth = textRenderer.width(text)
	val immediate = MultiBufferSource.immediate(Tesselator.getInstance().builder)
	textRenderer.drawInBatch(
		text.string,
		centerX - textWidth / 2,
		centerY - textRenderer.lineHeight / 2,
		color.argb,
		shadow,
		poseStack.last().pose(),
		immediate,
		false,
		backgroundColor.argb,
		FULL_BRIGHT,
		rightToLeft
	)
	immediate.endBatch()
}


fun drawText(
	poseStack: PoseStack,
	text: String,
	x: Number,
	y: Number,
	shadow: Boolean = true,
	color: Color = Color.WHITE,
) {
	textRenderer.drawInBatch(
		text,
		x.f,
		y.f,
		color.argb,
		shadow,
		poseStack.last().pose(),
		MultiBufferSource.immediate(Tesselator.getInstance().builder),
		false,
		Color.WHITE.argb,
		FULL_BRIGHT,
		false
	)
}

fun drawStringLines(
	poseStack: PoseStack,
	lines: List<String>,
	x: Number,
	y: Number,
	lineSpacing: Number = 1,
	color: Color = Color.BLACK,
	shadow: Boolean = false,
	align: HorizontalAlign = Left,
	rightToLeft: Boolean = false,
) {
	val drawText: (text: String, posX: Float, posY: Float) -> Unit = { text, posX, posY ->
		val immediate = MultiBufferSource.immediate(Tesselator.getInstance().builder)
		textRenderer.drawInBatch(text, posX, posY, color.argb, shadow, poseStack.last().pose(), immediate, false, 0, FULL_BRIGHT, rightToLeft)
		immediate.endBatch()
	}
	var textY = y.f
	when (align) {
		Left   -> {
			val textX = x.f
			for (text in lines) {
				drawText(text, textX, textY)
				textY += textRenderer.lineHeight + lineSpacing.f
			}
		}

		Center -> {
			for (text in lines) {
				val textX = x.f - (textRenderer.width(text) / 2)
				drawText(text, textX, textY)
				textY += textRenderer.lineHeight + lineSpacing.f
			}
		}

		Right  -> {
			for (text in lines) {
				val textX = x.f - textRenderer.width(text)
				drawText(text, textX, textY)
				textY += textRenderer.lineHeight + lineSpacing.f
			}
		}
	}

}

fun drawTextLines(
	poseStack: PoseStack,
	lines: List<Component>,
	x: Number,
	y: Number,
	lineSpacing: Number = 1,
	color: Color = Color.BLACK,
	shadow: Boolean = false,
	align: HorizontalAlign = Left,
	rightToLeft: Boolean = false,
) {
	drawStringLines(
		poseStack,
		ArrayList<String>().apply { lines.forEach { add(it.string) } },
		x,
		y,
		lineSpacing,
		color,
		shadow,
		align,
		rightToLeft
	)
}