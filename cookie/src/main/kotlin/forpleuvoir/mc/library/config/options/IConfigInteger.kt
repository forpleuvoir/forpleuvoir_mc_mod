package forpleuvoir.mc.library.config.options

import forpleuvoir.mc.library.config.ConfigValue
import forpleuvoir.mc.library.utils.clamp

/**
 * 整数配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 IConfigInteger

 * 创建时间 2022/7/4 20:31

 * @author forpleuvoir

 */
interface IConfigInteger : ConfigValue<Int> {
	/**
	 * 最小值
	 */
	val minValue: Int get() = Int.MIN_VALUE

	/**
	 * 最大值
	 */
	val maxValue: Int get() = Int.MAX_VALUE

	fun fixValue(value: Int): Int {
		return value.clamp(minValue, maxValue)
	}

}