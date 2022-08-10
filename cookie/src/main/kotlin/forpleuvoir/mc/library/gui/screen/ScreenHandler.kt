package forpleuvoir.mc.library.gui.screen

import com.mojang.blaze3d.vertex.BufferUploader
import forpleuvoir.mc.library.input.InputHandler
import forpleuvoir.mc.library.utils.mc
import net.minecraft.client.KeyMapping
import net.minecraft.client.renderer.texture.Tickable
import java.util.function.Consumer

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.screen

 * 文件名 ScreenHandler

 * 创建时间 2022/7/18 0:34

 * @author forpleuvoir

 */
object ScreenHandler : Tickable {

	var current: Screen? = null
		private set

	fun setCurrent(screen: Screen?, needInit: Boolean = true) {
		current = screen
		BufferUploader.reset()
		current?.let {
			mc.mouseHandler.releaseMouse()
			InputHandler.unPressAll()
			KeyMapping.releaseAll()
			if (needInit) it.init()
			mc.noRender = false
			mc.updateTitle()
			return
		}
		mc.soundManager.resume()
		mc.mouseHandler.grabMouse()
		mc.updateTitle()
		mc.screen?.init(mc, mc.window.guiScaledWidth, mc.window.guiScaledHeight)
	}

	/**
	 * 打开一个Screen
	 * @param scope [@kotlin.ExtensionFunctionType] Function1<ScreenManager, Screen>
	 */
	inline fun openScreen(scope: ScreenHandler.() -> Screen) {
		setCurrent(scope(this).apply { parentScreen = current })
	}

	@JvmStatic
	fun hasScreen(): Boolean = current != null

	@JvmStatic
	fun hasScreen(action: Consumer<Screen>) {
		current?.let { action.accept(it) }
	}

	override fun tick() {
		current?.run {
			if (active) tick.invoke()
		}
	}
}