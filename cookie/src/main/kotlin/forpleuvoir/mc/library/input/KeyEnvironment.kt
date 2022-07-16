package forpleuvoir.mc.library.input

import forpleuvoir.mc.library.api.Option
import forpleuvoir.mc.library.utils.mc
import forpleuvoir.mc.library.utils.text.translatable
import net.minecraft.network.chat.MutableComponent

/**
 * 按键环境

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.input

 * 文件名 KeyEnvironment

 * 创建时间 2022/7/15 21:35

 * @author forpleuvoir

 */
enum class KeyEnvironment(override val key: String) : Option {
	InGame("in_game"),
	InScreen("in_screen"),
	Both("both");

	override val displayName: MutableComponent
		get() = "cookie.key_bind.environment.${key}".translatable

	override val description: MutableComponent
		get() = displayName

	fun envMatch(): Boolean {
		if (this == Both) return true
		return this == currentEnv()
	}

	inline fun onEnvMatch(callback: () -> Unit) {
		if (envMatch()) callback.invoke()
	}

	fun fromKey(key: String): KeyEnvironment {
		allOption.forEach {
			if (it.key == key) return it
		}
		return InGame
	}

	val allOption: List<KeyEnvironment>
		get() = values().toList()

}

fun currentEnv(): KeyEnvironment {
	// TODO: 添加屏幕实现后要判断 屏幕
	return if (mc.screen == null) KeyEnvironment.InGame else KeyEnvironment.InScreen
}