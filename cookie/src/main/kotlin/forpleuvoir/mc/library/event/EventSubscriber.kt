package forpleuvoir.mc.library.event

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event

 * 文件名 EventSubscriber

 * 创建时间 2022/8/6 18:12

 * @author forpleuvoir

 */
@Target(CLASS)
@Retention(RUNTIME)
annotation class EventSubscriber(val eventBus: String = "default")
