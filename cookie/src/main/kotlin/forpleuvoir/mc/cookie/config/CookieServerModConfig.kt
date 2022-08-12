package forpleuvoir.mc.cookie.config

import forpleuvoir.mc.library.config.modconfig.ServerModConfig
import forpleuvoir.mc.library.config.modconfig.ServerModConfigApi
import java.util.function.Supplier

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.cookie.config

 * 文件名 CookieServerModConfig

 * 创建时间 2022/8/12 22:29

 * @author forpleuvoir

 */
class CookieServerModConfig : ServerModConfigApi {
	override fun getServerModConfig(): Supplier<ServerModConfig> {
		return Supplier { CookieServerConfigs }
	}
}