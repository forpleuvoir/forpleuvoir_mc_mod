package forpleuvoir.mc.library.utils.text

import com.google.common.collect.Lists
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.contents.LiteralContents

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.text

 * 文件名 ServerText

 * 创建时间 2022/8/24 21:51

 * @author forpleuvoir

 */
class ServerText(
	key: String,
	list: MutableList<Component>,
	style: Style,
	vararg objects: Any,
) : Text(LiteralContents(create(ServerTranslatableContents(key, *objects)).str), list, style) {

	constructor(key: String, vararg objects: Any) : this(key, Lists.newArrayList(), Style.EMPTY, *objects)

	constructor(key: String) : this(key, emptyArray<Any>())

}