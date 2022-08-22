package forpleuvoir.mc.library.api.impl

import forpleuvoir.mc.library.api.Savable
import net.minecraft.client.Minecraft

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.api.impl

 * 文件名 ClientModConfig

 * 创建时间 2022/8/12 13:49

 * @author forpleuvoir

 */
interface ClientSavable : Savable {

	val client: Minecraft
	fun init(client: Minecraft)

}