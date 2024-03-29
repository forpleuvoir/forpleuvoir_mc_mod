package forpleuvoir.mc.library.api

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.api

 * 文件名 Notifiable

 * 创建时间 2022/7/4 0:18

 * @author forpleuvoir

 */
interface Notifiable<T> {

	/**
	 * 变动时调用
	 */
	fun onChanged()

	/**
	 * 设置回调
	 * @param callback Function1<T, Unit>
	 */
	fun subscribeChange(obj: Any, callback: (T) -> Unit)

}