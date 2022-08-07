package forpleuvoir.mc.library.utils.text

import com.google.common.collect.Lists
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentContents
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import java.util.function.UnaryOperator

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.text

 * 文件名 Text

 * 创建时间 2022/8/6 3:37

 * @author forpleuvoir

 */
class Text(
	componentContents: ComponentContents,
	list: MutableList<Component>,
	style: Style,
) : MutableComponent(componentContents, list, style) {
	companion object {
		fun create(componentContents: ComponentContents): Text = Text(componentContents, Lists.newArrayList(), Style.EMPTY)
	}

	val str: String get() = this.string

	override fun append(component: Component): Text {
		siblings.add(component)
		return this
	}

	@JvmName("appendComponent")
	fun append(string: () -> Component) {
		siblings.add(string())
	}

	override fun append(string: String): Text {
		return append(literal(string))
	}

	inline fun append(string: () -> String) {
		siblings.add(literal(string()))
	}

	override fun setStyle(style: Style): Text {
		return super.setStyle(style) as Text
	}

	inline fun style(scope: (Style) -> Style) {
		this.style = scope.invoke(style)
	}

	fun withStyle(unaryOperator: (Style) -> Style): Text {
		style = (unaryOperator(style))
		return super.withStyle(unaryOperator) as Text
	}

	override fun withStyle(unaryOperator: UnaryOperator<Style>): Text {
		return super.withStyle(unaryOperator) as Text
	}

	override fun withStyle(style: Style): Text {
		return super.withStyle(style) as Text
	}

	override fun withStyle(vararg chatFormattings: ChatFormatting): Text {
		return super.withStyle(*chatFormattings) as Text
	}

	override fun withStyle(chatFormatting: ChatFormatting): Text {
		return super.withStyle(chatFormatting) as Text
	}
}