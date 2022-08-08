package forpleuvoir.mc.library.gui.icon

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.*
import forpleuvoir.mc.library.gui.texture.COOKIE_ICON_TEXTURE
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.color.RGBColor
import forpleuvoir.mc.library.utils.d
import forpleuvoir.mc.library.utils.i
import net.minecraft.resources.ResourceLocation

/**
 * Cookie图标

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.icon

 * 文件名 CookieIcon

 * 创建时间 2022/7/21 13:22

 * @author forpleuvoir

 */
class CookieIcon(
	override var x: Double = 0.0,
	override var y: Double = 0.0,
	override var size: Double = 16.0,
	override val u: Int,
	override val v: Int,
	override val color: Color = Color.WHITE,
	override val hoveredColor: Color = Color.BLACK,
	override val pressedColor: Color = RGBColor(0.8f, 0.8f, 0.8f),
	override val texture: ResourceLocation = COOKIE_ICON_TEXTURE,
	override var textureSize: Int = 16,
	override val textureHeight: Int = 256,
	override val textureWidth: Int = 256,
) : Icon {

	override var currentColor: () -> Color = {
		if (mouseX.d >= x.d && mouseX.d <= x.d + size.d && mouseY.d >= y.d && mouseY.d <= y.d + size.d) hoveredColor
		else color
	}

	override val render: (poseStack: PoseStack, delta: Double) -> Unit = { poseStack: PoseStack, delta: Double ->
		onRender(poseStack, delta)
	}

	override fun onRender(poseStack: PoseStack, delta: Double) {
		setShaderTexture(texture)
		enableBlend()
		defaultBlendFunc()
		enableDepthTest()
		setShaderColor(currentColor())
		drawTexture(poseStack, x, y, size, size, u, v, this.textureSize.i, this.textureSize.i, textureWidth, textureHeight)
		setShaderColor(Color.WHITE)
		disableBlend()
	}


}