package forpleuvoir.mc.library.utils

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils

 * 文件名 NumberUtil

 * 创建时间 2022/7/2 22:29

 * @author forpleuvoir

 */
fun Number.clamp(minValue: Number, maxValue: Number): Number {
	if (this.toDouble() < minValue.toDouble()) {
		return minValue
	}
	return if (this.toDouble() > maxValue.toDouble()) {
		maxValue
	} else this
}

fun Int.clamp(minValue: Number, maxValue: Number): Int {
	return (this as Number).clamp(minValue, maxValue).toInt()
}

fun Long.clamp(minValue: Number, maxValue: Number): Long {
	return (this as Number).clamp(minValue, maxValue).toLong()
}


fun Double.clamp(minValue: Number, maxValue: Number): Double {
	return (this as Number).clamp(minValue, maxValue).toDouble()
}

fun Float.clamp(minValue: Number, maxValue: Number): Float {
	return (this as Number).clamp(minValue, maxValue).toFloat()
}

val Number.d: Double get() = this.toDouble()

val Number.i: Int get() = this.toInt()

val Number.l: Long get() = this.toLong()

val Number.f: Float get() = this.toFloat()