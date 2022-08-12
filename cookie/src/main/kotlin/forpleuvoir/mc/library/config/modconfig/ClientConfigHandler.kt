package forpleuvoir.mc.library.config.modconfig

import forpleuvoir.mc.library.event.EventSubscriber
import forpleuvoir.mc.library.event.Subscriber
import forpleuvoir.mc.library.event.events.client.ClientStartingEvent

/**
 * 客户端配置处理器

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig

 * 文件名 ClientConfigHandler

 * 创建时间 2022/8/11 22:56

 * @author forpleuvoir

 */
@EventSubscriber
object ClientConfigHandler {

	@Subscriber
	fun init(event: ClientStartingEvent) {
	}


}