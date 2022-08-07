package forpleuvoir.mc.library.input

import forpleuvoir.mc.library.gui.foundation.HandleStatus
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Continue
import forpleuvoir.mc.library.gui.foundation.HandleStatus.Interrupt
import net.minecraft.client.renderer.texture.Tickable
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ConcurrentSkipListSet

/**
 * 输入处理器

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.input

 * 文件名 InputHandler

 * 创建时间 2022/7/16 10:44

 * @author forpleuvoir

 */
object InputHandler : Tickable {

	private val keyBinds: ConcurrentLinkedQueue<KeyBind> = ConcurrentLinkedQueue()

	private val keys: ConcurrentSkipListSet<Int> = ConcurrentSkipListSet()

	@JvmStatic
	fun register(vararg keyBind: KeyBind) {
		keyBinds.addAll(keyBind)
	}

	@JvmStatic
	fun unregister(vararg keyBinds: KeyBind) {
		this.keyBinds.removeAll(keyBinds.toSet())

	}

	/**
	 * 按键是否按下
	 * @param key Int
	 * @return Boolean
	 */
	fun hasKey(key: Int): Boolean {
		return keys.contains(key)
	}

	/**
	 * 按键按下
	 * @param keyCode Int
	 * @return Boolean 是否取消之后的操作
	 */
	@JvmStatic
	fun keyPress(keyCode: Int): HandleStatus {
		if (keyCode != KEY_UNKNOWN)
			if (keys.add(keyCode))
				keyBinds.forEach { if (it.update(LinkedHashSet(keys)) == Interrupt) return Interrupt }
		return Continue
	}

	/**
	 * 按键释放
	 * @param keyCode Int
	 * @return Boolean 是否取消之后的操作
	 */
	@JvmStatic
	fun keyRelease(keyCode: Int): HandleStatus {
		if (keyCode != KEY_UNKNOWN)
			if (keys.remove(keyCode))
				keyBinds.forEach { if (it.update(LinkedHashSet(keys)) == Interrupt) return Interrupt }
		return Continue
	}

	override fun tick() {
		if (keyBinds.isNotEmpty()) keyBinds.forEach { it.tick() }
	}

	/**
	 * 释放所有按键
	 */
	fun unPressAll() {
		keys.clear()
		keyBinds.forEach { it.update(LinkedHashSet(keys)) }
	}
}