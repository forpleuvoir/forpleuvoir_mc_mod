package forpleuvoir.mc.cookie.config

import forpleuvoir.mc.cookie.Cookie
import forpleuvoir.mc.library.api.CookieSavable
import forpleuvoir.mc.library.config.modconfig.impl.ConfigCategoryImpl
import forpleuvoir.mc.library.config.modconfig.impl.LocalServerModConfig

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.cookie.config

 * 文件名 CookieServerConfigs

 * 创建时间 2022/8/12 16:49

 * @author forpleuvoir

 */
@CookieSavable("config")
object CookieServerConfigs : LocalServerModConfig(Cookie.id) {
	init {
		addCategory(Toggle)
		addCategory(Setting)
	}

	object Toggle : ConfigCategoryImpl("toggle", this) {

		@JvmStatic
		val enable = configBoolean("enable", true)

	}

	object Setting : ConfigCategoryImpl("setting", this) {

		@JvmStatic
		val language = configString("language", "en_us").apply {
			subscribeChange(Toggle) {
				it as String

			}
		}
	}


}