package forpleuvoir.mc.library.gui.texture

import forpleuvoir.mc.library.gui.texture.GuiTexture.Corner
import forpleuvoir.mc.library.utils.resources

/**
 * 材质

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.texture

 * 文件名 CookieTextures

 * 创建时间 2022/7/21 0:06

 * @author forpleuvoir

 */
val COOKIE_TEXTURE = resources("texture/gui/cookie_widget.png")
val COOKIE_ICON_TEXTURE = resources("texture/gui/cookie_icon.png")

private fun texture(corner: Corner, u: Int, v: Int, regionWidth: Int, regionHeight: Int): GuiTexture {
	return GuiTexture(COOKIE_TEXTURE, corner, u, v, regionWidth, regionHeight, 256, 256)
}

val BUTTON_0 = texture(Corner(2, 2, 2, 4), 0, 0, 16, 16)

val BUTTON_0_HOVERED = texture(Corner(2, 2, 2, 3), 0, 16, 16, 16)

val BUTTON_0_PRESSED = texture(Corner(2, 2, 3, 2), 0, 32, 16, 16)

val BUTTON_1 = texture(Corner(3, 3, 3, 4), 16, 0, 16, 16)

val BUTTON_1_HOVERED = texture(Corner(3, 3, 3, 4), 16, 16, 16, 16)

val BUTTON_1_PRESSED = texture(Corner(3, 3, 4, 3), 16, 32, 16, 16)

val SCROLLER_BACKGROUND = texture(Corner(2), 32, 0, 16, 16)

val SCROLLER_BAR_HORIZONTAL = texture(Corner(2, 2, 2, 3), 0, 0, 16, 15)

val SCROLLER_BAR_VERTICAL = texture(Corner(2, 2, 2, 4), 0, 0, 16, 16)

val BORDER = texture(Corner(3, 3, 4, 3), 0, 48, 32, 32)

val TEXT_FIELD = texture(Corner(4, 4, 5, 4), 48, 0, 32, 32)