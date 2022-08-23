package forpleuvoir.mc.library.api.impl

import forpleuvoir.mc.library.api.CookieSavable
import forpleuvoir.mc.library.event.EventSubscriber
import forpleuvoir.mc.library.event.Subscriber
import forpleuvoir.mc.library.event.events.client.ClientStartingEvent
import forpleuvoir.mc.library.event.events.client.ClientStopEvent
import forpleuvoir.mc.library.utils.scanModPackage
import java.util.*

/**
 * 客户端配置处理器

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.api.impl

 * 文件名 ClientSavableHandler

 * 创建时间 2022/8/11 22:56

 * @author forpleuvoir

 */
@EventSubscriber
@Suppress("unused_parameter")
object ClientSavableHandler : SavableHandler<ClientSavable>() {

	override fun init() {
		Timer().schedule(object : TimerTask() {
			override fun run() {
				configs.forEach { (key, value) ->
					if (value.needSave) {
						log.info("[{}]auto saving..", key)
						value.saveAsync()
					}
				}
			}
		}, 0, 1000 * 30)
	}

	@Subscriber
	fun init(event: ClientStartingEvent) {
		log.info("init client savable...")
		init()
		scanModPackage {
			it.isAnnotationPresent(CookieSavable::class.java) && ClientSavable::class.java.isAssignableFrom(it)
		}.forEach { (metadata, clazzs) ->
			clazzs.forEach {
				val constructor = it.getDeclaredConstructor()
				constructor.isAccessible = true
				val instance = (it.kotlin.objectInstance ?: constructor.newInstance()) as ClientSavable
				val annotation = it.getAnnotation(CookieSavable::class.java)
				instance.init(event.minecraftClient)
				configs["${metadata.id} - ${annotation.name}"] = instance
			}
		}
	}

	@Subscriber
	fun stop(event: ClientStopEvent) {
		log.info("save client savable...")
		configs.values.forEach { it.save() }
	}


}