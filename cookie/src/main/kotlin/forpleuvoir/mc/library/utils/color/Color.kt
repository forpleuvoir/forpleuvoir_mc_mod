package forpleuvoir.mc.library.utils.color

import com.google.gson.JsonElement
import forpleuvoir.mc.library.api.serialization.JsonSerializer
import forpleuvoir.mc.library.utils.clamp
import forpleuvoir.mc.library.utils.jsonObject


/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.color

 * 文件名 Color

 * 创建时间 2021/12/13 18:55

 * @author forpleuvoir

 */
interface Color<T : Number> : JsonSerializer {

	companion object {
		fun copy(color: Color<out Number>): Color<out Number> {
			return if (color.red is Int) {
				Color4i(color.red as Int, color.green as Int, color.blue as Int, color.alpha as Int)
			} else {
				Color4f(color.red as Float, color.green as Float, color.blue as Float, color.alpha as Float)
			}
		}
	}

	val rgba: Int
	val hexString: String
		get() = "#${String.format("%x", hashCode()).uppercase()}"

	fun rgba(alpha: T): Int
	fun fromInt(color: Int): Color<T>
	fun alpha(alpha: T): Color<T> {
		return fromInt(this.rgba(alpha))
	}

	fun opacity(opacity: Double): Color<T>

	fun formatStr(str: String): String {
		return (if (str == "0") "00" else if (str.length == 1) "0$str" else str).uppercase()
	}

	override val serialization: JsonElement
		get() = jsonObject {
			"red" at red
			"green" at green
			"blue" at blue
			"alpha" at alpha
		}

	fun fixAllValue() {
		red = fixValue(red)
		green = fixValue(green)
		blue = fixValue(blue)
		alpha = fixValue(alpha)
	}

	@Suppress("unchecked_cast")
	fun fixValue(value: T): T {
		return (value as Number).clamp(minValue as Number, maxValue as Number) as T
	}

	fun toHsl(): HSLColor {
		return HSLColor(this)
	}

	fun fromHsl(hslColor: HSLColor) {
		this.fromInt(hslColor.toRgba())
	}

	fun toHsv(): HSVColor {
		return HSVColor(this)
	}

	fun fromHsv(hsvColor: HSVColor) {
		this.fromInt(hsvColor.toRgba())
	}

	val minValue: T
	val maxValue: T

	var red: T
	var green: T
	var blue: T
	var alpha: T

}