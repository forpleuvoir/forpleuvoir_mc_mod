package forpleuvoir.mc.library.utils.color

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import forpleuvoir.mc.library.api.serialization.JsonSerializer

/**
 * 颜色

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.color

 * 文件名 Color

 * 创建时间 2022/8/8 22:17

 * @author forpleuvoir

 */
interface Color : JsonSerializer {

	companion object {

		@JvmStatic
		fun color(color: Int): Color = RGBColor(color)

		@JvmStatic
		val WHITE: Color get() = RGBColor(255, 255, 255)

		@JvmStatic
		val LIGHT_GRAY: Color get() = RGBColor(192, 192, 192)

		@JvmStatic
		val GRAY: Color get() = RGBColor(128, 128, 128)

		@JvmStatic
		val DARK_GRAY: Color get() = RGBColor(64, 64, 64)

		@JvmStatic
		val BLACK: Color get() = RGBColor(0, 0, 0)

		@JvmStatic
		val RED: Color get() = RGBColor(255, 0, 0)

		@JvmStatic
		val PINK: Color get() = RGBColor(255, 175, 175)

		@JvmStatic
		val ORANGE: Color get() = RGBColor(255, 200, 0)

		@JvmStatic
		val YELLOW: Color get() = RGBColor(255, 255, 0)

		@JvmStatic
		val GREEN: Color get() = RGBColor(0, 255, 0)

		@JvmStatic
		val MAGENTA: Color get() = RGBColor(255, 0, 255)

		@JvmStatic
		val CYAN: Color get() = RGBColor(0, 255, 255)

		@JvmStatic
		val BLUE: Color get() = RGBColor(0, 0, 255)
	}

	/**
	 * 获取颜色的RGBA信息
	 */
	var color: Int

	val rgbColor: RGBColor get() = RGBColor(color)

	val hsbColor: HSBColor get() = HSBColor(color)

	val hslColor: HSLColor get() = HSLColor(color)

	/**
	 * 获取调整透明的之后的颜色复制对象
	 *
	 * 透明的 = 当前透明 * opacity
	 *
	 * @param opacity Float {0.0 - 1.0}
	 * @return IColor
	 */
	fun opacity(opacity: Float): Color

	/**
	 * 修改alpha之后返回当前对象
	 * @param alpha Float {0.0 - 1.0}
	 * @return IColor
	 */
	fun alpha(alpha: Float): Color

	/**
	 * 获取颜色副本
	 * @return IColor
	 */
	fun copy(): Color

	val hexString: String
		get() {
			return "#${(color shr 24 and 0xFF).toString(16).run { formatStr(this) }}" +
					(color shr 16 and 0xFF).toString(16).run { formatStr(this) } +
					(color shr 8 and 0xFF).toString(16).run { formatStr(this) } +
					(color shr 0 and 0xFF).toString(16).run { formatStr(this) }
		}

	private fun formatStr(str: String): String {
		return (if (str == "0") "00" else if (str.length == 1) "0$str" else str).uppercase()
	}


	override val serialization: JsonElement
		get() = JsonPrimitive(color)

	override fun deserialize(serializedObject: JsonElement) {
		serializedObject.asJsonPrimitive.run {
			color = this.asInt
		}
	}
}