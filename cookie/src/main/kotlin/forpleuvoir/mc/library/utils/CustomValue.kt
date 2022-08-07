package forpleuvoir.mc.library.utils

import net.fabricmc.loader.api.FabricLoader

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils

 * 文件名 CustomValue

 * 创建时间 2022/8/7 16:44

 * @author forpleuvoir

 */


val modPacks: Set<String>
	get() =
		HashSet<String>().also { packs ->
			FabricLoader.getInstance().allMods.forEach {
				it.metadata.customValues["cookie"]?.apply {
					asObject.get("package")?.asArray?.onEach { value ->
						packs.add(value.asString)
					}
				}
			}
		}


val eventPacks: Set<String>
	get() =
		HashSet<String>().also { packs ->
			FabricLoader.getInstance().allMods.forEach {
				it.metadata.customValues["cookie"]?.apply {
					asObject.get("events")?.asArray?.onEach { value ->
						packs.add(value.asString)
					}
				}
			}
		}

