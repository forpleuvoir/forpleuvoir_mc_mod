package forpleuvoir.mc.library.gui.foundation

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.api.Initializable
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Interrupt
import forpleuvoir.mc.library.utils.Direction
import forpleuvoir.mc.library.utils.d
import forpleuvoir.mc.library.utils.i
import forpleuvoir.mc.library.utils.math.Vector3
import net.minecraft.client.renderer.texture.Tickable
import net.minecraft.network.chat.Component

/**
 * 元素

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.foundation

 * 文件名 Element

 * 创建时间 2022/7/17 17:21

 * @author forpleuvoir

 */
interface Element : Drawable, Tickable, Initializable {

	/**
	 * 位置
	 */
	val position: Vector3<Double>

	override var visible: Boolean

	/**
	 * 可用的
	 */
	var active: Boolean

	/**
	 * 父元素
	 */
	var parent: ParentElement?

	/**
	 * 固定的
	 *
	 * 被固定的元素,不会被父元素调整位置
	 */
	val fixed: Boolean get() = false

	var x: Double
		get() = position.x
		set(value) {
			position.x = value
		}

	var y: Double
		get() = position.y
		set(value) {
			position.y = value
		}

	var z: Int
		get() = position.z.i
		set(value) {
			position.z = value.d
		}

	override var zOffset: Int
		get() = position.z.i
		set(value) {
			position.z = value.d
		}

	/**
	 * 处理优先级，越低越先被处理
	 */
	var handlePriority: Int

	/**
	 * 宽
	 */
	var width: Double

	/**
	 * 高
	 */
	var height: Double

	/**
	 * 提示
	 */
	var tip: (() -> Component)?

	/**
	 * 是否为空tip
	 * @return Boolean
	 */
	fun isEmptyTip(): Boolean {
		tip?.invoke()?.let { return it.string.isEmpty() }
		return true
	}

	/**
	 * 提示出现的位置 为空则自动选择合适的位置 优先级 up->right->down->left
	 */
	var tipDirection: (() -> Direction)?

	/**
	 * 左侧的位置
	 */
	val left: Double get() = position.x

	/**
	 * 右侧位置
	 */
	val right: Double get() = position.x.d + width.d

	/**
	 * 顶部位置
	 */
	val top: Double get() = position.y

	/**
	 * 底部位置
	 */
	val bottom: Double get() = position.y.d + height.d

	/**
	 * 外边距
	 */
	val margin: Margin

	/**
	 * 内边距
	 */
	val padding: Margin

	fun setPosition(x: Number, y: Number, z: Number) {
		this.position.set(x.d, y.d, z.d)
	}

	fun setPosition(vector3: Vector3<out Number>) = setPosition(vector3.x, vector3.y, vector3.z)

	fun deltaPosition(deltaX: Number, deltaY: Number, deltaZ: Number) {
		this.position.plusAssign(deltaX.d, deltaY.d, deltaZ.d)
	}

	fun deltaPosition(deltaVector3: Vector3<out Number>) = deltaPosition(deltaVector3.x, deltaVector3.y, deltaVector3.z)

	/**
	 * 鼠标是否在此元素[Element]内部
	 * @param mouseX Number
	 * @param mouseY Number
	 * @return Boolean
	 */
	fun isMouseOvered(mouseX: Number, mouseY: Number): Boolean =
		mouseX.d >= this.left.d && mouseX.d <= this.right.d && mouseY.d >= this.top.d && mouseY.d <= this.bottom.d

	/**
	 *
	 * 以下方法和对应作为对象的高阶函数
	 *
	 * 应该是高阶函数作为被系统内部调用的方法
	 *
	 * 普通方法由子类实现
	 *
	 * 如果在DSL场景需要给对应方法添加代码，只需要给对应的高阶函数重新赋值
	 *
	 * 子类重写方法,调用者调用高阶函数
	 *
	 * 例:
	 *
	 * render={poseStack,delta ->
	 *
	 *     render(poseStack,delta)
	 *     code
	 * }
	 *
	 */
	var tick: () -> Unit

	override fun tick() {}

	/**
	 * 渲染元素
	 * @param poseStack PoseStack
	 * @param delta Double 距离上一帧数渲染时间
	 */
	override var render: (poseStack: PoseStack, delta: Double) -> Unit

	/**
	 * 渲染元素
	 * @param poseStack PoseStack
	 * @param delta Double 距离上一帧数渲染时间
	 */
	override fun onRender(poseStack: PoseStack, delta: Double)

	/**
	 * 鼠标移动
	 * @param mouseX Number
	 * @param mouseY Number
	 */
	var mouseMove: (mouseX: Number, mouseY: Number) -> Unit

	/**
	 * 鼠标移动
	 * @param mouseX Number
	 * @param mouseY Number
	 */
	fun onMouseMove(mouseX: Number, mouseY: Number) {}

	/**
	 * 鼠标点击
	 * @param button Int
	 * @param mouseX Number
	 * @param mouseY Number
	 * @return 是否处理之后的同类操作
	 */
	var mouseClick: (mouseX: Number, mouseY: Number, button: Int) -> HandleStatus

	/**
	 * 鼠标点击
	 * @param button Int
	 * @param mouseX Number
	 * @param mouseY Number
	 * @return 是否处理之后的同类操作
	 */
	fun onMouseClick(mouseX: Number, mouseY: Number, button: Int): HandleStatus = Interrupt

	/**
	 * 鼠标释放
	 * @param button Int
	 * @param mouseX Number
	 * @param mouseY Number
	 * @return 是否处理之后的同类操作
	 */
	var mouseRelease: (mouseX: Number, mouseY: Number, button: Int) -> HandleStatus

	/**
	 * 鼠标释放
	 * @param button Int
	 * @param mouseX Number
	 * @param mouseY Number
	 * @return 是否处理之后的同类操作
	 */
	fun onMouseRelease(mouseX: Number, mouseY: Number, button: Int): HandleStatus = Interrupt

	/**
	 * 鼠标拖动
	 * @param mouseX Number
	 * @param mouseY Number
	 * @param button Int
	 * @param deltaX Number
	 * @param deltaY Number
	 * @return 是否处理之后的同类操作
	 */
	var mouseDragging: (mouseX: Number, mouseY: Number, button: Int, deltaX: Number, deltaY: Number) -> HandleStatus

	/**
	 * 鼠标拖动
	 * @param mouseX Number
	 * @param mouseY Number
	 * @param button Int
	 * @param deltaX Number
	 * @param deltaY Number
	 * @return 是否处理之后的同类操作
	 */
	fun onMouseDragging(mouseX: Number, mouseY: Number, button: Int, deltaX: Number, deltaY: Number): HandleStatus = Interrupt

	/**
	 * 鼠标滚动
	 * @param mouseX Number
	 * @param mouseY Number
	 * @param amount Number
	 * @return 是否处理之后的同类操作
	 */
	var mouseScrolling: (mouseX: Number, mouseY: Number, amount: Number) -> HandleStatus

	/**
	 * 鼠标滚动
	 * @param mouseX Number
	 * @param mouseY Number
	 * @param amount Number
	 * @return 是否处理之后的同类操作
	 */
	fun onMouseScrolling(mouseX: Number, mouseY: Number, amount: Number): HandleStatus = Interrupt

	/**
	 * 按键按下
	 * @param keyCode Int
	 * @param modifiers Int
	 * @return 是否处理之后的同类操作
	 */
	var keyPress: (keyCode: Int, modifiers: Int) -> HandleStatus

	/**
	 * 按键按下
	 * @param keyCode Int
	 * @param modifiers Int
	 * @return 是否处理之后的同类操作
	 */
	fun onKeyPress(keyCode: Int, modifiers: Int): HandleStatus = Interrupt

	/**
	 * 按键释放
	 * @param keyCode Int
	 * @param modifiers Int
	 * @return 是否处理之后的同类操作
	 */
	var keyRelease: (keyCode: Int, modifiers: Int) -> HandleStatus

	/**
	 * 按键释放
	 * @param keyCode Int
	 * @param modifiers Int
	 * @return 是否处理之后的同类操作
	 */
	fun onKeyRelease(keyCode: Int, modifiers: Int): HandleStatus = Interrupt

	/**
	 * 字符输入
	 * @param chr Char
	 * @param modifiers Int
	 * @return 是否处理之后的同类操作
	 */
	var charTyped: (chr: Char, modifiers: Int) -> HandleStatus

	/**
	 * 字符输入
	 * @param chr Char
	 * @param modifiers Int
	 * @return 是否处理之后的同类操作
	 */
	fun onCharTyped(chr: Char, modifiers: Int): HandleStatus = Interrupt

}