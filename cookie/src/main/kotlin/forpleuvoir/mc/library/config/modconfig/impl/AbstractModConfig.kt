package forpleuvoir.mc.library.config.modconfig.impl

import forpleuvoir.mc.library.config.modconfig.ConfigCategory
import forpleuvoir.mc.library.config.modconfig.ModConfig
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig.impl

 * 文件名 AbstractModConfig

 * 创建时间 2022/8/12 13:58

 * @author forpleuvoir

 */
abstract class AbstractModConfig:ModConfig {

	private val categories = ConcurrentLinkedQueue<ConfigCategory>()

	override val allCategory: Collection<ConfigCategory> = categories

	override fun addCategory(category: ConfigCategory) {
		categories.add(category)
	}

	override fun init() {
		categories.clear()
		allCategory.forEach {
			it.init()
			it.allConfigs.forEach { c ->
				c.subscribeChange(this) {
					isChanged.set(true)
				}
			}
		}
		load()
	}

	override val isChanged: AtomicBoolean = AtomicBoolean(false)
}