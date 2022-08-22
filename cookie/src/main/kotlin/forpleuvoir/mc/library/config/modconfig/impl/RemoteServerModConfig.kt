package forpleuvoir.mc.library.config.modconfig.impl

import forpleuvoir.mc.library.api.impl.ServerSavable
import net.minecraft.server.MinecraftServer

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig.impl

 * 文件名 RemoteServerModConfig

 * 创建时间 2022/8/12 15:33

 * @author forpleuvoir

 */
open class RemoteServerModConfig(
	override val modId: String,
	saveUrl: String,
	loadUrl: String,
) : RemoteModConfig(saveUrl, loadUrl), ServerSavable {

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