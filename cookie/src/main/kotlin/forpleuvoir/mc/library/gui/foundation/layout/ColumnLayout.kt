package forpleuvoir.mc.library.gui.foundation.layout

import forpleuvoir.mc.library.gui.foundation.ParentElement
import forpleuvoir.mc.library.utils.d
import forpleuvoir.mc.library.utils.math.Vector3d

/**
 * 水平布局

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.foundation.layout

 * 文件名 ColumnLayout

 * 创建时间 2022/7/18 13:13

 * @author forpleuvoir

 */
class ColumnLayout:Layout() {
	override fun align() {
		var width = 0.0
		var height = 0.0
		getChildren().forEach { element ->
			val preElement = preNotFixedElement(children, element)
			val preElementBottom = preElement?.bottom?.d ?: 0.0
			val marginTop = (if (preElement != null) preElementBottom else padding.top) + element.margin.top
			if (!element.fixed) element.setPosition(this.position + Vector3d(padding.left + element.margin.left, marginTop))
			if (element is ParentElement) element.init()
			height = (element.bottom.d + element.margin.bottom) - this.y + padding.bottom
			if (element.width + element.margin.horizontal + padding.horizontal > width) {
				width = element.width + element.margin.horizontal + padding.horizontal
			}
		}
		this.width = width.d
		this.height = height.d
	}


}