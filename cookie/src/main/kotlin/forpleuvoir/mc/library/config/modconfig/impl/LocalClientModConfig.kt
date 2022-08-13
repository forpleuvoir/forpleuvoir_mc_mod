package forpleuvoir.mc.library.config.modconfig.impl

import forpleuvoir.mc.library.config.ConfigUtil
import forpleuvoir.mc.library.config.modconfig.ClientModConfig
import java.nio.file.Path

/**
 * 客户端MOD配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig.impl

 * 文件名 ClientModConfig

 * 创建时间 2022/8/12 1:43

 * @author forpleuvoir

 */
open class LocalClientModConfig(override val modId: String) : LocalModConfig(modId), ClientModConfig {

	override fun localConfigPath(): Path = ConfigUtil.getClientConfigPath(modId)

}