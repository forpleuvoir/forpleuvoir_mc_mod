package forpleuvoir.mc.library.config.options

import forpleuvoir.mc.library.api.Option
import forpleuvoir.mc.library.api.Switchable
import forpleuvoir.mc.library.config.ConfigValue

/**
 * 选项配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 IConfigOption

 * 创建时间 2022/7/4 20:38

 * @author forpleuvoir

 */
interface IConfigOption : ConfigValue<Option>, Switchable {

	fun getOptions(): LinkedHashSet<Option>

}