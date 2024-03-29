package forpleuvoir.mc.library.utils.math

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import forpleuvoir.mc.library.utils.jsonObject

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils.math

 * 文件名 Vector3f

 * 创建时间 2022/2/19 18:01

 * @author forpleuvoir

 */
class Vector3f(
	override var x: Float = 0f,
	override var y: Float = 0f,
	override var z: Float = 0f,
) : Vector3<Float> {

	override fun setFromJson(jsonObject: JsonObject) {
		jsonObject.apply {
			x = this["x"].asFloat
			y = this["y"].asFloat
			z = this["z"].asFloat
		}
	}

	override val serialization: JsonElement
		get() = jsonObject {
			"x" at x
			"y" at y
			"y" at z
		}

	override fun unaryMinus(): Vector3f {
		return Vector3f(-x, -y, -z)
	}

	override fun plus(x: Float, y: Float, z: Float): Vector3f {
		return Vector3f(this.x + x, this.y + y, this.z + z)
	}

	override fun plusAssign(x: Float, y: Float, z: Float) {
		this.x += x
		this.y += y
		this.z += z
	}

	override fun minus(x: Float, y: Float, z: Float): Vector3f {
		return Vector3f(this.x - x, this.y - y, this.z - z)
	}

	override fun minusAssign(x: Float, y: Float, z: Float) {
		this.x -= x
		this.y -= y
		this.z -= z
	}

	override fun times(x: Float, y: Float, z: Float): Vector3<Float> {
		return Vector3f(this.x * x, this.y * y, this.z * z)
	}

	override fun timesAssign(x: Float, y: Float, z: Float) {
		this.x *= x
		this.y *= y
		this.z *= z
	}

	override fun div(x: Float, y: Float, z: Float): Vector3<Float> {
		return Vector3f(this.x / x, this.y / y, this.z / z)
	}

	override fun divAssign(x: Float, y: Float, z: Float) {
		this.x /= x
		this.y /= y
		this.z /= z
	}

	override fun rem(x: Float, y: Float, z: Float): Vector3<Float> {
		return Vector3f(this.x % x, this.y % y, this.z % z)
	}

	override fun remAssign(x: Float, y: Float, z: Float) {
		this.x %= x
		this.y %= y
		this.z %= z
	}

}