package forpleuvoir.mc.cookie.config

import forpleuvoir.mc.cookie.Cookie
import forpleuvoir.mc.library.api.CookieSavable
import forpleuvoir.mc.library.config.modconfig.impl.ConfigCategoryImpl
import forpleuvoir.mc.library.config.modconfig.impl.LocalClientModConfig
import forpleuvoir.mc.library.gui.foundation.layout.list
import forpleuvoir.mc.library.gui.screen.ScreenHandler
import forpleuvoir.mc.library.gui.screen.screen
import forpleuvoir.mc.library.gui.text.textLabel
import forpleuvoir.mc.library.gui.widget.button.button
import forpleuvoir.mc.library.input.KEY_C
import forpleuvoir.mc.library.input.KeyBind
import forpleuvoir.mc.library.input.KeyTriggerMode.OnLongPress
import forpleuvoir.mc.library.input.keyBindSetting
import forpleuvoir.mc.library.utils.color.Color
import forpleuvoir.mc.library.utils.text.literal
import forpleuvoir.mc.library.utils.text.translatable

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.cookie.config

 * 文件名 CookieConfigs

 * 创建时间 2022/8/11 23:59

 * @author forpleuvoir

 */
internal val Toggles = CookieConfigs.Toggle

@CookieSavable("config")
object CookieConfigs : LocalClientModConfig(Cookie.id) {

	init {
		addCategory(Toggle)
	}

	object Toggle : ConfigCategoryImpl("toggle", this) {

		@JvmStatic
		val enable = configBoolean("enable", true)

		@JvmStatic
		val color = configColor("color", Color.BLUE)

		@JvmStatic
		val open_gui = configKeyBind("open_gui", KeyBind(KEY_C, defaultSetting = keyBindSetting(triggerMode = OnLongPress)) {
			ScreenHandler.openScreen {
				screen {
					list {
						textLabel({ literal("test") }) {
							textColor = Color.RED
						}
						button(text = { translatable("cookie.confirm") }, onClick = {
							println(this.text().str)
						})
					}
				}
			}
		})

	}

}