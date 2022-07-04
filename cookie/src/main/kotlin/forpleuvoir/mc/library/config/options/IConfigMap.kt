package forpleuvoir.mc.library.config.options

import forpleuvoir.mc.library.config.ConfigValue

/**
 * 映射配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 IConfigMap

 * 创建时间 2022/7/4 21:32

 * @author forpleuvoir

 */
interface IConfigMap<K, V> : ConfigValue<MutableMap<K, V>>