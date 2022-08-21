package forpleuvoir.mc.library.gui.text

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.*
import forpleuvoir.mc.library.gui.texture.TEXT_FIELD
import forpleuvoir.mc.library.input.*
import forpleuvoir.mc.library.utils.Direction
import forpleuvoir.mc.library.utils.Direction.*
import forpleuvoir.mc.library.utils.clamp
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.mc
import forpleuvoir.mc.library.utils.textRenderer

/**
 * 文本输入框

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.text

 * 文件名 TextInput

 * 创建时间 2022/7/23 0:29

 * @author forpleuvoir

 */
open class TextInput(text: String = "") : AbstractElement() {

	override var width: Double = 60.0

	override var height: Double = 20.0

	/**
	 * 最大文本长度
	 */
	var maxLength: Int = 255
		set(value) {
			field = value.clamp(0, 65535)
		}

	var textColor: Color = Color.WHITE

	/**
	 * 输入限制
	 */
	var predicate: String.() -> Boolean = { true }

	var text: String = text.substring(0, text.length.coerceAtMost(maxLength))
		set(value) {
			if (!predicate(value)) return
			val old = field
			val newValue = value.substring(0, text.length.coerceAtMost(maxLength))
			if (old != newValue) {
				field = newValue
				textChange(field)
			}
		}

	var cursorPosition: Int = this.text.length
		set(value) {
			val old = field
			if (old != value) {
				field = value.clamp(0, text.length)
			}
		}

	var textChange: (String) -> Unit = {}

	var selectionStart: Int = 0
		set(value) {
			field = value.clamp(0, selectionEnd)
		}

	var selectionEnd: Int = 0
		set(value) {
			field = value.clamp(selectionStart, text.length)
		}

	val selectionText: String get() = text.substring(selectionStart, selectionEnd)

	protected val focused: Boolean get() = this == parent?.focused

	/**
	 * 在光标位置插入文本
	 * @param text String
	 */
	protected open fun insert(text: String) {
		val old = this.text
		val new = old.substring(0, cursorPosition) + text + old.substring(cursorPosition)
		this.text = new
		cursorPosition += text.length
	}

	/**
	 * 粘贴
	 */
	protected open fun paste() {
		insert(mc.keyboardHandler.clipboard)
	}

	/**
	 * 复制选中文本
	 */
	protected open fun copy() {
		mc.keyboardHandler.clipboard = selectionText
	}

	/**
	 * 剪切选中文本
	 */
	protected open fun cut() {
		mc.keyboardHandler.clipboard = selectionText
		this.text = this.text.substring(0, selectionStart) + this.text.substring(selectionEnd)
	}

	/**
	 * 删除单词
	 */
	protected open fun deleteWord() {
		if (selectionStart == selectionEnd) {
			if (cursorPosition > 0) {
				this.text = this.text.substring(0, cursorPosition - 1) + this.text.substring(cursorPosition)
				cursorPosition--
			}
		} else {
			this.text = this.text.substring(0, selectionStart) + this.text.substring(selectionEnd)
			cursorPosition = selectionStart
		}
	}

	/**
	 * 撤回
	 */
	protected open fun undo() {
		TODO("撤回")
	}

	/**
	 * 重做
	 */
	protected open fun redo() {
		TODO("重做")
	}

	/**
	 * 左移动
	 */
	protected open fun wordStart() {
		TODO("光标移动到单词头部")
	}

	/**
	 * 右移动
	 */
	protected open fun wordEnding() {
		TODO("光标移动到单词结尾")
	}

	/**
	 * 光标移动
	 * @param direction Direction 移动方向
	 * @param shift Int 偏移量
	 */
	protected open fun cursorMove(direction: Direction, shift: Int = 1) {
		when (direction) {
			Left  -> cursorPosition -= shift
			Right -> cursorPosition += shift
			else  -> Unit
		}
	}

	/**
	 * 选择文本向左移动
	 */
	protected open fun selectionShl() {

	}

	/**
	 * 选择文本向右移动
	 */
	protected open fun selectionShr() {

	}


	override fun onKeyPress(keyCode: Int, modifiers: Int): HandleStatus {
		if (InputHandler.hasKey(KEY_LEFT_CONTROL) || InputHandler.hasKey(KEY_RIGHT_CONTROL)) {
			when (keyCode) {
				KEY_V         -> paste()
				KEY_C         -> copy()
				KEY_X         -> cut()
				KEY_BACKSPACE -> deleteWord()
				KEY_Z         -> undo()
				KEY_Y         -> redo()
				KEY_LEFT      -> wordStart()
				KEY_RIGHT     -> wordEnding()
			}
		} else if (InputHandler.hasKey(KEY_LEFT_SHIFT) || InputHandler.hasKey(KEY_RIGHT_SHIFT)) {
			when (keyCode) {
				KEY_LEFT  -> selectionShl()
				KEY_RIGHT -> selectionShr()
			}
		} else {
			when (keyCode) {
				KEY_UP    -> cursorMove(Up)
				KEY_DOWN  -> cursorMove(Down)
				KEY_LEFT  -> cursorMove(Left)
				KEY_RIGHT -> cursorMove(Right)
			}
		}
		return super.onKeyPress(keyCode, modifiers)
	}

	override fun onCharTyped(chr: Char, modifiers: Int): HandleStatus {
		insert(chr.toString())
		return super.onCharTyped(chr, modifiers)
	}

	protected open fun drawBackground(poseStack: PoseStack, delta: Number) {
		drawTexture(poseStack, x, y, width, height, TEXT_FIELD)
	}

	protected open fun drawSelectionText(poseStack: PoseStack, delta: Number) {
		val selectionSize: Int = selectionStart - selectionEnd
		if (selectionSize != 0) {
			val startX = x + padding.left + textRenderer.width(text.substring(0, selectionStart))
			val width = textRenderer.width(selectionText)
			val startY = y + padding.top
			drawRect(poseStack, startX, startY, width, textRenderer.lineHeight, textColor)
			drawText(poseStack, selectionText, startX, startY, false, textColor)
		}
	}

	protected open fun drawCursor(poseStack: PoseStack, delta: Double) {
		TODO("绘制光标")
	}

	override fun onRender(poseStack: PoseStack, delta: Double) {
		drawBackground(poseStack, delta)
		if (selectionText.isNotEmpty()) drawSelectionText(poseStack, delta)
	}

}