package forpleuvoir.mc.library.config.options

import forpleuvoir.mc.library.config.ConfigValue

/**
 * 映射配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 IConfigMap

 * 创建时间 2022/7/4 21:32

 * @author forpleuvoir

 */
interface IConfigMap<K, V> : ConfigValue<Map<K, V>> {

	operator fun set(key: K, value: V)

	operator fun get(key: K): V?

	fun remove(key: K): V?

	fun rename(origin: K, current: K)

	fun rest(originKey: K, currentKey: K, value: V)

	fun clear()

}