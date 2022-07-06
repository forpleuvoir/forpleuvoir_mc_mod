package forpleuvoir.mc.library.config

import forpleuvoir.mc.library.api.*
import forpleuvoir.mc.library.api.serialization.JsonSerializer
import forpleuvoir.mc.library.api.serialization.Serializable

/**
 * 单个配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config

 * 文件名 Config

 * 创建时间 2022/7/3 23:55

 * @author forpleuvoir

 */
interface Config<T> : Option, ConfigValue<T>, Notifiable<T>, Resettable, Matchable, JsonSerializer, Initializable {

	val type: ConfigType

}