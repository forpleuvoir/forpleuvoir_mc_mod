package forpleuvoir.mc.library.event.events.client

import com.mojang.brigadier.CommandDispatcher
import forpleuvoir.mc.library.event.Event
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.commands.CommandBuildContext

/**
 * 客户端命令注册事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.client

 * 文件名 ClientCommandRegisterEvent

 * 创建时间 2022/8/13 15:45

 * @author forpleuvoir

 */
class ClientCommandRegisterEvent(
	@JvmField val dispatcher: CommandDispatcher<FabricClientCommandSource>,
	@JvmField val registryAccess: CommandBuildContext,
) : Event()