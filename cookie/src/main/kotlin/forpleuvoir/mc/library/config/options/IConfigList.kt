package forpleuvoir.mc.library.config.options

import forpleuvoir.mc.library.config.ConfigValue

/**
 * 列表集合配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 IConfigStringList

 * 创建时间 2022/7/4 20:37

 * @author forpleuvoir

 */
interface IConfigList<T> : ConfigValue<List<T>> {

	fun add(element: T): T?

	operator fun get(index: Int): T?

	operator fun set(index: Int, element: T)

	fun remove(index: Int): T?

	fun remove(element: T): T?

	fun clear()

}