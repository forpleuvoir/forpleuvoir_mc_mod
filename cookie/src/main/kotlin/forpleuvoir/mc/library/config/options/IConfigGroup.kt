package forpleuvoir.mc.library.config.options

import forpleuvoir.mc.library.config.Config
import forpleuvoir.mc.library.config.ConfigValue

/**
 * 配置组

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 IConfigGroup

 * 创建时间 2022/7/4 21:29

 * @author forpleuvoir

 */
interface IConfigGroup : ConfigValue<MutableSet<Config<*>>>