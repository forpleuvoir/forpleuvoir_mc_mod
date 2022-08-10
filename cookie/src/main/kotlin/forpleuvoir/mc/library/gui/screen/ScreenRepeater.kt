package forpleuvoir.mc.library.gui.screen

import forpleuvoir.mc.library.utils.mc
import forpleuvoir.mc.library.utils.text.literal
import net.minecraft.client.gui.screens.Screen

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.screen

 * 文件名 ScreenRepeater

 * 创建时间 2022/8/10 21:29

 * @author forpleuvoir

 */
class ScreenRepeater(private val parent: Screen?, private val target: forpleuvoir.mc.library.gui.screen.Screen?) : Screen(literal()) {

	companion object {
		private var lastScreenType: Class<*>? = null
	}

	override fun init() {
		when (lastScreenType) {
			Screen::class.java                                   -> {
				ScreenHandler.setCurrent(target)
				if (target != null) lastScreenType = forpleuvoir.mc.library.gui.screen.Screen::class.java
			}

			forpleuvoir.mc.library.gui.screen.Screen::class.java -> {
				mc.setScreen(parent)
				if (parent != null) lastScreenType = Screen::class.java
			}

			else                                                 -> {
				mc.setScreen(null)
				ScreenHandler.setCurrent(null)
			}
		}
	}

}