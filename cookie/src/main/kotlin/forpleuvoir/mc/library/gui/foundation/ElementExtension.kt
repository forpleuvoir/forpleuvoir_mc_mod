package forpleuvoir.mc.library.gui.foundation

import forpleuvoir.mc.library.utils.d
import forpleuvoir.mc.library.utils.ifc
import forpleuvoir.mc.library.utils.mc
import java.util.stream.Stream

/**
 * 元素扩展

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.foundation

 * 文件名 ElementExtension

 * 创建时间 2022/7/17 19:49

 * @author forpleuvoir

 */
val mouseX: Double get() = mc.mouseHandler.xpos() * mc.window.guiScaledWidth.d / mc.window.width.d

val mouseY: Double get() = mc.mouseHandler.ypos() * mc.window.guiScaledHeight.d / mc.window.height.d

/**
 * 当鼠标位于此元素[Element]内部时调用
 * @param action [@kotlin.ExtensionFunctionType] Function1<Element, Unit>
 */
inline fun <T : Element> T.mouseHover(action: T.() -> Unit) = isMouseOvered(mouseX, mouseY).ifc { action() }

/**
 * 鼠标是否在此元素内
 * @receiver T
 * @return Boolean
 */
fun <T : Element> T.mouseHover(): Boolean = isMouseOvered(mouseX, mouseY)

/**
 * 转换为排序后的元素
 * @receiver Collection<Element>
 * @return Stream<Element>?
 */
fun Collection<Element>.sort(): Stream<Element> {
	return this.stream().sorted { o1, o2 -> o1.handlePriority - o2.handlePriority }
}

/**
 * 转换为反向排序后的元素
 * @receiver Collection<Element>
 * @return Stream<Element>?
 */
fun Collection<Element>.reverseOrder(): Stream<Element> {
	return this.stream().sorted { o1, o2 -> o2.handlePriority - o1.handlePriority }
}

/**
 * 转换为排序后的元素
 * @receiver Collection<Element>
 * @return Stream<Element>?
 */
fun Collection<Drawable>.drawableSort(): Stream<Drawable> {
	return this.stream().sorted { o1, o2 -> o1.zOffset - o2.zOffset }
}

/**
 * 转换为反向排序后的元素
 * @receiver Collection<Element>
 * @return Stream<Element>?
 */
fun Collection<Drawable>.drawableReverseOrder(): Stream<Drawable> {
	return this.stream().sorted { o1, o2 -> o2.zOffset - o1.zOffset }
}