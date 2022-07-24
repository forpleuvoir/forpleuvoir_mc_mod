package forpleuvoir.mc.library.gui.text

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.*
import forpleuvoir.mc.library.gui.texture.TEXT_FIELD
import forpleuvoir.mc.library.input.*
import forpleuvoir.mc.library.utils.clamp
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.color.Color4f
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

	var textColor: Color<out Number> = Color4f.WHITE

	var text: String = text.substring(0, text.length.coerceAtMost(maxLength))
		set(value) {
			val old = field
			if (old != value) {
				field = value
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

	val selectionStart: Int get() = 0

	val selectionEnd: Int get() = 0

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
	protected open fun undo() {}

	override fun onKeyPress(keyCode: Int, modifiers: Int): HandleStatus {
		if (InputHandler.hasKey(KEY_LEFT_CONTROL)) {
			when (keyCode) {
				KEY_V         -> paste()
				KEY_C         -> copy()
				KEY_X         -> cut()
				KEY_BACKSPACE -> deleteWord()
				KEY_Z         -> undo()
				KEY_Y         -> TODO("取消撤回")
				KEY_LEFT      -> TODO("光标移动到单词结尾")
				KEY_RIGHT     -> TODO("光标移动到单词头部")
			}
		} else if (InputHandler.hasKey(KEY_LEFT_SHIFT)) {
			when (keyCode) {

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

	}

	override fun onRender(poseStack: PoseStack, delta: Double) {
		drawBackground(poseStack, delta)
		if (selectionText.isNotEmpty()) drawSelectionText(poseStack, delta)
	}

}