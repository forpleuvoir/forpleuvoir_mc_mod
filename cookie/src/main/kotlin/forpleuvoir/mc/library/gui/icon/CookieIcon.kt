package forpleuvoir.mc.library.gui.icon

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.*
import forpleuvoir.mc.library.gui.texture.COOKIE_ICON_TEXTURE
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.color.Color4f
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
	override val color: Color<out Number> = Color4f.WHITE,
	override val hoverColor: Color<out Number> = Color4f.BLACK,
	override val pressedColor: Color<out Number> = Color4f(0.8f, 0.8f, 0.8f),
	override val texture: ResourceLocation = COOKIE_ICON_TEXTURE,
	override var textureSize: Int = 16,
	override val textureHeight: Int = 256,
	override val textureWidth: Int = 256,
) : Icon {

	override val render: (poseStack: PoseStack, delta: Double) -> Unit = { poseStack: PoseStack, delta: Double ->
		onRender(poseStack, delta)
	}

	override fun onRender(poseStack: PoseStack, delta: Double) {
		setShaderTexture(texture)
		enableBlend()
		defaultBlendFunc()
		enableDepthTest()
		if (mouseX.d >= x.d && mouseX.d <= x.d + size.d && mouseY.d >= y.d && mouseY.d <= y.d + size.d) setShaderColor(hoverColor)
		else setShaderColor(color)
		drawTexture(poseStack, x, y, size, size, u, v, this.textureSize.i, this.textureSize.i, textureWidth, textureHeight)
		setShaderColor(Color4f.WHITE)
		disableBlend()
	}


}