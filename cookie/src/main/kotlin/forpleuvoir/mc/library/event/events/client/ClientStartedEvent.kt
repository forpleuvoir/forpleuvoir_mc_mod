package forpleuvoir.mc.library.event.events.client

import forpleuvoir.mc.library.event.Event
import net.minecraft.client.Minecraft

/**
 * 客户端启动完成事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.client

 * 文件名 ClientStartedEvent

 * 创建时间 2022/8/7 12:51

 * @author forpleuvoir

 */
class ClientStartedEvent(@JvmField val minecraftClient: Minecraft) : Event()