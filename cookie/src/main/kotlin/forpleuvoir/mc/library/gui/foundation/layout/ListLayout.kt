package forpleuvoir.mc.library.gui.foundation.layout

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.HandleStatus
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Continue
import forpleuvoir.mc.library.gui.foundation.ParentElement
import forpleuvoir.mc.library.gui.widget.ScrollerBar
import forpleuvoir.mc.library.utils.d
import forpleuvoir.mc.library.utils.math.Vector3d

/**
 *
 * 列表布局
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.foundation.layout

 * 文件名 ListLayout

 * 创建时间 2022/7/21 1:38

 * @author forpleuvoir

 */
class ListLayout(width: Double = 0.0, height: Double = 0.0) : Layout() {

	private var fixedWidth: Boolean = false
	private var fixedHeight: Boolean = false
	var scrollerBar: ScrollerBar = ScrollerBar()
	var horizontal: Boolean by scrollerBar::horizontal


	override fun onRender(poseStack: PoseStack, delta: Double) {
		align()
		super.onRender(poseStack, delta)
	}

	init {
		fixedWidth = width != 0.0
		fixedHeight = height != 0.0
		this.width = width
		this.height = height
		this.addElement(scrollerBar.apply { zOffset = -1 })
	}

	val contentSize: Double
		get() {
			return if (horizontal) {
				if (fixedWidth) height - scrollerBar.height
				else width
			} else {
				if (fixedHeight) width - scrollerBar.width
				else height
			}
		}

	override fun align() {
		val alignChildren = children.filter { it != scrollerBar }
		if (!horizontal) {
			var width = 0.0
			var height = this.padding.vertical
			alignChildren.forEach { element ->
				val preElement =
					if (preNotFixedElement(alignChildren, element) != scrollerBar) preNotFixedElement(alignChildren, element) else null
				val preElementBottom = preElement?.bottom?.d ?: (this.top.d + this.padding.top.d)
				val marginTop =
					(if (preElement != null) preElementBottom else (this.top.d + this.padding.top.d) - scrollerBar.amount) + element.margin.top
				if (element is ParentElement) element.init()
				if (!element.fixed) {
					element.setPosition(Vector3d(this.left.d + padding.left + element.margin.left, marginTop))
					element.visible = !(element.bottom.d <= this.top.d || element.top.d >= this.bottom.d)
					height += element.height + element.margin.vertical
					if (element.height + element.margin.vertical + padding.vertical > width) {
						width = element.width + element.margin.horizontal + padding.horizontal
					}
				}
			}
			if (!fixedWidth)
				this.width = width + scrollerBar.width
			if (!fixedHeight)
				this.height = height
			scrollerBar.apply {
				this.height = this@ListLayout.height
				x = this@ListLayout.right.d - scrollerBar.width
				y = this@ListLayout.top.d
				maxAmount = { (height - this@ListLayout.height).coerceAtLeast(0.0) }
				percent = { (this@ListLayout.height / height).coerceAtMost(1.0) }
			}
		} else {
			var width = this.padding.vertical
			var height = 0.0
			alignChildren.forEach { element ->
				var preElement = if (elementPre(element) != scrollerBar) elementPre(element) else null
				preElement?.let {
					if (it.fixed) preElement = null
				}
				val preElementRight = preElement?.right?.d ?: (this.left.d + this.padding.left)
				val marginLeft =
					(if (preElement != null) preElementRight else (this.left.d + this.padding.left) - scrollerBar.amount) + element.margin.left
				if (element is ParentElement) element.init()
				if (!element.fixed) {
					element.setPosition(Vector3d(marginLeft, this.top.d + padding.top + element.margin.top))
					element.visible = !(element.right.d <= this.left.d || element.left.d >= this.right.d)
					width += element.width + element.margin.horizontal
					if (element.height + padding.horizontal + element.margin.horizontal > height) {
						height = element.height + element.margin.horizontal + padding.horizontal
					}
				}
			}
			if (!fixedWidth)
				this.width = width
			if (!fixedHeight)
				this.height = height + scrollerBar.height
			scrollerBar.apply {
				this.width = this@ListLayout.width
				x = this@ListLayout.left.d
				y = this@ListLayout.bottom.d - scrollerBar.height
				maxAmount = { (width - this@ListLayout.width).coerceAtLeast(0.0) }
				percent = { (this@ListLayout.width / width).coerceAtMost(1.0) }
			}
		}

	}

	override fun onMouseScrolling(mouseX: Number, mouseY: Number, amount: Number): HandleStatus {
		return if (hovered?.mouseScrolling?.invoke(mouseX, mouseY, amount) == Continue) {
			scrollerBar.mouseScrolling(mouseX, mouseY, amount)
		} else hovered?.mouseScrolling?.invoke(mouseX, mouseY, amount) ?: scrollerBar.mouseScrolling(mouseX, mouseY, amount)
	}


}