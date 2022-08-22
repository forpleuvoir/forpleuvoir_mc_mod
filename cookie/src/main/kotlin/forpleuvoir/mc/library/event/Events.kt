package forpleuvoir.mc.library.event

import forpleuvoir.mc.cookie.util.logger
import forpleuvoir.mc.library.api.Initializable
import forpleuvoir.mc.library.utils.ReflectionUtil
import forpleuvoir.mc.library.utils.scanModPackage
import kotlin.reflect.full.companionObjectInstance

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.event

 * 文件名 Events

 * 创建时间 2022/8/6 16:40

 * @author forpleuvoir

 */
object Events : Initializable {

	private val log = logger()

	private val events = LinkedHashSet<Class<out Event>>()

	override fun init() {
		log.info("cookie events register...")
		register()
		log.info("cookie event subscribe register...")
		subscribe()
	}

	/**
	 * 注册所有事件
	 */
	@Suppress("UNCHECKED_CAST")
	private fun register() {
		scanModPackage { e ->
			ReflectionUtil.isAssignableFrom(e, Event::class.java)
		}.forEach { (_, clazz) ->
			clazz.forEach { events.add(it as Class<out Event>) }
		}
	}

	/**
	 * 扫描所有订阅了事件的类
	 */
	@Suppress("UNCHECKED_CAST")
	private fun subscribe() {
		scanModPackage { it.isAnnotationPresent(EventSubscriber::class.java) }
			.forEach { (_, set) ->
				set.forEach { e ->
					val eventSubscriber = e.getAnnotation(EventSubscriber::class.java)
					EventBus[eventSubscriber.eventBus]?.let { bus ->
						e.methods.toList()
							.stream()
							.filter { m ->
								m.isAnnotationPresent(Subscriber::class.java)
										&& m.parameterCount == 1
										&& (ReflectionUtil.isAssignableFrom(
									m.parameterTypes[0],
									Event::class.java
								) || m.parameterTypes[0].equals(Event::class.java))
							}
							.forEach { m ->
								val parameter = m.parameters[0]
								val type = parameter.type
								bus.subscribe(type as Class<out Event>, m.getAnnotation(Subscriber::class.java).greedy) { event ->
									//判断是否有伴生对象,如果存在则以伴生对象作为执行对象，没有则判断是否为对象类,没有则判定为JAVA的静态方法
									val obj = e.kotlin.companionObjectInstance ?: e.kotlin.objectInstance
									m.invoke(obj, event)
								}
							}
					}
				}
			}
	}

	fun eventSet(): Set<Class<out Event>> {
		return events
	}

	fun nameSet(): Set<String> {
		return LinkedHashSet<String>().apply {
			events.forEach { add(it.displayName.string) }
		}
	}

	fun byName(name: String): Class<out Event>? {
		return events.find { it.displayName.string == name }
	}


}