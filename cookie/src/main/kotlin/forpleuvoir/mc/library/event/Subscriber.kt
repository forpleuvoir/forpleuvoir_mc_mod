package forpleuvoir.mc.library.event

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event

 * 文件名 Subscriber

 * 创建时间 2022/8/6 18:28

 * @author forpleuvoir

 */
@Target(FUNCTION)
@Retention(RUNTIME)
annotation class Subscriber(val greedy: Boolean = false)
