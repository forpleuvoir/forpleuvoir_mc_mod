package forpleuvoir.mc.cookie

import forpleuvoir.mc.library.event.EventBus
import forpleuvoir.mc.library.event.events.server.ServerCommandRegisterEvent
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.cookie

 * 文件名 Cookie

 * 创建时间 2022/7/10 22:50

 * @author forpleuvoir

 */
object Cookie : ModInitializer {
	const val name: String = "Cookie"
	const val id: String = "cookie"
	override fun onInitialize() {
		CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
			EventBus.broadcast(ServerCommandRegisterEvent(dispatcher, registryAccess, environment))
		}
	}

}