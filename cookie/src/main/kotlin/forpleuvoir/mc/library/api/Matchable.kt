package forpleuvoir.mc.library.api

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.api

 * 文件名 Matchable

 * 创建时间 2022/7/4 0:19

 * @author forpleuvoir

 */
interface Matchable {

	/**
	 * 匹配
	 * @param regex 正则表达式
	 * @return 是否匹配成功
	 */
	infix fun matched(regex: Regex): Boolean

}