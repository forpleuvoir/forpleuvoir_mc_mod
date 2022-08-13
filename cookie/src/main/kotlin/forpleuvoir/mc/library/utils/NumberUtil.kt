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

fun min(x: Double, y: Double, z: Double): Double {
	val min: Double = if (x < y) x else y
	return if (min < z) min else z
}

fun min(x: Float, y: Float, z: Float): Float {
	val min: Float = if (x < y) x else y
	return if (min < z) min else z
}

fun min(x: Int, y: Int, z: Int): Int {
	val min: Int = if (x < y) x else y
	return if (min < z) min else z
}

fun max(x: Double, y: Double, z: Double): Double {
	val max: Double = if (x > y) x else y
	return if (max > z) max else z
}

fun max(x: Float, y: Float, z: Float): Float {
	val max: Float = if (x > y) x else y
	return if (max > z) max else z
}

fun max(x: Int, y: Int, z: Int): Int {
	val max: Int = if (x > y) x else y
	return if (max > z) max else z
}

fun Byte.clamp(minValue: Number, maxValue: Number): Byte {
	return (this as Number).clamp(minValue, maxValue).toByte()
}

fun Short.clamp(minValue: Number, maxValue: Number): Short {
	return (this as Number).clamp(minValue, maxValue).toShort()
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