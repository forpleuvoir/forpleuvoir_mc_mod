package forpleuvoir.mc.library.api


/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.api

 * 文件名 Savable

 * 创建时间 2022/8/13 0:36

 * @author forpleuvoir

 */
interface Savable {

	fun save()

	fun load()

	fun saveAsync()

	fun loadAsync()

	val needSave: Boolean get() = true
}
