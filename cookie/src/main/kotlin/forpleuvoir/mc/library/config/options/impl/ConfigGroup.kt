package forpleuvoir.mc.library.config.options.impl

import com.google.common.collect.ImmutableSet
import com.google.gson.JsonElement
import forpleuvoir.mc.library.config.Config
import forpleuvoir.mc.library.config.ConfigType
import forpleuvoir.mc.library.config.ConfigTypes
import forpleuvoir.mc.library.config.options.ConfigBase
import forpleuvoir.mc.library.config.options.IConfigGroup
import forpleuvoir.mc.library.utils.jsonObject
import net.minecraft.network.chat.MutableComponent

/**
 * 配置组 配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options.impl

 * 文件名 ConfigGroup

 * 创建时间 2022/7/6 0:43

 * @author forpleuvoir

 */
open class ConfigGroup(
	override val key: String,
	override val displayName: MutableComponent,
	override val description: MutableComponent,
	final override val defaultValue: Set<Config<*>>,
) : ConfigBase<Set<Config<*>>>(), IConfigGroup {

	override val type: ConfigType
		get() = ConfigTypes.GROUP

	override var configValue: Set<Config<*>> = ImmutableSet.copyOf(defaultValue)

	override fun resetDefValue() {
		setValue(ImmutableSet.copyOf(defaultValue))
	}

	override fun getConfigFromKey(key: String): Config<*>? {
		return getValue().find { it.key == key }
	}

	override fun getKeys(): Set<String> {
		return ImmutableSet.Builder<String>().apply { getValue().forEach { add(it.key) } }.build()
	}

	override fun setFromJson(jsonElement: JsonElement): Boolean {
		if (!jsonElement.isJsonObject) return false
		val jsonObject = jsonElement.asJsonObject
		this.configValue.forEach {
			it.apply { jsonObject[it.key].deserialize() }
		}
		return true
	}

	override val serialize: JsonElement
		get() = jsonObject {
			getValue().forEach { it.key at it.serialize }
		}


}