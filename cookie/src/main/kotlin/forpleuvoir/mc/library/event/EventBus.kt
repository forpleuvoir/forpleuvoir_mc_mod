package forpleuvoir.mc.library.event

import forpleuvoir.mc.library.utils.ReflectionUtil
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * 事件总线 默认实现

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event

 * 文件名 EventBus

 * 创建时间 2022/8/6 13:27

 * @author forpleuvoir

 */
class EventBus : IEventBus {

	companion object {
		@JvmStatic
		val INSTANCE = EventBus()

		fun <E : Event> broadcast(event: E) = INSTANCE.broadcast(event)

		inline fun <reified E : Event> subscribe(greedy: Boolean = false, noinline subscriber: (E) -> Unit) {
			subscribe(E::class.java, greedy, subscriber)
		}

		fun <E : Event> subscribe(channel: Class<out E>, greedy: Boolean = false, subscriber: (E) -> Unit) =
			INSTANCE.subscribe(channel, greedy, subscriber)

		private val eventBusContainer: HashMap<String, IEventBus> = HashMap()

		init {
			eventBusContainer["default"] = INSTANCE
		}

		fun registerEventBus(name: String, eventBus: IEventBus) {
			if (eventBusContainer.containsKey(name)) {
				throw Exception("this EventBus was existed")
			}
			eventBusContainer[name] = eventBus
		}

		operator fun get(name: String): IEventBus? = eventBusContainer[name]

	}

	/**
	 * 所有事件订阅者
	 */
	private val subscribers = ConcurrentHashMap<Class<out Event>, ConcurrentLinkedQueue<Pair<(Event) -> Unit, Boolean>>>()

	override fun <E : Event> broadcast(event: E) {
		subscribers[event.javaClass]?.forEach { it.first(event) }
		val superClass = ReflectionUtil.getSuperClass(event::class.java)
		superClass.forEach { eventChannel ->
			subscribers[eventChannel]?.forEach {
				if (it.second) it.first(event)
			}
		}
	}

	inline fun <reified E : Event> subscribe(greedy: Boolean = false, noinline subscriber: (E) -> Unit) {
		subscribe(E::class.java, greedy, subscriber)
	}

	@Suppress("UNCHECKED_CAST")
	override fun <E : Event> subscribe(channel: Class<out E>, greedy: Boolean, subscriber: (E) -> Unit) {
		if (subscribers.containsKey(channel)) {
			subscribers[channel]?.add(subscriber as (Event) -> Unit to greedy)
		} else {
			val list = ConcurrentLinkedQueue<Pair<(Event) -> Unit, Boolean>>()
			list.add(subscriber as (Event) -> Unit to greedy)
			subscribers[channel] = list
		}
	}
}