package forpleuvoir.mc.library.event.events.client

import forpleuvoir.mc.library.event.Event
import net.minecraft.client.Minecraft

/**
 * 客户端关闭事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.client

 * 文件名 ClientStopEvent

 * 创建时间 2022/8/12 22:55

 * @author forpleuvoir

 */
class ClientStopEvent(@JvmField val minecraftClient: Minecraft) : Event()