package forpleuvoir.mc.cookie.util

import forpleuvoir.mc.cookie.Cookie
import forpleuvoir.mc.library.utils.ModLogger

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.cookie.util

 * 文件名 CookieLogger

 * 创建时间 2022/7/10 22:49

 * @author forpleuvoir

 */

internal fun Any.logger(): ModLogger {
	return ModLogger(this::class.java, Cookie.name)
}