package forpleuvoir.mc.library.event.events.server

import com.mojang.brigadier.CommandDispatcher
import forpleuvoir.mc.library.event.Event
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

/**
 * 服务端命令注册事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.server

 * 文件名 ServerCommandRegisterEvent

 * 创建时间 2022/8/13 15:51

 * @author forpleuvoir

 */
class ServerCommandRegisterEvent(
	@JvmField
	val dispatcher: CommandDispatcher<CommandSourceStack>,
	@JvmField
	val registryAccess: CommandBuildContext,
	@JvmField
	val environment: Commands.CommandSelection,
) : Event()