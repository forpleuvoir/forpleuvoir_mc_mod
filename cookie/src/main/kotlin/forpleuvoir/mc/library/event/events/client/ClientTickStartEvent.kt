package forpleuvoir.mc.library.event.events.client

import forpleuvoir.mc.library.event.Event
import net.minecraft.client.Minecraft


/**
 * 客户端tick开始事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.client

 * 文件名 ClientTickStartEvent

 * 创建时间 2022/8/7 12:56

 * @author forpleuvoir

 */
class ClientTickStartEvent(@JvmField val minecraftClient: Minecraft) : Event()