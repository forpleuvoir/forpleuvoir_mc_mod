package forpleuvoir.mc.library.utils

import com.google.gson.*

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.utils

 * 文件名 JsonUtil

 * 创建时间 2022/7/2 21:43

 * @author forpleuvoir

 */

val gson = GsonBuilder().setPrettyPrinting().create()!!

val String.parseToJsonArray: JsonArray get() = JsonParser.parseString(this).asJsonArray

val String.parseToJsonObject: JsonObject get() = JsonParser.parseString(this).asJsonObject

val JsonElement.string: String get() = this.toString()

fun Any.toJsonObject(): JsonObject {
	return gson.toJsonTree(this).asJsonObject
}

/**
 * 将对象转换成json字符串
 *
 * @param json 需要转换的对象
 * @return json字符串
 */
fun Any.toJsonStr(): String {
	return gson.toJson(this)
}

fun jsonArray(vararg elements: Any): JsonArray {
	return jsonArray(elements.toList())
}

fun jsonArray(elements: Iterable<Any>): JsonArray {
	return JsonArray().apply {
		for (element in elements) {
			when (element) {
				is Boolean     -> add(element)
				is Number      -> add(element)
				is String      -> add(element)
				is Char        -> add(element)
				is JsonElement -> add(element)
				else           -> add(element.toJsonObject())
			}
		}
	}
}

class JsonObjectScope {

	val jsonObject: JsonObject = JsonObject()

	infix fun String.at(value: String) {
		jsonObject.addProperty(this, value)
	}

	infix fun String.at(value: Number) {
		jsonObject.addProperty(this, value)
	}

	infix fun String.at(value: Boolean) {
		jsonObject.addProperty(this, value)
	}

	infix fun String.at(value: Char) {
		jsonObject.addProperty(this, value)
	}

	infix fun String.at(value: JsonElement) {
		jsonObject.add(this, value)
	}
}

fun jsonObject(scope: JsonObjectScope.() -> Unit): JsonObject {
	val jsonObjectScope = JsonObjectScope()
	jsonObjectScope.scope()
	return jsonObjectScope.jsonObject
}

fun jsonObject(map: Map<String, Any>): JsonObject {
	return jsonObject {
		map.forEach { (key, element) ->
			when (element) {
				is Boolean     -> key at element
				is Number      -> key at element
				is String      -> key at element
				is Char        -> key at element
				is JsonElement -> key at element
				else           -> key at element.toJsonObject()
			}
		}
	}
}

inline fun <reified T> JsonObject.getOr(key: String, or: T): T {
	this.has(key).ifc {
		try {
			return gson.fromJson(this.get(key), T::class.java)
		} catch (_: Exception) {
		}
	}
	return or
}

fun JsonObject.getOr(key: String, or: Number): Number {
	this.has(key).ifc {
		try {
			return this.get(key).asNumber
		} catch (_: Exception) {
		}
	}
	return or
}

fun JsonObject.getOr(key: String, or: Boolean): Boolean {
	this.has(key).ifc {
		try {
			return this.get(key).asBoolean
		} catch (_: Exception) {
		}
	}
	return or
}

fun JsonObject.getOr(key: String, or: String): String {
	this.has(key).ifc {
		try {
			return this.get(key).asString
		} catch (_: Exception) {
		}
	}
	return or
}

fun JsonObject.getOr(key: String, or: JsonObject): JsonObject {
	this.has(key).ifc {
		try {
			return this.get(key).asJsonObject
		} catch (_: Exception) {
		}
	}
	return or
}

fun JsonObject.getOr(key: String, or: JsonArray): JsonArray {
	this.has(key).ifc {
		try {
			return this.get(key).asJsonArray
		} catch (_: Exception) {
		}
	}
	return or
}