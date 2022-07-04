package forpleuvoir.mc.library.config.options

import forpleuvoir.mc.library.config.ConfigValue
import forpleuvoir.mc.library.utils.clamp

/**
 * 浮点配置

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.options

 * 文件名 IConfigDouble

 * 创建时间 2022/7/4 20:35

 * @author forpleuvoir

 */
interface IConfigDouble : ConfigValue<Double> {
	/**
	 * 最小值
	 */
	val minValue: Double get() = Double.MIN_VALUE

	/**
	 * 最大值
	 */
	val maxValue: Double get() = Double.MAX_VALUE

	fun fixValue(value: Double): Double {
		return value.clamp(minValue, maxValue)
	}
}