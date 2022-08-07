package forpleuvoir.mc.library.event

/**
 * 事件总线

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event

 * 文件名 IEventBus

 * 创建时间 2022/8/6 3:11

 * @author forpleuvoir

 */
interface IEventBus {

	fun <E : Event> broadcast(event: E)

	fun <E : Event> subscribe(channel: Class<out E>, greedy: Boolean = false, subscriber: (E) -> Unit)

}