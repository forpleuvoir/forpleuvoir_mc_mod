package forpleuvoir.mc.library.event

/**
 * 可取消的事件

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event

 * 文件名 CancellableEvent

 * 创建时间 2022/8/6 3:06

 * @author forpleuvoir

 */
abstract class CancellableEvent : Event() {

	override val cancellable: Boolean = true

	override var canceled: Boolean = false

	override fun cancel() {
		if (cancellable) canceled = true
		else throw Exception("this event could not be cancel")
	}

	fun isCanceled(action: Runnable) = if (canceled) action.run() else Unit

}