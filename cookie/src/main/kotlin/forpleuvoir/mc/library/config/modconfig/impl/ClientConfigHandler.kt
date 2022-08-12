package forpleuvoir.mc.library.config.modconfig.impl

import forpleuvoir.mc.cookie.util.logger
import forpleuvoir.mc.library.config.modconfig.ClientModConfig
import forpleuvoir.mc.library.config.modconfig.ClientModConfigApi
import forpleuvoir.mc.library.event.EventSubscriber
import forpleuvoir.mc.library.event.Subscriber
import forpleuvoir.mc.library.event.events.client.ClientStartingEvent
import forpleuvoir.mc.library.event.events.client.ClientStopEvent
import forpleuvoir.mc.library.utils.getEntrypoints

/**
 * 客户端配置处理器

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig.impl

 * 文件名 ClientConfigHandler

 * 创建时间 2022/8/11 22:56

 * @author forpleuvoir

 */
@EventSubscriber
object ClientConfigHandler : AbstractConfigHandler<ClientModConfig>() {

	private val log = logger()

	@Subscriber
	fun init(event: ClientStartingEvent) {
		log.info("init client config...")
		getEntrypoints("cookie_mod_client_config", ClientModConfigApi::class.java) {
			val metadata = it.provider.metadata
			it.entrypoint.getClientModConfig().get().run {
				init()
				configs[metadata.id] = this
			}
		}
	}

	@Subscriber
	fun stop(event: ClientStopEvent) {
		log.info("save client config...")
		configs.values.forEach { it.close() }
	}


}