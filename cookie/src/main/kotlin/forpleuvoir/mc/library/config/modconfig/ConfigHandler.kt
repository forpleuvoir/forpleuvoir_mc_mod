package forpleuvoir.mc.library.config.modconfig

import forpleuvoir.mc.library.api.Initializable

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig

 * 文件名 ConfigHandler

 * 创建时间 2022/8/13 0:36

 * @author forpleuvoir

 */
interface ConfigHandler : Initializable {

	fun saveAll()

	fun loadAll()

	fun saveAllAsync()

	fun loadAllAsync()

}