package forpleuvoir.mc.cookie.command

import forpleuvoir.mc.cookie.config.CookieServerConfigs
import forpleuvoir.mc.library.config.command.ConfigCommandHelper
import forpleuvoir.mc.library.event.EventSubscriber
import forpleuvoir.mc.library.event.Subscriber
import forpleuvoir.mc.library.event.events.server.ServerCommandRegisterEvent

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.cookie.command

 * 文件名 CookieCommand

 * 创建时间 2022/8/13 16:04

 * @author forpleuvoir

 */
@EventSubscriber
object CookieCommand {

	@Subscriber
	fun register(event: ServerCommandRegisterEvent) {
		event.dispatcher.register(
			ConfigCommandHelper.server("cookie:config:server", CookieServerConfigs) {
				it.hasPermission(2)
			}.command()
		)
	}

}