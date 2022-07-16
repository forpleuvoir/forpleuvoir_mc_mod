package forpleuvoir.mc.library.input

import forpleuvoir.mc.library.api.Initializable

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.input

 * 文件名 IKeyBindValue

 * 创建时间 2022/7/16 12:07

 * @author forpleuvoir

 */
interface IKeyBindValue : Initializable {

	fun register() {
		InputHandler.register(getKeyBind())
	}

	fun unregister() {
		InputHandler.unregister(getKeyBind())
	}

	fun getKeyBind(): KeyBind

	fun setKeyBind(keyBind: KeyBind)

	override fun init() {
		register()
	}


}