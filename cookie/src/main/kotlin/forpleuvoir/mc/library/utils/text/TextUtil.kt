package forpleuvoir.mc.library.utils.text

import forpleuvoir.mc.library.utils.textRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentContents
import net.minecraft.network.chat.contents.LiteralContents
import net.minecraft.network.chat.contents.TranslatableContents
import java.util.*

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.text

 * 文件名 TextUtil

 * 创建时间 2022/7/2 21:49

 * @author forpleuvoir

 */
fun literal(text: String = ""): Text = Text.create(LiteralContents(text))

inline fun text(text: String = "", scope: Text.() -> Unit = {}): Text = Text.create(LiteralContents(text)).apply { scope() }

inline fun text(text: ComponentContents, scope: Text.() -> Unit = {}): Text = Text.create(text).apply { scope() }

val String.literal: Text get() = Text.create(LiteralContents(this))

@JvmName("translatable1")
fun translatable(text: String, vararg params: Any): Text = Text.create(TranslatableContents(text, *params))

fun serverText(key: String, vararg params: Any): Text = ServerText(key, *params)

fun serverText(key: String): Text = ServerText(key)

fun String.translatable(vararg params: Any): Text = Text.create(TranslatableContents(this, *params))

val String.translatable: Text get() = Text.create(TranslatableContents(this))

fun Collection<String>.maxWidth(): Int {
	var temp = 0
	for (s in this) {
		if (temp < textRenderer.width(s))
			temp = textRenderer.width(s)
	}
	return temp
}

@JvmName("textMaxWidth")
fun Collection<Component>.maxWidth(): Int {
	var temp = 0
	for (t in this) {
		if (temp < textRenderer.width(t))
			temp = textRenderer.width(t)
	}
	return temp
}

fun String.wrapToLines(width: Int = 0): List<String> {
	val texts: LinkedList<String> = LinkedList()
	var temp = StringBuilder()
	for (element in this) {
		run {
			if (element != '\n') {
				if (width == 0) return@run
				if (textRenderer.width(temp.toString() + element) <= width) return@run
			}
			texts.add(temp.toString())
			temp = StringBuilder()
		}
		if (element != '\n') {
			temp.append(element)
		}
	}
	texts.add(temp.toString())
	return texts
}

fun Collection<String>.wrapToLines(width: Int = 0): List<String> {
	val texts: LinkedList<String> = LinkedList()
	for (text in this) {
		texts.addAll(text.wrapToLines(width))
	}
	return texts
}

fun Component.wrapToLines(width: Int = 0): List<Component> {
	val texts: LinkedList<Component> = LinkedList()
	this.string.wrapToLines(width).forEach { texts.add(it.literal) }
	return texts
}

@JvmName("wrapToLinesComponent")
fun Collection<Component>.wrapToLines(width: Int = 0): List<Component> {
	val texts: LinkedList<Component> = LinkedList()
	for (text in this) {
		texts.addAll(text.wrapToLines(width))
	}
	return texts
}

fun List<String>.wrapToSingleText(width: Int = 0): String {
	val str = StringBuilder()
	this.forEachIndexed { index, text ->
		val wrapToLines = text.wrapToLines(width)
		wrapToLines.forEachIndexed { i, t ->
			str.append(t)
			if (i != wrapToLines.size - 1) str.append("\n")
		}
		if (index != this.size - 1) str.append("\n")
	}
	return str.toString()
}

fun List<Component>.wrapToSingleText(width: Int = 0): Component {
	val str = StringBuilder()
	this.forEachIndexed { index, text ->
		val wrapToLines = text.wrapToLines(width)
		wrapToLines.forEachIndexed { i, t ->
			str.append(t.string)
			if (i != wrapToLines.size - 1) str.append("\n")
		}
		if (index != this.size - 1) str.append("\n")
	}
	return str.toString().literal
}
