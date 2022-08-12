package forpleuvoir.mc.library.utils

import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.entrypoint.EntrypointContainer

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils

 * 文件名 CustomValue

 * 创建时间 2022/8/7 16:44

 * @author forpleuvoir

 */

val loader: FabricLoader = FabricLoader.getInstance()

val modPacks: Set<String>
	get() =
		HashSet<String>().also { packs ->
			loader.allMods.forEach {
				it.metadata.customValues["cookie"]?.apply {
					asObject.get("package")?.asArray?.onEach { value ->
						packs.add(value.asString)
					}
				}
			}
		}

fun <T> getEntrypoints(name: String, type: Class<T>, scope: (EntrypointContainer<T>) -> Unit) {
	loader.getEntrypointContainers(name, type).forEach {
		scope(it)
	}
}