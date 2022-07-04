package forpleuvoir.mc.library.config.options

import forpleuvoir.mc.library.api.Toggleable
import forpleuvoir.mc.library.config.ConfigValue

/**
 * 布尔配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 IConfigBoolean

 * 创建时间 2022/7/4 18:26

 * @author forpleuvoir

 */
interface IConfigBoolean : ConfigValue<Boolean>, Toggleable {
	override fun toggle() {
		setValue(!getValue())
	}
}