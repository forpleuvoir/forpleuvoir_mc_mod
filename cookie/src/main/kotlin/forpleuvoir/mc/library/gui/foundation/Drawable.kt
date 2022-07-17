package forpleuvoir.mc.library.gui.foundation

import com.mojang.blaze3d.vertex.PoseStack

/**
 * 可渲染

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.foundation

 * 文件名 Drawable

 * 创建时间 2022/7/17 17:26

 * @author forpleuvoir

 */
interface Drawable {

	/**
	 * 可见
	 */
	val visible: Boolean get() = true

	/**
	 * Z轴偏移 越低越先渲染
	 */
	val zOffset: Int get() = 0

	/**
	 * 渲染
	 */
	val render: (poseStack: PoseStack, delta: Double) -> Unit

}
