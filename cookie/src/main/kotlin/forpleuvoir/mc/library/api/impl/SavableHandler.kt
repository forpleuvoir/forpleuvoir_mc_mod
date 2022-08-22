package forpleuvoir.mc.library.api.impl

import forpleuvoir.mc.cookie.util.logger
import forpleuvoir.mc.library.api.Initializable
import forpleuvoir.mc.library.api.Savable
import java.util.concurrent.ConcurrentHashMap

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.api.impl

 * 文件名 SavableHandler

 * 创建时间 2022/8/23 0:36

 * @author forpleuvoir

 */
abstract class SavableHandler<T : Savable> : Savable, Initializable {

	protected val log = logger()

	protected val configs = ConcurrentHashMap<String, T>()

	override fun load() {
		configs.values.forEach { it.load() }
	}

	override fun loadAsync() {
		configs.values.forEach { it.loadAsync() }
	}

	override fun save() {
		configs.values.forEach { it.save() }
	}

	override fun saveAsync() {
		configs.values.forEach { it.saveAsync() }
	}

}