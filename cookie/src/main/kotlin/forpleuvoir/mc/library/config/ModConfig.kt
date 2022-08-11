package forpleuvoir.mc.library.config

import forpleuvoir.mc.library.api.Initializable
import java.nio.file.Path

/**
 * mod配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config

 * 文件名 ModConfig

 * 创建时间 2022/8/11 23:25

 * @author forpleuvoir

 */
interface ModConfig : Initializable {

	/**
	 * 保存配置到文件
	 * @param path Path 配置文件目录路径
	 */
	fun save(path: Path)

	/**
	 * 加载配置到内存
	 * @param path Path 配置文件目录路径
	 */
	fun load(path: Path)

	/**
	 * 当配置关闭时
	 * @param path Path
	 */
	fun close(path: Path) = save(path)

	fun addCategory(category: ConfigCategory)

	val modId: String

	/**
	 * 获取所有配置
	 * @return List<Config<*>>
	 */
	val allCategory: List<ConfigCategory>

	/**
	 * 是否有配置发生改变
	 * @return Boolean
	 */
	val isChanged: Boolean
}