package forpleuvoir.mc.library.utils.color

import forpleuvoir.mc.library.utils.clamp
import forpleuvoir.mc.library.utils.i
import kotlin.math.floor

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.color

 * 文件名 HSBColor

 * 创建时间 2022/3/14 0:00

 * @author forpleuvoir

 */
class HSBColor(val color: Color) {

	/**
	 * 色相 Range(0.0f ~ 360.0f)
	 */
	var hue: Float = 360f
		set(value) {
			field = value.clamp(0, 360)
		}

	/**
	 * 饱和度 Range(0.0F ~ 1.0F)
	 */
	var saturation: Float = 1f
		set(value) {
			field = value.clamp(0, 1f)
		}

	/**
	 * 明度 Range(0.0F ~ 1.0F)
	 */
	var brightness: Float = 1f
		set(value) {
			field = value.clamp(0, 1f)
		}

	/**
	 * 透明度 Range(0.0F ~ 1.0F)
	 */
	var alpha: Float = 1f
		set(value) {
			field = value.clamp(0f, 1f)
		}

	var argb: Int
		get() {
			var r = 0
			var g = 0
			var b = 0
			val hue = this.hue / 360f
			if (saturation == 0f) {
				b = (brightness * 255.0f + 0.5f).toInt()
				g = b
				r = g
			} else {
				val h = (hue - floor(hue.toDouble()).toFloat()) * 6.0f
				val f = h - floor(h.toDouble()).toFloat()
				val p = brightness * (1.0f - saturation)
				val q = brightness * (1.0f - saturation * f)
				val t = brightness * (1.0f - saturation * (1.0f - f))
				when (h.toInt()) {
					0 -> {
						r = (brightness * 255.0f + 0.5f).toInt()
						g = (t * 255.0f + 0.5f).toInt()
						b = (p * 255.0f + 0.5f).toInt()
					}

					1 -> {
						r = (q * 255.0f + 0.5f).toInt()
						g = (brightness * 255.0f + 0.5f).toInt()
						b = (p * 255.0f + 0.5f).toInt()
					}

					2 -> {
						r = (p * 255.0f + 0.5f).toInt()
						g = (brightness * 255.0f + 0.5f).toInt()
						b = (t * 255.0f + 0.5f).toInt()
					}

					3 -> {
						r = (p * 255.0f + 0.5f).toInt()
						g = (q * 255.0f + 0.5f).toInt()
						b = (brightness * 255.0f + 0.5f).toInt()
					}

					4 -> {
						r = (t * 255.0f + 0.5f).toInt()
						g = (p * 255.0f + 0.5f).toInt()
						b = (brightness * 255.0f + 0.5f).toInt()
					}

					5 -> {
						r = (brightness * 255.0f + 0.5f).toInt()
						g = (p * 255.0f + 0.5f).toInt()
						b = (q * 255.0f + 0.5f).toInt()
					}
				}
			}
			return ((alpha * 255).i shl 24) or (r shl 16) or (g shl 8) or (b shl 0)
		}
		set(value) {
			val color = Color(value)
			val r = color.red
			val g = color.green
			val b = color.blue
			var hue: Float
			val saturation: Float
			val brightness: Float
			var cMax: Int = if (r > g) r else g
			if (b > cMax) cMax = b
			var cMin: Int = if (r < g) r else g
			if (b < cMin) cMin = b
			brightness = cMax.toFloat() / 255.0f
			saturation = if (cMax != 0) (cMax - cMin).toFloat() / cMax.toFloat() else 0f
			if (saturation == 0f) hue = 0f else {
				val redC: Float = (cMax - r).toFloat() / (cMax - cMin).toFloat()
				val greenC: Float = (cMax - g).toFloat() / (cMax - cMin).toFloat()
				val blueC: Float = (cMax - b).toFloat() / (cMax - cMin).toFloat()
				hue = if (r == cMax) blueC - greenC else if (g == cMax) 2.0f + redC - blueC else 4.0f + greenC - redC
				hue /= 6.0f
				if (hue < 0) hue += 1.0f
			}
			this.hue = hue * 360f
			this.saturation = saturation
			this.brightness = brightness
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

}