package forpleuvoir.mc.library.input

import forpleuvoir.mc.library.api.Option
import forpleuvoir.mc.library.utils.text.translatable
import net.minecraft.network.chat.MutableComponent

/**
 * 按键触发模式

 * 项目名 cookie

 * 包名 forpleuvoir.mc.library.input


 * 文件名 KeyTriggerMode

 * 创建时间 2022/2/16 21:31

 * @author forpleuvoir

 */
enum class KeyTriggerMode(override val key: String) : Option {

	OnPress("on_press"),
	OnLongPress("on_long_press"),
	OnRelease("on_release"),
	Pressed("press"),
	BOTH("both");

	override val displayName: MutableComponent
		get() = "cookie.key_bind.trigger_mode.${key}".translatable

	override val description: MutableComponent
		get() = "cookie.key_bind.trigger_mode.${key}.description".translatable

	val allOption: List<KeyTriggerMode>
		get() = values().toList()

	fun fromKey(key: String): KeyTriggerMode {
		allOption.forEach {
			if (it.key == key) return it
		}
		return OnRelease
	}

}