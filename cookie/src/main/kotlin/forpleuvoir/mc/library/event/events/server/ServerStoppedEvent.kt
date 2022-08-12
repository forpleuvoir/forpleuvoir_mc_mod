package forpleuvoir.mc.library.event.events.server

import forpleuvoir.mc.library.event.Event
import net.minecraft.server.MinecraftServer

/**
 * 服务端关闭事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.server

 * 文件名 ServerStoppedEvent

 * 创建时间 2022/8/13 1:29

 * @author forpleuvoir

 */
class ServerStoppedEvent(@JvmField val server: MinecraftServer) : Event()