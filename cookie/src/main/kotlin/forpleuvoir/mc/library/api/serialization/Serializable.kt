package forpleuvoir.mc.library.api.serialization

/**
 *
 * 可序列化

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.api.serialization

 * 文件名 Serializable

 * 创建时间 2022/7/2 22:51

 * @author forpleuvoir

 */
interface Serializable<T> {

	val serialize: T

	fun deserialize(serializedObject: T)
}