package forpleuvoir.mc.cookie.config

import forpleuvoir.mc.cookie.Cookie
import forpleuvoir.mc.library.config.ClientModConfig
import forpleuvoir.mc.library.config.ConfigCategoryImpl

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.cookie.config

 * 文件名 CookieConfigs

 * 创建时间 2022/8/11 23:59

 * @author forpleuvoir

 */
internal val Toggles = CookieConfigs.Toggle

object CookieConfigs : ClientModConfig(Cookie.id) {

	init {
		addCategory(Toggle)
	}

	object Toggle : ConfigCategoryImpl("toggle", this) {

		@JvmStatic
		val enable = configBoolean("enable", true)

	}

}