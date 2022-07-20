package forpleuvoir.mc.library.gui.widget

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.HandleStatus
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Interrupt
import forpleuvoir.mc.library.gui.foundation.drawTexture
import forpleuvoir.mc.library.gui.texture.SCROLLER_BACKGROUND
import forpleuvoir.mc.library.gui.texture.SCROLLER_BAR_HORIZONTAL
import forpleuvoir.mc.library.gui.texture.SCROLLER_BAR_VERTICAL
import forpleuvoir.mc.library.utils.clamp
import forpleuvoir.mc.library.utils.d

/**
 * 滚动条

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.widget

 * 文件名 ScrollerBar

 * 创建时间 2022/7/21 1:04

 * @author forpleuvoir

 */
open class ScrollerBar : ClickableElement() {

	/**
	 * 是否为水平滚动条
	 */
	var horizontal: Boolean = false

	override var width: Double = 8.0
		get() {
			return if (shouldRender) field else 0.0
		}
		set(value) {
			field = value.coerceAtLeast(if (shouldRender) 8.0 else 0.0)
		}

	override var height: Double = 8.0
		get() {
			return if (shouldRender) field else 0.0
		}
		set(value) {
			field = value.coerceAtLeast(if (shouldRender) 8.0 else 0.0)
		}

	/**
	 * 滚动条当前的计量
	 */
	var amount = 0.0
		set(value) {
			field = value.clamp(0, maxAmount())
			amountConsumer(field)
		}

	var amountConsumer: (Double) -> Unit = {}

	/**
	 * 滚动条最大计量
	 */
	var maxAmount: () -> Double = { 0.0 }

	/**
	 * 页面占总数的百分比
	 *
	 * 当前页面条目/总数
	 */
	var percent: () -> Double = { 0.0 }

	var amountDelta: () -> Double = { 6.0 }

	open val shouldRender: Boolean get() = maxAmount() > 0

	open operator fun minusAssign(amount: Double) {
		this.amount -= amount
	}

	open operator fun plusAssign(amount: Double) {
		this.amount += amount
	}

	protected open fun setAmountFromMouse(mouseX: Number, mouseY: Number) {
		val percent: Double = if (!horizontal) {
			(mouseY.d - this.y) / height
		} else {
			(mouseX.d - this.x) / width
		}
		amount = maxAmount() * percent
	}

	override fun onMouseClick(mouseX: Number, mouseY: Number, button: Int): HandleStatus {
		super.mouseClick.invoke(mouseX, mouseY, button)
		setAmountFromMouse(mouseX, mouseY)
		return Interrupt
	}

	override fun onMouseDragging(mouseX: Number, mouseY: Number, button: Int, deltaX: Number, deltaY: Number): HandleStatus {
		setAmountFromMouse(mouseX, mouseY)
		return Interrupt
	}

	override fun onMouseScrolling(mouseX: Number, mouseY: Number, amount: Number): HandleStatus {
		this -= amount.d * amountDelta()
		return Interrupt
	}

	override fun onRender(poseStack: PoseStack, delta: Double) {
		if (shouldRender) {
			renderBackground(poseStack, delta)
			renderBar(poseStack, delta)
		}
	}

	protected open fun renderBar(poseStack: PoseStack, delta: Number) {
		if (!horizontal) {
			val height = (percent() * this.height)
			val maxScrollLength = this.height - height
			val posY = this.y + ((this.amount / this.maxAmount()) * maxScrollLength).toInt()
			drawTexture(poseStack, x, posY, width, height, SCROLLER_BAR_VERTICAL)
		} else {
			val width = (percent() * this.width)
			val maxScrollLength = this.width - width
			val posX = this.x + ((this.amount / this.maxAmount()) * maxScrollLength).toInt()
			drawTexture(poseStack, posX, y, width, height, SCROLLER_BAR_HORIZONTAL)
		}
	}

	protected open fun renderBackground(poseStack: PoseStack, delta: Number) {
		drawTexture(poseStack, x, y, width, height, SCROLLER_BACKGROUND)
	}

}