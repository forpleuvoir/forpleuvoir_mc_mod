package forpleuvoir.mc.library.config.options

import forpleuvoir.mc.library.config.ConfigValue

/**
 * 字符串组配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 IConfigStringList

 * 创建时间 2022/7/4 20:37

 * @author forpleuvoir

 */
interface IConfigStringList : ConfigValue<List<String>> {

	fun add(element: String): String?

	operator fun get(index: Int): String?

	operator fun set(index: Int, element: String)

	fun remove(index: Int): String?

	fun remove(element: String): String?

	fun clear()

}