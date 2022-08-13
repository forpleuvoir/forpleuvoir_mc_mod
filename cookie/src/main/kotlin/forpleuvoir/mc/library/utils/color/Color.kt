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
		fun decode(color: Int): Color = RGBColor(color)

		/**
		 * 字符串格式的颜色
		 *
		 * @param color String #FF66CCFF #ARGB
		 * @return Int
		 */
		@JvmStatic
		fun decode(color: String): Int {
			val hex: String = color.replace(Regex("0x|0X"), "").replace("#", "")
			if (hex.length == 8) {
				val alpha: Int = hex.substring(0, 2).toInt(16)
				val red: Int = hex.substring(2, 4).toInt(16)
				val green: Int = hex.substring(4, 6).toInt(16)
				val blue: Int = hex.substring(6, 8).toInt(16)
				return alpha shl 24 or (red shl 16) or (green shl 8) or blue
			}
			return 0
		}

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

	val red: Int get() = color shr 16 and 0xFF

	val green: Int get() = color shr 8 and 0xFF

	val blue: Int get() = color shr 0 and 0xFF

	val alpha: Int get() = color shr 24 and 0xFF

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
	 * @param alphaF Float {0.0 - 1.0}
	 * @return [Color]
	 */
	fun alphaF(alphaF: Float): Color

	/**
	 * 修改alpha之后返回当前对象
	 * @param alpha Int {0 - 255}
	 * @return [Color]
	 */
	fun alpha(alpha: Int): Color {
		return alphaF(alpha / 255f)
	}

	/**
	 * 获取颜色副本
	 * @return IColor
	 */
	fun copy(): Color

	val hexString: String
		get() {
			val formatStr: (String) -> String = { str ->
				(if (str == "0") "00" else if (str.length == 1) "0$str" else str).uppercase()
			}
			return "#${alpha.toString(16).run { formatStr(this) }}" +
					red.toString(16).run { formatStr(this) } +
					green.toString(16).run { formatStr(this) } +
					blue.toString(16).run { formatStr(this) }
		}

	override val serialization: JsonElement
		get() = JsonPrimitive(color)

	override fun deserialize(serializedObject: JsonElement) {
		serializedObject.asJsonPrimitive.run {
			if (this.isNumber) {
				color = this.asInt
			}
			if (this.isString) {
				color = decode(this.asString)
			}
		}
	}
}