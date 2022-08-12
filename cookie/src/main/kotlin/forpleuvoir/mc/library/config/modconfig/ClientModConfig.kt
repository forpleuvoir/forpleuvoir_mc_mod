package forpleuvoir.mc.library.config.modconfig

import net.fabricmc.api.EnvType
import net.fabricmc.api.EnvType.CLIENT
import net.minecraft.client.Minecraft

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig

 * 文件名 ClientModConfig

 * 创建时间 2022/8/12 13:49

 * @author forpleuvoir

 */
interface ClientModConfig : ModConfig {
	override val env: EnvType
		get() = CLIENT
}