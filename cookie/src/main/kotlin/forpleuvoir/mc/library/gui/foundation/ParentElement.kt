package forpleuvoir.mc.library.gui.foundation

import forpleuvoir.mc.library.api.Initializable

/**
 * 父元素

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.foundation

 * 文件名 ParentElement

 * 创建时间 2022/7/17 19:59

 * @author forpleuvoir

 */
interface ParentElement : Element, Initializable {

	/**
	 * 获取所有子元素集合
	 * @return List<Element>
	 */
	fun getChildren(): List<Element>

	/**
	 * 当前聚焦的元素
	 */
	var focused: Element?

	/**
	 * 是否处于拖拽状态
	 */
	var dragging: Boolean

	/**
	 * 鼠标悬浮的元素
	 */
	val hovered: Element?

	/**
	 * 拥有tip的鼠标悬浮元素
	 */
	val hoveredTip: Element?

	/**
	 * 添加元素
	 * @param element Element
	 * @return Element
	 */
	fun <E : Element> addElement(element: E): E

	/**
	 * 所选元素的上一个元素
	 * @param element Element 所选元素
	 * @return Element? 上一个元素
	 */
	fun elementPre(element: Element): Element?

	/**
	 * 所选元素的下一个元素
	 * @param element Element 所选元素
	 * @return Element? 下一个元素
	 */
	fun elementNext(element: Element): Element?

	/**
	 * 所选元素的下标
	 * @param element Element 所选元素
	 * @return Int
	 */
	fun elementIndexOf(element: Element): Int

	/**
	 * 删除所选元素
	 * @param element Element 所选元素
	 * @return Boolean 是否删除成功
	 */
	fun removeElement(element: Element): Boolean

}