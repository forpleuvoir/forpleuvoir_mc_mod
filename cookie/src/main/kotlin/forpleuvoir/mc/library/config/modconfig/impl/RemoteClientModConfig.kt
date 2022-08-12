package forpleuvoir.mc.library.config.modconfig.impl

import forpleuvoir.mc.library.config.modconfig.ClientModConfig
import forpleuvoir.mc.library.utils.mc
import net.minecraft.client.Minecraft

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig.impl

 * 文件名 RemoteClientModConfig

 * 创建时间 2022/8/12 15:30

 * @author forpleuvoir

 */
open class RemoteClientModConfig(override val modId: String, saveUrl:String, loadUrl:String):RemoteModConfig(saveUrl, loadUrl),ClientModConfig {


}