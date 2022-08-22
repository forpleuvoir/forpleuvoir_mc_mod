package forpleuvoir.mc.library.api

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

/**
 * 自动保存、加载
 *
 * 带有此注解的类会被自动注册对应的容器中

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.api

 * 文件名 CookieSavable

 * 创建时间 2022/8/22 21:51

 * @author forpleuvoir

 */
@Retention(RUNTIME)
@Target(CLASS)
annotation class CookieSavable(val name: String)
