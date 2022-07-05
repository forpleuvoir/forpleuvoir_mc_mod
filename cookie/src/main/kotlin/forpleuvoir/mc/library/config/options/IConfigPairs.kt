package forpleuvoir.mc.library.config.options

import forpleuvoir.mc.library.config.ConfigValue

/**
 * 键值对集合配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 IConfigPairs

 * 创建时间 2022/7/4 21:31

 * @author forpleuvoir

 */
interface IConfigPairs<K, V> : ConfigValue<MutableList<Pair<K, V>>> {

	fun add(key: K, value: V)

	fun remove(key: K): V

	fun get(key: K): V

	fun get(index: Int): Pair<K, V>

}