package forpleuvoir.mc.library.gui.foundation.layout

import forpleuvoir.mc.library.gui.foundation.Margin
import forpleuvoir.mc.library.gui.foundation.ParentElement
import forpleuvoir.mc.library.gui.widget.ScrollerBar

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.foundation.layout

 * 文件名 Layouts

 * 创建时间 2022/7/23 0:08

 * @author forpleuvoir

 */
inline fun ParentElement.column(
	padding: Margin = Margin(),
	margin: Margin = Margin(),
	scope: ColumnLayout.() -> Unit,
): ColumnLayout =
	this.addElement(ColumnLayout().apply {
		padding(padding)
		margin(margin)
		scope()
	})


inline fun ParentElement.row(
	padding: Margin = Margin(),
	margin: Margin = Margin(),
	scope: RowLayout.() -> Unit,
): RowLayout =
	this.addElement(RowLayout().apply {
		padding(padding)
		margin(margin)
		scope()
	})

inline fun ParentElement.list(
	width: Double = 0.0,
	height: Double = 0.0,
	horizontal: Boolean = false,
	scrollerBarScope: ScrollerBar.() -> Unit = {},
	padding: Margin = Margin(),
	margin: Margin = Margin(),
	scope: ListLayout.() -> Unit,
): ListLayout =
	this.addElement(ListLayout(width, height).apply {
		scrollerBar.apply(scrollerBarScope)
		this.horizontal = horizontal
		padding(padding)
		margin(margin)
		scope()
	})
