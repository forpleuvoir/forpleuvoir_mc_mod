package forpleuvoir.mc.library.api.impl

import forpleuvoir.mc.library.api.CookieSavable
import forpleuvoir.mc.library.event.EventSubscriber
import forpleuvoir.mc.library.event.Subscriber
import forpleuvoir.mc.library.event.events.server.ServerSavingEvent
import forpleuvoir.mc.library.event.events.server.ServerStartedEvent
import forpleuvoir.mc.library.event.events.server.ServerStartingEvent
import forpleuvoir.mc.library.event.events.server.ServerStoppedEvent
import forpleuvoir.mc.library.utils.scanModPackage

/**
 * 服务端配置处理器

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.api.impl

 * 文件名 ServerSavableHandler

 * 创建时间 2022/8/13 1:21

 * @author forpleuvoir

 */
@EventSubscriber
@Suppress("unused_parameter")
object ServerSavableHandler : SavableHandler<ServerSavable>() {

	override fun init() {}

	@Subscriber
	fun init(event: ServerStartingEvent) {
		log.info("init server savable...")
		scanModPackage {
			it.isAnnotationPresent(CookieSavable::class.java) && ServerSavable::class.java.isAssignableFrom(it)
		}.forEach { (metadata, clazzs) ->
			clazzs.forEach {
				val constructor = it.getDeclaredConstructor()
				constructor.isAccessible = true
				val instance = (it.kotlin.objectInstance ?: constructor.newInstance()) as ServerSavable
				val annotation = it.getAnnotation(CookieSavable::class.java)
				instance.init(event.server)
				configs["${metadata.id} - ${annotation.name}"] = instance
			}
		}
	}


	@Subscriber
	fun serverSave(event: ServerSavingEvent) {
		configs.forEach { (key, value) ->
			if (value.needSave) {
				log.info("[{}]auto save server savable...", key)
				value.saveAsync()
			}
		}
	}

	@Subscriber
	fun stop(event: ServerStoppedEvent) {
		log.info("save server savable...")
		saveAsync()
	}

}