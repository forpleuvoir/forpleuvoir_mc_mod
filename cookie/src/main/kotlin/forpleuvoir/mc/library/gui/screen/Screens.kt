package forpleuvoir.mc.library.gui.screen

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.screen

 * 文件名 Screens

 * 创建时间 2022/7/23 0:07

 * @author forpleuvoir

 */
fun ScreenHandler.screen(screenScope: AbstractScreen.() -> Unit): Screen {
	val screen = object : AbstractScreen() {
		override fun init() {
			children.clear()
			screenScope()
		}
	}
	return screen
}