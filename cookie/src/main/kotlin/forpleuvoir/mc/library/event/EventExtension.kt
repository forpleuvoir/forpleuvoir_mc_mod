package forpleuvoir.mc.library.event

import forpleuvoir.mc.library.utils.text.Text
import forpleuvoir.mc.library.utils.text.translatable

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event

 * 文件名 EventExtension

 * 创建时间 2022/8/6 3:35

 * @author forpleuvoir

 */

val Class<out Event>.displayName: Text get() = translatable(this.name)

val Class<out Event>.displayDescription: Text get() = translatable("${this.name}.desc")