package forpleuvoir.mc.library.gui.foundation

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Continue
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Interrupt
import forpleuvoir.mc.library.utils.d
import forpleuvoir.mc.library.utils.math.Vector3
import forpleuvoir.mc.library.utils.notc
import java.util.*

/**
 * 父元素基础实现

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.foundation

 * 文件名 AbstractParentElement

 * 创建时间 2022/7/17 23:14

 * @author forpleuvoir

 */
abstract class AbstractParentElement : AbstractElement(), ParentElement {

	protected val children: LinkedList<Element> = LinkedList()

	override var focused: Element? = null

	override var dragging: Boolean = true

	override val hovered: Element?
		get() {
			val iterator = getChildren().sort().iterator()
			var element: Element
			do {
				if (!iterator.hasNext()) return null
				element = iterator.next()
			} while (!element.mouseHover())
			return element
		}

	override val hoveredTip: Element?
		get() {
			hovered?.let {
				return if (it is ParentElement && it.hoveredTip != null) it.hoveredTip
				else if (!it.isEmptyTip()) it
				else null
			}
			return null
		}

	override fun getChildren(): List<Element> {
		return children
	}

	override fun <E : Element> addElement(element: E): E {
		children.addLast(element)
		return element
	}

	override fun elementPre(element: Element): Element? {
		val indexOf = children.indexOf(element)
		if (indexOf < 1) return null
		return children[indexOf - 1]
	}

	override fun elementNext(element: Element): Element? {
		val indexOf = children.indexOf(element)
		if (indexOf != -1 && indexOf < children.size - 1) return null
		return children[indexOf + 1]
	}

	override fun elementIndexOf(element: Element): Int {
		return this.children.indexOf(element)
	}

	override fun removeElement(element: Element): Boolean {
		return this.children.remove(element)
	}

	override fun setPosition(vector3: Vector3<out Number>) {
		val delta = this.position.minus(x.d, y.d, z.d)
		super<AbstractElement>.setPosition(x, y, z)
		getChildren().forEach { if (!fixed) it.deltaPosition(delta) }
	}

	override fun deltaPosition(deltaX: Number, deltaY: Number, deltaZ: Number) {
		super<AbstractElement>.deltaPosition(deltaX, deltaY, deltaZ)
		getChildren().forEach { it.deltaPosition(deltaX, deltaY, deltaZ) }
	}

	override fun tick() {
		getChildren().forEach { it.tick.invoke() }
	}

	override fun onRender(poseStack: PoseStack, delta: Double) {
		getChildren().drawableSort().forEach { if (it.visible) it.render.invoke(poseStack, delta) }
	}

	override fun onMouseMove(mouseX: Number, mouseY: Number) {
		hovered?.mouseMove?.invoke(mouseX, mouseY)
	}

	override fun onMouseClick(mouseX: Number, mouseY: Number, button: Int): HandleStatus {
		hovered?.let {
			it.active.notc { return Continue }
		}
		focused = hovered
		if (button == 0) dragging = true
		return if (focused?.active == true) focused?.mouseClick?.invoke(mouseX, mouseY, button) ?: Continue
		else Interrupt
	}

	override fun onMouseRelease(mouseX: Number, mouseY: Number, button: Int): HandleStatus {
		dragging = false
		return focused?.mouseRelease?.invoke(mouseX, mouseY, button) ?: Continue
	}

	override fun onMouseDragging(mouseX: Number, mouseY: Number, button: Int, deltaX: Number, deltaY: Number): HandleStatus {
		focused?.apply {
			if (dragging && button == 0) return mouseDragging.invoke(mouseX, mouseY, button, deltaX, deltaY)
		}
		return Continue
	}

	override fun onMouseScrolling(mouseX: Number, mouseY: Number, amount: Number): HandleStatus {
		return hovered?.mouseScrolling?.invoke(mouseX, mouseY, amount) ?: Continue
	}

	override fun onKeyPress(keyCode: Int, modifiers: Int): HandleStatus {
		return focused?.keyPress?.invoke(keyCode, modifiers) ?: Continue
	}


	override fun onKeyRelease(keyCode: Int, modifiers: Int): HandleStatus {
		return focused?.keyRelease?.invoke(keyCode, modifiers) ?: Continue
	}

	override fun onCharTyped(chr: Char, modifiers: Int): HandleStatus {
		return focused?.onCharTyped(chr, modifiers) ?: Continue
	}
}