package forpleuvoir.mc.library.utils.math

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import forpleuvoir.mc.library.api.serialization.JsonSerializer
import forpleuvoir.mc.library.utils.notc
import forpleuvoir.mc.library.utils.toJsonStr

/**
 * 3维向量

 * 项目名 cookie

 * 包名 org.hiirosakura.cookie.util.math

 * 文件名 Vector3

 * 创建时间 2022/2/18 23:48

 * @author forpleuvoir

 */
interface Vector3<T : Number> : JsonSerializer {

	var x: T
	var y: T
	var z: T

	override fun deserialize(serializedObject: JsonElement) {
		if (!serializedObject.isJsonObject) throw Exception("json${this.toJsonStr()} is not JsonObject")
		serializedObject.asJsonObject.apply {
			has("x").notc { throw Exception("json${this.toJsonStr()} is not have x") }
			has("y").notc { throw Exception("json${this.toJsonStr()} is not have y") }
			has("z").notc { throw Exception("json${this.toJsonStr()} is not have z") }
		}
		setFromJson(serializedObject.asJsonObject)
	}

	fun setFromJson(jsonObject: JsonObject)

	fun set(x: T, y: T, z: T) {
		this.x = x
		this.y = y
		this.z = z
	}

	fun set(vector3: Vector3<T>) {
		this.x = vector3.x
		this.y = vector3.y
		this.z = vector3.z
	}

	operator fun unaryMinus(): Vector3<T>

	operator fun plus(vector3: Vector3<T>): Vector3<T> {
		return plus(vector3.x, vector3.y, vector3.z)
	}

	fun plus(x: T, y: T, z: T): Vector3<T>


	operator fun plusAssign(vector3: Vector3<T>) {
		times(vector3.x, vector3.y, vector3.z)
	}

	fun plusAssign(x: T, y: T, z: T)

	operator fun minus(vector3: Vector3<T>): Vector3<T> {
		return minus(vector3.x, vector3.y, vector3.z)
	}

	fun minus(x: T, y: T, z: T): Vector3<T>

	operator fun minusAssign(vector3: Vector3<T>) {
		times(vector3.x, vector3.y, vector3.z)
	}

	fun minusAssign(x: T, y: T, z: T)

	operator fun times(vector3: Vector3<T>): Vector3<T> {
		return times(vector3.x, vector3.y, vector3.z)
	}

	fun times(x: T, y: T, z: T): Vector3<T>

	operator fun timesAssign(vector3: Vector3<T>) {
		times(vector3.x, vector3.y, vector3.z)
	}

	fun timesAssign(x: T, y: T, z: T)

	operator fun div(vector3: Vector3<T>): Vector3<T> {
		return times(vector3.x, vector3.y, vector3.z)
	}

	fun div(x: T, y: T, z: T): Vector3<T>

	operator fun divAssign(vector3: Vector3<T>) {
		times(vector3.x, vector3.y, vector3.z)
	}

	fun divAssign(x: T, y: T, z: T)

	operator fun rem(vector3: Vector3<T>): Vector3<T> {
		return times(vector3.x, vector3.y, vector3.z)
	}

	fun rem(x: T, y: T, z: T): Vector3<T>

	operator fun remAssign(vector3: Vector3<T>) {
		times(vector3.x, vector3.y, vector3.z)
	}

	fun remAssign(x: T, y: T, z: T)

}