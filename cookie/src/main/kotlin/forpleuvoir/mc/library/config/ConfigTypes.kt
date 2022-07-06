package forpleuvoir.mc.library.config

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config

 * 文件名 ConfigTypes

 * 创建时间 2022/7/4 0:25

 * @author forpleuvoir

 */
enum class ConfigTypes : ConfigType {
	BOOLEAN,
	INTEGER,
	DOUBLE,
	COLOR,
	STRING,
	STRING_LIST,
	OPTIONS,
	HOTKEY,
	GROUP,
	PAIRS,
	STRING_MAP,
	MAP,
	BOOLEAN_WITH_KEY_BIND
	;

	override val type: String
		get() = name
}