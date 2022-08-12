package forpleuvoir.mc.library.config.modconfig.impl

import com.google.gson.JsonObject
import forpleuvoir.mc.cookie.util.logger
import forpleuvoir.mc.library.config.ConfigUtil
import forpleuvoir.mc.library.utils.net.httpGet
import forpleuvoir.mc.library.utils.net.httpPost
import forpleuvoir.mc.library.utils.toJsonObject
import forpleuvoir.mc.library.utils.toJsonStr

/**
 * 远程mod配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig.impl

 * 文件名 RemoteModConfig

 * 创建时间 2022/8/12 13:55

 * @author forpleuvoir

 */
abstract class RemoteModConfig(private val saveUrl: String, private val loadUrl: String) : AbstractModConfig() {

	private val log = logger()

	override fun save() {
		ConfigUtil.run {
			val json = JsonObject()
			allCategory.forEach {
				writeConfigCategory(json, it)
			}
			httpPost(saveUrl, json.toJsonStr()).sendAsync {
				if (it.statusCode() != 200) {
					log.error("config save failed, url:{},json:{}", saveUrl, json.toJsonStr())
				} else {
					isChanged.set(false)
				}
			}
		}
	}

	override fun load() {
		httpGet(loadUrl).sendAsync {
			if (it.statusCode() != 200) {
				log.error("config load failed, url:{}", saveUrl)
			} else {
				it.body().toJsonObject().run {
					allCategory.forEach { config ->
						ConfigUtil.readConfigCategory(this, config)
					}
				}
			}
		}
	}

}