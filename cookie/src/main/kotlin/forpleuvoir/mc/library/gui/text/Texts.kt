package forpleuvoir.mc.library.gui.text

import forpleuvoir.mc.library.gui.foundation.Align
import forpleuvoir.mc.library.gui.foundation.Align.Center
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Interrupt
import forpleuvoir.mc.library.gui.foundation.ParentElement
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.d
import forpleuvoir.mc.library.utils.text.Text
import forpleuvoir.mc.library.utils.text.literal
import forpleuvoir.mc.library.utils.textRenderer
import java.util.function.Supplier

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.text

 * 文件名 Texts

 * 创建时间 2022/7/22 23:31

 * @author forpleuvoir

 */

inline fun ParentElement.textLabel(
	noinline text: () -> Text,
	width: Double = textRenderer.width(text()).d,
	height: Double = textRenderer.lineHeight.d,
	noinline onClick: TextLabel.(Int) -> Unit = { },
	align: Align = Center,
	shadow: Boolean = false,
	rightToLeft: Boolean = false,
	backgroundColor: Color = Color.BLACK.alpha(0f),
	bordColor: Color = Color.BLACK.alpha(0f),
	scope: (TextLabel.() -> Unit) = {},
): TextLabel =
	this.addElement(TextLabel(text, width, height).apply {
		this.align = align
		this.mouseClick = { _, _, button ->
			onClick.invoke(this, button)
			Interrupt
		}
		this.shadow = shadow
		this.rightToLeft = rightToLeft
		this.backgroundColor = backgroundColor
		this.bordColor = bordColor
		this.parent = this@textLabel
		scope()
	})


inline fun ParentElement.textLabel(
	text: Supplier<String>,
	width: Double = textRenderer.width(text.get()).d,
	height: Double = textRenderer.lineHeight.d,
	noinline onClick: TextLabel.(Int) -> Unit = { },
	align: Align = Center,
	shadow: Boolean = false,
	rightToLeft: Boolean = false,
	backgroundColor: Color = Color.BLACK.alpha(0f),
	bordColor: Color = Color.BLACK.alpha(0f),
	scope: (TextLabel.() -> Unit) = {},
): TextLabel = textLabel({ literal(text.get()) }, width, height, onClick, align, shadow, rightToLeft, backgroundColor, bordColor, scope)