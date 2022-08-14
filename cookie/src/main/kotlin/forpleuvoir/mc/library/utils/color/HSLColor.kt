package forpleuvoir.mc.library.utils.color

import forpleuvoir.mc.library.utils.clamp
import forpleuvoir.mc.library.utils.color.Color.Companion.fixHueValue
import forpleuvoir.mc.library.utils.color.Color.Companion.fixValue
import forpleuvoir.mc.library.utils.i
import forpleuvoir.mc.library.utils.max
import forpleuvoir.mc.library.utils.min

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.color

 * 文件名 HSLColor

 * 创建时间 2022/3/13 23:57

 * @author forpleuvoir

 */
class HSLColor(val color: Color) {

	val checkedRange: Boolean get() = color.checkedRange

	/**
	 * 色相 Range(0.0f ~ 360.0f)
	 */
	var hue: Float = 360f
		set(value) {
			field = value.fixHueValue(checkedRange, "Hue")
		}

	/**
	 * 饱和度 Range(0.0F ~ 1.0F)
	 */
	var saturation: Float = 1f
		set(value) {
			field = value.fixValue(checkedRange, "Saturation")
		}

	/**
	 * 亮度 Range(0.0F ~ 1.0F)
	 */
	var lightness: Float = 1f
		set(value) {
			field = value.fixValue(checkedRange, "Lightness")
		}

	/**
	 * 不透明度 Range(0.0F ~ 1.0F)
	 */
	var alpha: Float = 1f
		set(value) {
			field = value.fixValue(checkedRange, "Alpha")
		}


	var argb: Int
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
			val color = Color(value)
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
			this.saturation = saturation.clamp(0f, 1.0f)
			this.lightness = (summa / 2.0f).clamp(0f, 1.0f)
			this.alpha = color.alphaF
		}

	init {
		this.argb = color.argb
	}

	/**
	 * 将此对象颜色同步到[color]
	 */
	fun syncToColor() {
		color.argb = this.argb
	}

	/**
	 * 将[color]颜色同步到此对象
	 */
	fun syncFromColor() {
		this.argb = color.argb
	}

	override fun toString(): String {
		return "HSLColor(hue=$hue, saturation=$saturation, lightness=$lightness, alpha=$alpha)"
	}


}