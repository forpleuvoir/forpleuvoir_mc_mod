package forpleuvoir.mc.library.gui.foundation.layout

import forpleuvoir.mc.library.gui.foundation.AbstractParentElement
import forpleuvoir.mc.library.gui.foundation.Element

/**
 * 布局

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.foundation.layout

 * 文件名 Layout

 * 创建时间 2022/7/18 13:09

 * @author forpleuvoir

 */
abstract class Layout : AbstractParentElement() {

	override fun init() {
		align()
	}

	/**
	 * 对齐元素
	 */
	abstract fun align()

	/**
	 * 上一个非固定的元素
	 * @param children LinkedList<Element>
	 * @param element Element
	 * @return Element?
	 */
	protected fun preNotFixedElement(children: List<Element>, element: Element): Element? {
		val indexOf = children.indexOf(element)
		if (indexOf < 1) return null
		val e = children[indexOf - 1]
		return if (e.fixed) {
			preNotFixedElement(children, e)
		} else e
	}

}