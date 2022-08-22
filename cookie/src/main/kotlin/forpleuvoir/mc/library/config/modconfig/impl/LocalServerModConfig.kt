package forpleuvoir.mc.library.config.modconfig.impl

import forpleuvoir.mc.library.api.impl.ServerSavable
import forpleuvoir.mc.library.config.ConfigUtil
import net.minecraft.server.MinecraftServer
import java.nio.file.Path

/**
 * 服务器MOD配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig.impl

 * 文件名 LocalServerModConfig

 * 创建时间 2022/8/12 1:46

 * @author forpleuvoir

 */
open class LocalServerModConfig(override val modId: String) : LocalModConfig(modId), ServerSavable {

	override fun localConfigPath(): Path = ConfigUtil.getServerConfigPath(modId, server)

	override lateinit var server: MinecraftServer

	override fun init(server: MinecraftServer) {
		this.server = server
		allCategory.forEach { configCategory ->
			configCategory.allConfigs.forEach {
				it.resetDefValue()
			}
		}
		init()
	}

}