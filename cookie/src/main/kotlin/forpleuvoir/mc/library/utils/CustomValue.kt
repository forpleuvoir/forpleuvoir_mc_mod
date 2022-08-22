package forpleuvoir.mc.library.utils

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.entrypoint.EntrypointContainer
import net.fabricmc.loader.api.metadata.ModMetadata

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils

 * 文件名 CustomValue

 * 创建时间 2022/8/7 16:44

 * @author forpleuvoir

 */

val loader: FabricLoader = FabricLoader.getInstance()

val modPacks: Map<ModMetadata, Set<String>> by lazy {
	ImmutableMap.builder<ModMetadata, Set<String>>().also { packs ->
		loader.allMods.forEach {
			val list = ImmutableSet.builder<String>()
			it.metadata.customValues["cookie"]?.apply {
				asObject.get("package")?.asArray?.onEach { value ->
					list.add(value.asString)
				}
				packs.put(it.metadata, list.build())
			}
		}
	}.build()
}

fun scanModPackage(predicate: (Class<*>) -> Boolean = { true }): Map<ModMetadata, Set<Class<*>>> {
	val builder = ImmutableMap.builder<ModMetadata, Set<Class<*>>>()
	modPacks.forEach { (metadata, packs) ->
		val set = ImmutableSet.builder<Class<*>>()
		packs.forEach {
			set.addAll(ReflectionUtil.scanPackage(it, predicate))
		}
		builder.put(metadata, set.build())
	}
	return builder.build()
}


fun <T> getEntrypoints(name: String, type: Class<T>, scope: (EntrypointContainer<T>) -> Unit) {
	loader.getEntrypointContainers(name, type).forEach {
		scope(it)
	}
}