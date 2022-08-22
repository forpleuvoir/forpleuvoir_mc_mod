package forpleuvoir.mc.library.api.impl

import forpleuvoir.mc.library.api.Savable
import net.minecraft.server.MinecraftServer

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.api.impl

 * 文件名 ServerSavable

 * 创建时间 2022/8/12 13:43

 * @author forpleuvoir

 */
interface ServerSavable : Savable {

	val server: MinecraftServer

	fun init(server: MinecraftServer)

}