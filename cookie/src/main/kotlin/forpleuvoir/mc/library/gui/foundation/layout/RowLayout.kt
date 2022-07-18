package forpleuvoir.mc.library.gui.foundation.layout

import forpleuvoir.mc.library.gui.foundation.ParentElement
import forpleuvoir.mc.library.utils.d
import forpleuvoir.mc.library.utils.i
import forpleuvoir.mc.library.utils.math.Vector3d

/**
 * 垂直布局

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.foundation.layout

 * 文件名 RowLayout

 * 创建时间 2022/7/18 13:18

 * @author forpleuvoir

 */
class RowLayout : Layout() {

	override fun align() {
		var width = this.padding.vertical
		var height = 0.0
		getChildren().forEach { element ->
			val preElement = preNotFixedElement(children, element)
			val preElementRight = preElement?.right?.d ?: (this.left.d + this.padding.left)
			val marginLeft = (if (preElement != null) preElementRight else this.left.d + this.padding.left) + element.margin.left
			if (element is ParentElement) element.init()
			if (!element.fixed) {
				element.setPosition(Vector3d(marginLeft, this.top.d + padding.top + element.margin.top))
				width += element.width + element.margin.horizontal
				if (element.height + padding.horizontal + element.margin.horizontal > height) {
					height = element.height + element.margin.horizontal + padding.horizontal
				}
			}
		}
		this.width = width.d
		this.height = height.d
	}

}