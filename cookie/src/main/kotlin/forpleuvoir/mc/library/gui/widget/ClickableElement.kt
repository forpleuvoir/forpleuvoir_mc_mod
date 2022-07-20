package forpleuvoir.mc.library.gui.widget

import forpleuvoir.mc.library.gui.foundation.AbstractElement
import forpleuvoir.mc.library.gui.foundation.HandleStatus
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Continue
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Interrupt
import forpleuvoir.mc.library.gui.foundation.mouseHover
import forpleuvoir.mc.library.utils.soundManager
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.sounds.SoundEvents

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.widget

 * 文件名 ClickableElement

 * 创建时间 2022/7/21 0:49

 * @author forpleuvoir

 */
abstract class ClickableElement : AbstractElement() {

	/**
	 * 是否被按下
	 */
	var pressed: Boolean = false
		private set

	open var onClick: () -> Unit = {}

	open var onRelease: () -> Unit = {}

	override fun onMouseClick(mouseX: Number, mouseY: Number, button: Int): HandleStatus {
		if (button == 0) {
			soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f))
			pressed = true
			onClick()
		}
		return Interrupt
	}

	override fun onMouseRelease(mouseX: Number, mouseY: Number, button: Int): HandleStatus {
		val b = !pressed
		if (!b) pressed = false
		onRelease()
		return if (b) Continue else Interrupt
	}

	protected fun <T> status(normal: T, hovered: T, pressed: T): T {
		return if (active)
			if (mouseHover())
				if (this.pressed)
					pressed
				else hovered
			else normal
		else pressed
	}

}