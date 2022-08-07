package forpleuvoir.mc.library.gui.widget.button

import forpleuvoir.mc.library.gui.foundation.ParentElement
import forpleuvoir.mc.library.gui.icon.Icon
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.color.Color4f
import forpleuvoir.mc.library.utils.text.Text
import forpleuvoir.mc.library.utils.textRenderer

/**
 * 按钮

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.widget.button

 * 文件名 Buttons

 * 创建时间 2022/7/22 23:57

 * @author forpleuvoir

 */

inline fun ParentElement.button(
	noinline text: () -> Text,
	width: Double = textRenderer.width(text()) + 8.0,
	height: Double = 20.0,
	color: Color<out Number> = Color4f.WHITE,
	noinline onClick: Button.() -> Unit = { },
	noinline onRelease: Button.() -> Unit = {},
	scope: Button.() -> Unit = {},
): Button =
	this.addElement(Button(text).apply {
		this.width = width
		this.height = height
		this.buttonColor = color
		this.onClick = { onClick.invoke(this) }
		this.onRelease = { onRelease.invoke(this) }
		this.scope()
	})

@JvmName("buttonString")
inline fun ParentElement.button(
	noinline text: () -> String,
	width: Double = textRenderer.width(text()) + 8.0,
	height: Double = 20.0,
	color: Color<out Number> = Color4f.WHITE,
	noinline onClick: Button.() -> Unit = { },
	noinline onRelease: Button.() -> Unit = {},
	scope: Button.() -> Unit = {},
): Button =
	this.addElement(Button(text).apply {
		this.width = width
		this.height = height
		this.buttonColor = color
		this.onClick = { onClick.invoke(this) }
		this.onRelease = { onRelease.invoke(this) }
		this.scope()
	})


inline fun ParentElement.iconButton(
	icon: Icon,
	width: Double = 20.0,
	height: Double = 20.0,
	color: Color<out Number> = Color4f.WHITE,
	noinline onClick: Button.() -> Unit = { },
	noinline onRelease: Button.() -> Unit = {},
	scope: Button.() -> Unit = {},
): IconButton =
	this.addElement(IconButton(icon).apply {
		this.width = width
		this.height = height
		this.buttonColor = color
		this.onClick = { onClick.invoke(this) }
		this.onRelease = { onRelease.invoke(this) }
		this.scope()
	})
