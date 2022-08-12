package forpleuvoir.mc.library.utils

import forpleuvoir.mc.cookie.Cookie
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.sounds.SoundManager
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ReloadableResourceManager


/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils

 * 文件名 Misc

 * 创建时间 2022/7/2 21:43

 * @author forpleuvoir

 */

val mc: Minecraft by lazy { Minecraft.getInstance() }

val textRenderer: Font by lazy { mc.font }

val soundManager: SoundManager by lazy { mc.soundManager }

val textureManager: TextureManager by lazy { mc.textureManager }

val resourceManager: ReloadableResourceManager by lazy { mc.resourceManager as ReloadableResourceManager }

fun resources(path: String): ResourceLocation = ResourceLocation(Cookie.id, path)

/**
 * 是否为开发环境
 */
var isDevEnv: Boolean = loader.isDevelopmentEnvironment

inline fun Boolean?.ifc(action: () -> Unit) {
	if (this == true) {
		action.invoke()
	}
}

inline fun Boolean?.notc(action: () -> Unit) = if (this != null) {
	if (!this) action() else Unit
} else Unit
