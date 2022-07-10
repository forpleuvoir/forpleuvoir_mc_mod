package forpleuvoir.mc.library.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils

 * 文件名 ModLogger

 * 创建时间 2022/7/10 22:39

 * @author forpleuvoir

 */
open class ModLogger(clazz: Class<*>, modName: String) {

	private val log: Logger

	init {
		log = LoggerFactory.getLogger("${modName}[${clazz.simpleName}]")
	}

	fun info(str: String, vararg params: Any?) {
		log.info(str, *params)
	}

	fun error(str: String, vararg params: Any?) {
		log.error(str, *params)
	}

	fun error(e: Exception) {
		log.error(e.message, e)
	}

	fun warn(str: String, vararg params: Any?) {
		log.warn(str, *params)
	}
}