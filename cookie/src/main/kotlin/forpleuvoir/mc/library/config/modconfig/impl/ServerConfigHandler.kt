package forpleuvoir.mc.library.config.modconfig.impl

import forpleuvoir.mc.cookie.util.logger
import forpleuvoir.mc.library.config.modconfig.ServerModConfig
import forpleuvoir.mc.library.config.modconfig.ServerModConfigApi
import forpleuvoir.mc.library.event.EventSubscriber
import forpleuvoir.mc.library.event.Subscriber
import forpleuvoir.mc.library.event.events.server.ServerSavingEvent
import forpleuvoir.mc.library.event.events.server.ServerStartedEvent
import forpleuvoir.mc.library.event.events.server.ServerStoppedEvent
import forpleuvoir.mc.library.utils.getEntrypoints

/**
 * 服务端配置处理器

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig.impl

 * 文件名 ServerConfigHandler

 * 创建时间 2022/8/13 1:21

 * @author forpleuvoir

 */
@EventSubscriber
object ServerConfigHandler : AbstractConfigHandler<ServerModConfig>() {

	private val log = logger()

	@Subscriber
	fun init(event: ServerStartedEvent) {
		log.info("init server config...")
		getEntrypoints("cookie_mod_server_config", ServerModConfigApi::class.java) {
			val metadata = it.provider.metadata
			it.entrypoint.getServerModConfig().get().run {
				init(event.server)
				configs[metadata.id] = this
			}
		}
	}

	@Subscriber
	fun serverSave(event: ServerSavingEvent) {
		log.info("save server config...")
		configs.values.forEach {
			if (it.isChanged.get()) {
				it.saveAsync()
			}
		}
	}

	@Subscriber
	fun stop(event: ServerStoppedEvent) {
		log.info("save server config...")
		configs.values.forEach { it.saveAsync() }
	}

}