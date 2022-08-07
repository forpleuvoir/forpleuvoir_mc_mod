package forpleuvoir.mc.library.event

/**
 * 事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event

 * 文件名 Event

 * 创建时间 2022/8/6 3:00

 * @author forpleuvoir

 */
abstract class Event {

	/**
	 * 是否可以被取消
	 */
	open val cancellable: Boolean get() = false

	/**
	 * 事件是否已被取消
	 */
	open val canceled: Boolean get() = false

	/**
	 * 如果当前事件可以被取消则取消当前事件
	 */
	open fun cancel() {
		if (!cancellable) throw Exception("this event could not be cancel")
	}

}