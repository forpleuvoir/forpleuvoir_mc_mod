package forpleuvoir.mc.library.api

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.api

 * 文件名 Resettable

 * 创建时间 2022/7/4 0:16

 * @author forpleuvoir

 */
interface Resettable {

	val isDefault: Boolean

	fun resetDefValue()

}