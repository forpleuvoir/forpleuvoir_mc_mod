package forpleuvoir.mc.library.gui.widget.button

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.drawTexture
import forpleuvoir.mc.library.gui.icon.Icon
import forpleuvoir.mc.library.gui.texture.BUTTON_0
import forpleuvoir.mc.library.gui.texture.BUTTON_0_HOVERED
import forpleuvoir.mc.library.gui.texture.BUTTON_0_PRESSED

/**
 * 图标按钮

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.widget.button

 * 文件名 IconButton

 * 创建时间 2022/7/22 0:35

 * @author forpleuvoir

 */
open class IconButton(var icon: Icon) : Button() {

	override var width: Double = 20.0

	override fun onRender(poseStack: PoseStack, delta: Double) {
		drawBackground(poseStack, delta)
		renderIcon(poseStack, delta, icon)
	}

	var renderIcon: (poseStack: PoseStack, delta: Double, icon: Icon) -> Unit = { poseStack, delta, icon ->
		val iconY = status(this.y + (height - icon.size) / 2 - 1, this.y + (height - icon.size) / 2 - 1, this.y + (height - icon.size) / 2)
		icon.position(this.x + (width - icon.size) / 2, iconY)
		icon.size(icon.size)
		icon.render(poseStack, delta)
	}

	override fun drawBackground(poseStack: PoseStack, delta: Number) {
		val texture = status(BUTTON_0, BUTTON_0_HOVERED, BUTTON_0_PRESSED)
		drawTexture(poseStack, x, y, width, height, texture, buttonColor)
	}
}