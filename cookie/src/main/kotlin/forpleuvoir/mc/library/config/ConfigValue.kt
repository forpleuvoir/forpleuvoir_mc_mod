package forpleuvoir.mc.library.config

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config

 * 文件名 ConfigValue

 * 创建时间 2022/7/4 0:15

 * @author forpleuvoir

 */
interface ConfigValue<T> {

	fun setValue(value: T)

	fun getValue(): T

}