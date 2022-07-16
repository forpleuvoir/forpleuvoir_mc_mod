package forpleuvoir.mc.library.config.options

import forpleuvoir.mc.library.config.ConfigValue
import forpleuvoir.mc.library.input.IKeyBindValue
import forpleuvoir.mc.library.input.KeyBind

/**
 * 热键配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 IConfigKeyBind

 * 创建时间 2022/7/4 21:28

 * @author forpleuvoir

 */
interface IConfigKeyBind : ConfigValue<KeyBind>, IKeyBindValue {

	override fun getKeyBind(): KeyBind = getValue()
	override fun setKeyBind(keyBind: KeyBind) = setValue(keyBind)


}