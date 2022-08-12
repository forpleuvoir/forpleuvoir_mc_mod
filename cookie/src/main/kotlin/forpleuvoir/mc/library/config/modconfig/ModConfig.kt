package forpleuvoir.mc.library.config.modconfig

import forpleuvoir.mc.library.api.Initializable
import net.fabricmc.api.EnvType
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicBoolean

/**
 * mod配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.modconfig

 * 文件名 ModConfig

 * 创建时间 2022/8/11 23:25

 * @author forpleuvoir

 */
interface ModConfig : Initializable {

	val env: EnvType

	/**
	 * 保存配置到文件
	 */
	fun save()

	fun saveAsync() {
		CompletableFuture.runAsync {
			save()
		}
	}

	/**
	 * 加载配置到内存
	 */
	fun load()

	fun loadAsync() {
		CompletableFuture.runAsync {
			load()
		}
	}

	/**
	 * 当配置关闭时
	 */
	fun close() = save()

	fun addCategory(category: ConfigCategory)

	val modId: String

	/**
	 * 获取所有配置
	 * @return List<Config<*>>
	 */
	val allCategory: Collection<ConfigCategory>

	/**
	 * 是否有配置发生改变
	 * @return Boolean
	 */
	val isChanged: AtomicBoolean
}