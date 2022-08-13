package forpleuvoir.mc.library.config.modconfig.impl

import forpleuvoir.mc.cookie.util.logger
import forpleuvoir.mc.library.config.modconfig.ConfigHandler
import forpleuvoir.mc.library.config.modconfig.ModConfig
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig.impl

 * 文件名 AbstractConfigHandler

 * 创建时间 2022/8/13 1:01

 * @author forpleuvoir

 */
@Suppress("LeakingThis")
abstract class AbstractConfigHandler<T : ModConfig> : ConfigHandler {

	private val log = logger()
	protected val configs = ConcurrentHashMap<String, T>()

	init {
		init()
	}

	override fun init() {
		Timer().schedule(object : TimerTask() {
			override fun run() {
				configs.values.forEach {
					log.info("[{}]config auto saving..", it.modId)
					if (it.isChanged.get()) {
						it.saveAsync()
					}
				}
			}
		}, 0, 1000 * 30)
	}

	override fun loadAll() {
		configs.values.forEach { it.load() }
	}

	override fun loadAllAsync() {
		configs.values.forEach { it.loadAsync() }
	}

	override fun saveAll() {
		configs.values.forEach { it.save() }
	}

	override fun saveAllAsync() {
		configs.values.forEach { it.saveAsync() }
	}

}