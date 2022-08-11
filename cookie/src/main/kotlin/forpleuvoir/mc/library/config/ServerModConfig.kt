package forpleuvoir.mc.library.config

import net.minecraft.server.MinecraftServer
import java.nio.file.Path

/**
 * 服务器MOD配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config

 * 文件名 ServerModConfig

 * 创建时间 2022/8/12 1:46

 * @author forpleuvoir

 */
class ServerModConfig(override val modId: String, private val server: MinecraftServer) : AbstractModConfig(modId) {
	override fun getConfigPath(): Path = ConfigUtil.getServerConfigPath(modId, server)
}