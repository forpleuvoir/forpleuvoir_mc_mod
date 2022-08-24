package forpleuvoir.mc.library.event.events

import forpleuvoir.mc.library.event.Event
import net.fabricmc.loader.api.metadata.ModMetadata

/**
 * 模组初始化时

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event.events

 * 文件名 ModInitializerEvent

 * 创建时间 2022/8/24 17:10

 * @author forpleuvoir

 */
class ModInitializerEvent(@JvmField val meta: ModMetadata) : Event()