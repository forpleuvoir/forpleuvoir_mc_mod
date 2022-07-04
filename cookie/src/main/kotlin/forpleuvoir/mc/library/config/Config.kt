package forpleuvoir.mc.library.config

import forpleuvoir.mc.library.api.Matchable
import forpleuvoir.mc.library.api.Notifiable
import forpleuvoir.mc.library.api.Option
import forpleuvoir.mc.library.api.Resettable

/**
 * 单个配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config

 * 文件名 Config

 * 创建时间 2022/7/3 23:55

 * @author forpleuvoir

 */
interface Config<T> : Option, ConfigValue<T>, Notifiable<T>, Resettable, Matchable {
	val type: ConfigType

}