package forpleuvoir.mc.cookie

import forpleuvoir.mc.cookie.util.logger
import forpleuvoir.mc.library.event.EventBus
import forpleuvoir.mc.library.event.events.client.ClientCommandRegisterEvent
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType.CLIENT
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.cookie

 * 文件名 CookieClient

 * 创建时间 2022/7/2 21:23

 * @author forpleuvoir

 */
@Environment(CLIENT)
object CookieClient : ClientModInitializer {

	private val log = logger()

	override fun onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register { dispatcher, registryAccess ->
			log.info("command register...")
			EventBus.broadcast(ClientCommandRegisterEvent(dispatcher, registryAccess))
		}
	}

}