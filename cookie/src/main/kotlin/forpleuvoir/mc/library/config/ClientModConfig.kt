package forpleuvoir.mc.library.config

import forpleuvoir.mc.cookie.config.CookieConfigs
import java.nio.file.Path

/**
 * 客户端MOD配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config

 * 文件名 ClientModConfig

 * 创建时间 2022/8/12 1:43

 * @author forpleuvoir

 */
open class ClientModConfig(override val modId: String) : AbstractModConfig(modId) {
	override fun getConfigPath(): Path = ConfigUtil.getClientConfigPath(CookieConfigs.modId)
}