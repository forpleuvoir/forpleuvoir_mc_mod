package forpleuvoir.mc.cookie.config

import forpleuvoir.mc.library.config.modconfig.ClientModConfig
import forpleuvoir.mc.library.config.modconfig.ClientModConfigApi
import java.util.function.Supplier

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.cookie.config

 * 文件名 CookieClientModConfig

 * 创建时间 2022/8/12 22:28

 * @author forpleuvoir

 */
class CookieClientModConfig : ClientModConfigApi {
	override fun getClientModConfig(): Supplier<ClientModConfig> {
		return Supplier {
			CookieConfigs
		}
	}
}