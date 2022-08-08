package forpleuvoir.mc.library.utils.color

import com.google.gson.JsonElement
import forpleuvoir.mc.library.utils.clamp
import forpleuvoir.mc.library.utils.f
import forpleuvoir.mc.library.utils.i
import forpleuvoir.mc.library.utils.jsonObject
import kotlin.math.floor

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.color

 * 文件名 HSBColor

 * 创建时间 2022/3/14 0:00

 * @author forpleuvoir

 */
class HSBColor() : Color {

	constructor(rgba: Int) : this() {
		this.color = rgba
	}

	constructor(color: RGBColor) : this(color.color)

	constructor(hslColor: HSLColor) : this(hslColor.color)

	constructor(hsbColor: HSBColor) : this(hsbColor.color)

	constructor(hue: Float = 360.0f, saturation: Float = 1.0f, value: Float = 1.0f, alpha: Float = 1.0f) : this() {
		this.hue = hue
		this.saturation = saturation
		this.brightness = value
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
			field = value.clamp(0, 1f)
		}

	/**
	 * 明度
	 */
	var brightness: Float = 1f
		set(value) {
			field = value.clamp(0, 1f)
		}

	var alpha: Float = 1f
		set(value) {
			field = value.clamp(0f, 1f)
		}

	override var color: Int
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
			val color = RGBColor(value)
			val r = color.red
			val g = color.green
			val b = color.blue
			var hue: Float
			val saturation: Float
			val brightness: Float
			var cmax: Int = if (r > g) r else g
			if (b > cmax) cmax = b
			var cmin: Int = if (r < g) r else g
			if (b < cmin) cmin = b
			brightness = cmax.toFloat() / 255.0f
			saturation = if (cmax != 0) (cmax - cmin).toFloat() / cmax.toFloat() else 0f
			if (saturation == 0f) hue = 0f else {
				val redc: Float = (cmax - r).toFloat() / (cmax - cmin).toFloat()
				val greenc: Float = (cmax - g).toFloat() / (cmax - cmin).toFloat()
				val bluec: Float = (cmax - b).toFloat() / (cmax - cmin).toFloat()
				hue = if (r == cmax) bluec - greenc else if (g == cmax) 2.0f + redc - bluec else 4.0f + greenc - redc
				hue /= 6.0f
				if (hue < 0) hue += 1.0f
			}
			this.hue = hue * 360f
			this.saturation = saturation
			this.brightness = brightness
			this.alpha = color.alphaF
		}

	override fun alpha(alpha: Float): HSBColor {
		this.alpha = alpha
		return this
	}

	override fun opacity(opacity: Float): HSBColor {
		return HSBColor(this).apply { alpha = (alpha * opacity.clamp(0.0, 1.0)).f }
	}

	override fun copy(): HSBColor = hsbColor

	override val serialization: JsonElement
		get() = jsonObject {
			"hue" at hue
			"saturation" at saturation
			"brightness" at brightness
			"alpha" at alpha
		}

	override fun deserialize(serializedObject: JsonElement) {
		serializedObject.asJsonObject.apply {
			hue = this["hue"].asFloat
			saturation = this["saturation"].asFloat
			brightness = this["brightness"].asFloat
			alpha = this["alpha"].asFloat
		}
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as HSBColor

		if (hashCode() != other.hashCode()) return false

		return true
	}

	override fun hashCode(): Int = color

	override fun toString(): String {
		return "HSVColor(hue=$hue, saturation=$saturation, brightness=$brightness, alpha=$alpha, hexString='$hexString')"
	}


}