package forpleuvoir.mc.library.config.modconfig

import forpleuvoir.mc.library.api.Initializable
import forpleuvoir.mc.library.config.Config

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig

 * 文件名 ConfigCategory

 * 创建时间 2022/8/12 0:29

 * @author forpleuvoir

 */
interface ConfigCategory : Initializable {

	val name: String

	val allConfigs: Collection<Config<*>>

}