package forpleuvoir.mc.library.event.events.client

import forpleuvoir.mc.library.event.Event
import net.minecraft.client.Minecraft

/**
 * 客户端生命周期事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.client.render

 * 文件名 ClientLifecycleEvent

 * 创建时间 2022/8/15 0:23

 * @author forpleuvoir

 */


/**
 * 客户端启动完成事件
 * @property minecraftClient Minecraft
 * @constructor
 */
class ClientStartedEvent(@JvmField val minecraftClient: Minecraft) : Event()

/**
 * 客户端启动中事件
 * @property minecraftClient Minecraft
 * @constructor
 */
class ClientStartingEvent(@JvmField val minecraftClient: Minecraft) : Event()

/**
 * 客户端关闭事件
 * @property minecraftClient Minecraft
 * @constructor
 */
class ClientStopEvent(@JvmField val minecraftClient: Minecraft) : Event()