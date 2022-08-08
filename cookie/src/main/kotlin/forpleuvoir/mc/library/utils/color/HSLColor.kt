package forpleuvoir.mc.library.utils.color

import com.google.gson.JsonElement
import forpleuvoir.mc.library.utils.*

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.color

 * 文件名 HSLColor

 * 创建时间 2022/3/13 23:57

 * @author forpleuvoir

 */
class HSLColor() : Color {

	constructor(rgba: Int) : this() {
		this.color = rgba
	}

	constructor(color: RGBColor) : this(color.color)

	constructor(hsbColor: HSBColor) : this(hsbColor.color)

	constructor(hslColor: HSLColor) : this(hslColor.color)

	constructor(hue: Float = 360f, saturation: Float = 1.0f, lightness: Float = 1.0f, alpha: Float = 1.0f) : this() {
		this.hue = hue
		this.saturation = saturation
		this.lightness = lightness
		this.alpha = alpha
	}

	/**
	 * 色相
	 */
	var hue: Float = 360f
		set(value) {
			field = value.clamp(0, 360)
		}

	/**
	 * 饱和度
	 */
	var saturation: Float = 1f
		set(value) {
			field = value.clamp(0f, 1f)
		}

	/**
	 * 亮度
	 */
	var lightness: Float = 1f
		set(value) {
			field = value.clamp(0f, 1f)
		}

	var alpha: Float = 1f
		set(value) {
			field = value.clamp(0f, 1f)
		}


	override var color: Int
		get() {
			val r: Float
			val g: Float
			val b: Float
			var hue: Float = this.hue / 360
			val saturation: Float = this.saturation
			val lightness: Float = this.lightness
			val normalize: (Float, Float, Float) -> Float = { q, p, color ->
				if (color < 1.0f) {
					p + (q - p) * color
				} else if (color < 3.0f) {
					q
				} else if (color < 4.0f) {
					(p + (q - p) * (4.0f - color))
				} else p
			}
			if (saturation > 0.0f) {
				hue = if (hue < 1.0f) hue * 6.0f else 0.0f
				val q = lightness + saturation * if (lightness > 0.5f) 1.0f - lightness else lightness
				val p = 2.0f * lightness - q
				r = normalize(q, p, if (hue < 4.0f) hue + 2.0f else hue - 4.0f)
				g = normalize(q, p, hue)
				b = normalize(q, p, if (hue < 2.0f) hue + 4.0f else hue - 2.0f)
			} else {
				r = lightness
				g = lightness
				b = lightness
			}
			return ((alpha * 255).i shl 24) or ((r * 255).i shl 16) or ((g * 255).i shl 8) or ((b * 255).i shl 0)
		}
		set(value) {
			val color = RGBColor(value)
			val r = color.redF
			val g = color.greenF
			val b = color.blueF
			val max = max(r, g, b)
			val min = min(r, g, b)
			val summa = max + min
			var saturation = max - min
			if (saturation > 0.0f) {
				saturation /= if (summa > 1.0f) 2.0f - summa else summa
			}
			this.hue = run {
				var hue1 = max - min
				if (hue1 > 0.0f) {
					if (max == r) {
						hue1 = (g - b) / hue1
						if (hue1 < 0.0f) {
							hue1 += 6.0f
						}
					} else if (max == g) {
						hue1 = 2.0f + (b - r) / hue1
					} else {
						hue1 = 4.0f + (r - g) / hue1
					}
					hue1 /= 6.0f
				}
				hue1 * 360
			}
			this.saturation = saturation
			this.lightness = summa / 2.0f
			this.alpha = color.alphaF
		}

	override fun alpha(alpha: Float): HSLColor {
		this.alpha = alpha
		return this
	}

	override fun opacity(opacity: Float): HSLColor {
		return HSLColor(this).apply { alpha = (alpha * opacity.clamp(0.0, 1.0)).f }
	}

	override fun copy(): HSLColor = hslColor

	override val serialization: JsonElement
		get() = jsonObject {
			"hue" at hue
			"saturation" at saturation
			"lightness" at lightness
			"alpha" at alpha
		}

	override fun deserialize(serializedObject: JsonElement) {
		serializedObject.asJsonObject.apply {
			hue = this["hue"].asFloat
			saturation = this["saturation"].asFloat
			lightness = this["lightness"].asFloat
			alpha = this["alpha"].asFloat
		}
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as HSLColor

		if (hashCode() != other.hashCode()) return false

		return true
	}

	override fun hashCode(): Int = color

	override fun toString(): String {
		return "HSLColor(hue=$hue, saturation=$saturation, lightness=$lightness, alpha=$alpha, hexString='$hexString')"
	}


}