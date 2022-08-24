package forpleuvoir.mc.library.utils.text

import com.google.common.collect.ImmutableList
import net.minecraft.network.chat.FormattedText
import net.minecraft.network.chat.contents.TranslatableContents
import net.minecraft.network.chat.contents.TranslatableFormatException
import java.util.*

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.text

 * 文件名 ServerTranslatableContents

 * 创建时间 2022/8/24 22:03

 * @author forpleuvoir

 */
class ServerTranslatableContents(key: String, vararg objects: Any) : TranslatableContents(key, *objects) {

	constructor(key: String) : this(key, emptyArray<Any>())

	override fun decompose() {
		val language = ServerLanguage
		if (language !== decomposedWith) {
			decomposedWith = language
			val string = language.getOrDefault(key)
			decomposedParts = try {
				val builder = ImmutableList.builder<FormattedText>()
				Objects.requireNonNull(builder)
				decomposeTemplate(string) { element: FormattedText -> builder.add(element) }
				builder.build()
			} catch (e: TranslatableFormatException) {
				ImmutableList.of(FormattedText.of(string))
			}
		}
	}

}