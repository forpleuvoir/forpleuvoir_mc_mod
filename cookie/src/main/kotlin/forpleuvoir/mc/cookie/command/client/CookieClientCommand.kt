package forpleuvoir.mc.cookie.command.client

import forpleuvoir.mc.cookie.config.CookieConfigs
import forpleuvoir.mc.library.config.command.ConfigCommandHelper
import forpleuvoir.mc.library.event.EventSubscriber
import forpleuvoir.mc.library.event.Subscriber
import forpleuvoir.mc.library.event.events.client.ClientCommandRegisterEvent

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.cookie.command.client

 * 文件名 CookieClientCommand

 * 创建时间 2022/8/13 16:12

 * @author forpleuvoir

 */
@EventSubscriber
object CookieClientCommand {

	@Subscriber
	fun register(event: ClientCommandRegisterEvent) {
		event.dispatcher.register(ConfigCommandHelper.client("cookie:config:client", CookieConfigs).command())
	}

}