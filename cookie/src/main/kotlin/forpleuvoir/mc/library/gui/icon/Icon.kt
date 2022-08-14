package forpleuvoir.mc.library.gui.icon

import forpleuvoir.mc.library.gui.foundation.Drawable
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.d
import net.minecraft.resources.ResourceLocation

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.icon

 * 文件名 Icon

 * 创建时间 2022/7/21 13:15

 * @author forpleuvoir

 */
interface Icon : Drawable {

	var x: Double
	var y: Double
	var size: Double
	val color: Color get() = Color.WHITE
	val hoveredColor: Color get() = Color.BLACK
	val pressedColor: Color get() = Color(0.8f, 0.8f, 0.8f)
	var currentColor: () -> Color
	val u: Int
	val v: Int
	val texture: ResourceLocation
	val textureSize: Int
	val textureWidth: Int get() = 256
	val textureHeight: Int get() = 256

	fun position(x: Number, y: Number) {
		this.x = x.d
		this.y = y.d
	}

	fun size(size: Number) {
		this.size = size.d
	}

}