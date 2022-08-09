package forpleuvoir.mc.library.utils.color

import com.google.gson.JsonElement
import forpleuvoir.mc.library.api.serialization.JsonSerializer
import forpleuvoir.mc.library.utils.clamp
import forpleuvoir.mc.library.utils.f
import forpleuvoir.mc.library.utils.i
import forpleuvoir.mc.library.utils.jsonObject

/**
 * 颜色

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.color

 * 文件名 RGBColor

 * 创建时间 2022/8/8 20:54

 * @author forpleuvoir

 */
class RGBColor() : Color, JsonSerializer {
	companion object {
		private const val MAX_INT_VALUE: Int = 255
		private const val MIN_INT_VALUE: Int = 0
		private const val MAX_FLOAT_VALUE: Float = 1.0f
		private const val MIN_FLOAT_VALUE: Float = 0.0f
	}

	constructor(
		red: Int = MAX_INT_VALUE,
		green: Int = MAX_INT_VALUE,
		blue: Int = MAX_INT_VALUE,
		alpha: Int = MAX_INT_VALUE,
	) : this() {
		this.red = red
		this.green = green
		this.blue = blue
		this.alpha = alpha
	}

	constructor(
		redF: Float = MAX_FLOAT_VALUE,
		greenF: Float = MAX_FLOAT_VALUE,
		blueF: Float = MAX_FLOAT_VALUE,
		alphaF: Float = MAX_FLOAT_VALUE,
	) : this() {
		this.redF = redF
		this.greenF = greenF
		this.blueF = blueF
		this.alphaF = alphaF
	}

	constructor(rgba: Int) : this() {
		this.color = rgba
	}

	constructor(color: RGBColor) : this(color.red, color.green, color.blue, color.alpha)

	constructor(HSBColor: HSBColor) : this(HSBColor.color)

	constructor(hslColor: HSLColor) : this(hslColor.color)

	/**
	 * 红色值
	 */
	override var red: Int = MAX_INT_VALUE
		set(value) {
			field = value.clamp(MIN_INT_VALUE, MAX_INT_VALUE)
		}

	var redF: Float
		set(value) {
			red = (value.clamp(MIN_FLOAT_VALUE, MAX_FLOAT_VALUE) * MAX_INT_VALUE).i
		}
		get() = red / MAX_INT_VALUE.f

	override var green: Int = MAX_INT_VALUE
		set(value) {
			field = value.clamp(MIN_INT_VALUE, MAX_INT_VALUE)
		}

	var greenF: Float
		set(value) {
			green = (value.clamp(MIN_FLOAT_VALUE, MAX_FLOAT_VALUE) * MAX_INT_VALUE).i
		}
		get() = green / MAX_INT_VALUE.f

	override var blue: Int = MAX_INT_VALUE
		set(value) {
			field = value.clamp(MIN_INT_VALUE, MAX_INT_VALUE)
		}

	var blueF: Float
		set(value) {
			blue = (value.clamp(MIN_FLOAT_VALUE, MAX_FLOAT_VALUE) * MAX_INT_VALUE).i
		}
		get() = blue / MAX_INT_VALUE.f

	override var alpha: Int = MAX_INT_VALUE
		set(value) {
			field = value.clamp(MIN_INT_VALUE, MAX_INT_VALUE)
		}

	var alphaF: Float
		set(value) {
			alpha = (value.clamp(MIN_FLOAT_VALUE, MAX_FLOAT_VALUE) * MAX_INT_VALUE).i
		}
		get() = alpha / MAX_INT_VALUE.f

	override var color: Int
		get() {
			return (alpha and 0xFF shl 24) or
					(red and 0xFF shl 16) or
					(green and 0xFF shl 8) or
					(blue and 0xFF shl 0)
		}
		set(value) {
			alpha = value shr 24 and 0XFF
			red = value shr 16 and 0XFF
			green = value shr 8 and 0XFF
			blue = value shr 0 and 0XFF
		}


	override fun alphaF(alphaF: Float): RGBColor {
		this.alphaF = alphaF
		return this
	}

	override fun alpha(alpha: Int): Color {
		this.alpha = alpha
		return this
	}

	override fun opacity(opacity: Float): Color {
		return RGBColor(this).apply { alpha = (alpha * opacity.clamp(0.0, 1.0)).i }
	}


	override fun copy(): RGBColor = rgbColor

	override val serialization: JsonElement
		get() = jsonObject {
			"red" at red
			"green" at green
			"blue" at blue
			"alpha" at alpha
		}

	override fun deserialize(serializedObject: JsonElement) {
		serializedObject.asJsonObject.apply {
			red = this["red"].asInt
			green = this["green"].asInt
			blue = this["blue"].asInt
			alpha = this["alpha"].asInt
		}
	}

	override fun hashCode(): Int = color

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as RGBColor

		if (color != other.color) return false

		return true
	}

	override fun toString(): String {
		return "RGBColor(red=$red, green=$green, blue=$blue, alpha=$alpha, hexString='$hexString')"
	}


}