package forpleuvoir.mc.library.event.events.server

import forpleuvoir.mc.library.event.Event
import net.minecraft.server.MinecraftServer

/**
 * 服务端生命周期事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.server

 * 文件名 ServerLifecycleEvent

 * 创建时间 2022/8/15 0:29

 * @author forpleuvoir

 */

/**
 * 服务端启动完成事件
 * @property server MinecraftServer
 * @constructor
 */
class ServerStartedEvent(@JvmField val server: MinecraftServer) : Event()

/**
 * 服务端启动中事件
 * @property server MinecraftServer
 * @constructor
 */
class ServerStartingEvent(@JvmField val server: MinecraftServer) : Event()

/**
 * 服务端关闭事件
 * @property server MinecraftServer
 * @constructor
 */
class ServerStoppedEvent(@JvmField val server: MinecraftServer) : Event()

/**
 * 服务端关闭中事件
 * @property server MinecraftServer
 * @constructor
 */
class ServerStoppingEvent(@JvmField val server: MinecraftServer) : Event()