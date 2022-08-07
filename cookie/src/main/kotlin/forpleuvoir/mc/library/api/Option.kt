package forpleuvoir.mc.library.api

import forpleuvoir.mc.library.utils.text.Text

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.api

 * 文件名 Option

 * 创建时间 2022/7/4 21:15

 * @author forpleuvoir

 */
interface Option {
	val key: String

	val displayName: Text

	val description: Text
}