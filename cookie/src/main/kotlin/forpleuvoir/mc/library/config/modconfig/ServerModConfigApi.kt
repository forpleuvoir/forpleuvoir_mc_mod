package forpleuvoir.mc.library.config.modconfig

import java.util.function.Supplier

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig

 * 文件名 ServerModConfigApi

 * 创建时间 2022/8/12 22:23

 * @author forpleuvoir

 */
interface ServerModConfigApi {
	fun getServerModConfig(): Supplier<ServerModConfig>
}