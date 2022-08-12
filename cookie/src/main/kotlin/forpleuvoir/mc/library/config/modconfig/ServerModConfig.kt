package forpleuvoir.mc.library.config.modconfig

import net.fabricmc.api.EnvType
import net.fabricmc.api.EnvType.SERVER
import net.minecraft.server.MinecraftServer

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig

 * 文件名 ServerModConfig

 * 创建时间 2022/8/12 13:43

 * @author forpleuvoir

 */
interface ServerModConfig : ModConfig {
	override val env: EnvType
		get() = SERVER

	val server: MinecraftServer

	fun init(server: MinecraftServer)

}