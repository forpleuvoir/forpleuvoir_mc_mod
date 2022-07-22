package forpleuvoir.mc.library.gui.widget

import forpleuvoir.mc.library.gui.foundation.ParentElement

/**
 * 组件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.widget

 * 文件名 Widgets

 * 创建时间 2022/7/23 0:03

 * @author forpleuvoir

 */
inline fun ParentElement.scrollerBar(
	width: Double = 8.0,
	height: Double = 8.0,
	horizontal: Boolean = false,
	noinline percent: () -> Double,
	noinline maxAmount: () -> Double,
	noinline amountConsumer: (Double) -> Unit = {},
	noinline amountDelta: () -> Double = { 1.0 },
	scope: ScrollerBar.() -> Unit = {},
): ScrollerBar {
	return this.addElement(ScrollerBar().apply {
		this.width = width
		this.height = height
		this.horizontal = horizontal
		this.percent = percent
		this.maxAmount = maxAmount
		this.amountDelta = amountDelta
		this.amountConsumer = amountConsumer
		scope()
	})
}

inline fun ParentElement.dropMenu(
	noinline items: () -> List<String>,
	currentItem: String,
	noinline expandSize: () -> Int = { 10 },
	scope: DropMenu.() -> Unit = {},
): DropMenu {
	return this.addElement(DropMenu(items, currentItem, expandSize).apply(scope))
}