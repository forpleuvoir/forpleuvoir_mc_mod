package forpleuvoir.mc.library.gui.foundation

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.screen.ScreenHandler
import forpleuvoir.mc.library.utils.Direction
import forpleuvoir.mc.library.utils.math.Vector3
import forpleuvoir.mc.library.utils.math.Vector3d
import forpleuvoir.mc.library.utils.text.Text

/**
 * 元素基础实现

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.foundation

 * 文件名 AbstractElement

 * 创建时间 2022/7/17 22:57

 * @author forpleuvoir

 */
abstract class AbstractElement : Element {

	override val position: Vector3<Double> = Vector3d()

	override var visible: Boolean = true

	override var active: Boolean = true

	override var parent: ParentElement? = ScreenHandler.current

	override var fixed: Boolean = false

	override var handlePriority: Int = 0

	override var width: Double = 0.0

	override var height: Double = 0.0

	override var tip: (() -> Text)? = null

	override var tipDirection: (() -> Direction)? = null

	override val margin: Margin = Margin()

	fun margin(margin: Margin) = this.margin.set(margin)

	override val padding: Margin = Margin()

	fun padding(padding: Margin) = this.margin.set(padding)

	override fun init() {}

	override var tick: () -> Unit = ::tick

	override var render: (poseStack: PoseStack, delta: Double) -> Unit = ::onRender

	override var mouseMove: (mouseX: Number, mouseY: Number) -> Unit = ::onMouseMove

	override var mouseClick: (mouseX: Number, mouseY: Number, button: Int) -> HandleStatus = ::onMouseClick

	override var mouseRelease: (mouseX: Number, mouseY: Number, button: Int) -> HandleStatus = ::onMouseRelease

	override var mouseDragging: (mouseX: Number, mouseY: Number, button: Int, deltaX: Number, deltaY: Number) -> HandleStatus = ::onMouseDragging

	override var mouseScrolling: (mouseX: Number, mouseY: Number, amount: Number) -> HandleStatus = ::onMouseScrolling

	override var keyPress: (keyCode: Int, modifiers: Int) -> HandleStatus = ::onKeyPress

	override var keyRelease: (keyCode: Int, modifiers: Int) -> HandleStatus = ::onKeyRelease

	override var charTyped: (chr: Char, modifiers: Int) -> HandleStatus = ::onCharTyped
}