package forpleuvoir.mc.library.event.events.client

import forpleuvoir.mc.library.event.Event
import net.minecraft.client.Minecraft

/**
 * 客户端tick事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events.client

 * 文件名 ClientTickEvent

 * 创建时间 2022/8/15 0:26

 * @author forpleuvoir

 */

/**
 * 客户端tick结束事件

 * @property minecraftClient Minecraft
 * @constructor
 */
class ClientTickEndEvent(@JvmField val minecraftClient: Minecraft) : Event()

/**
 * 客户端tick开始事件
 * @property minecraftClient Minecraft
 * @constructor
 */
class ClientTickStartEvent(@JvmField val minecraftClient: Minecraft) : Event()
