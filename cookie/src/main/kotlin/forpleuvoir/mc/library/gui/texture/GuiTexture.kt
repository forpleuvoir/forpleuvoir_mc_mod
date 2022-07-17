package forpleuvoir.mc.library.gui.texture

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import forpleuvoir.mc.library.utils.getOr
import forpleuvoir.mc.library.utils.i
import net.minecraft.resources.ResourceLocation

/**
 * gui 材质

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.texture

 * 文件名 GuiTexture

 * 创建时间 2022/7/17 18:25

 * @author forpleuvoir

 */
class GuiTexture(
	val texture: ResourceLocation,
	val corner: Corner,
	val u: Int,
	val v: Int,
	val regionWidth: Int,
	val regionHeight: Int,
	val textureWidth: Int,
	val textureHeight: Int
) {

	companion object {

		fun getFromJsonObject(obj: JsonObject?, default: GuiTexture): GuiTexture {
			return try {
				val texture = ResourceLocation(obj!!.getOr("texture", default.texture.toString()))
				val corner = Corner.getFromJson(obj.get("corner"), default.corner)
				val u = obj.getOr("u", default.u).i
				val v = obj.getOr("v", default.v).i
				val regionWidth = obj.getOr("region_width", default.regionWidth).i
				val regionHeight = obj.getOr("region_height", default.regionHeight).i
				val textureWidth = obj.getOr("texture_width", default.textureWidth).i
				val textureHeight = obj.getOr("texture_height", default.textureHeight).i
				GuiTexture(texture, corner, u, v, regionWidth, regionHeight, textureWidth, textureHeight)
			} catch (_: Exception) {
				default
			}

		}
	}
}

class Corner(
	val left: Int,
	val right: Int,
	val top: Int,
	val bottom: Int
) {

	constructor(vertical: Int, horizontal: Int) : this(left = vertical, right = vertical, top = horizontal, bottom = horizontal)

	constructor(corner: Int) : this(corner, corner, corner, corner)

	companion object {

		fun getFromJson(obj: JsonElement, default: Corner): Corner {
			return try {
				if (obj.isJsonObject)
					obj.asJsonObject.let {
						val left: Int
						val right: Int
						if (it.has("vertical")) {
							left = it.get("vertical").asInt
							right = it.get("vertical").asInt
						} else {
							left = it.getOr("left", default.left).i
							right = it.getOr("right", default.right).i
						}
						val top: Int
						val bottom: Int
						if (it.has("horizontal")) {
							top = it.get("horizontal").asInt
							bottom = it.get("horizontal").asInt
						} else {
							top = it.getOr("top", default.top).i
							bottom = it.getOr("bottom", default.bottom).i
						}
						Corner(left, right, top, bottom)
					}
				else if (obj.isJsonPrimitive)
					Corner(obj.asJsonPrimitive.asInt)
				else throw Exception()
			} catch (_: Exception) {
				default
			}

		}

	}
}
