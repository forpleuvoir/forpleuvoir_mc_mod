package forpleuvoir.mc.library.gui.widget

import com.mojang.blaze3d.vertex.PoseStack
import forpleuvoir.mc.library.gui.foundation.*
import forpleuvoir.mc.library.gui.foundation.layout.ListLayout
import forpleuvoir.mc.library.gui.icon.Down
import forpleuvoir.mc.library.gui.icon.Left
import forpleuvoir.mc.library.gui.text.TextLabel
import forpleuvoir.mc.library.gui.text.textLabel
import forpleuvoir.mc.library.gui.texture.BORDER
import forpleuvoir.mc.library.gui.texture.TEXT_FIELD
import forpleuvoir.mc.library.gui.widget.button.IconButton
import forpleuvoir.mc.library.utils.Direction
import forpleuvoir.mc.library.utils.color.Color4f
import forpleuvoir.mc.library.utils.d
import forpleuvoir.mc.library.utils.text.Text
import forpleuvoir.mc.library.utils.text.literal
import forpleuvoir.mc.library.utils.text.maxWidth
import forpleuvoir.mc.library.utils.textRenderer
import java.util.function.Supplier

/**
 * 下拉菜单

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.gui.widget

 * 文件名 DropMenu

 * 创建时间 2022/7/22 23:15

 * @author forpleuvoir

 */
class DropMenu(
	val items: () -> List<String>,
	currentItem: String = items()[0],
	var expandSize: () -> Int = { 10 },
) : AbstractParentElement() {

	/**
	 * 固定高度
	 */
	private val fixedHeight = 18

	override var width: Double = items().maxWidth() + fixedHeight + 8.0
		set(value) {
			field = value.coerceAtLeast(fixedHeight + 4.0)
			list.width = field - 8
			currentText.width = field
			x = this@DropMenu.x + this@DropMenu.width - width
		}

	override var zOffset: Int = 10
	override var handlePriority: Int = 10

	var currentItem: String = currentItem
		set(value) {
			if (items().contains(value)) {
				val origin = field
				field = value
				onToggle(origin, field)
			}
		}

	var expanded: Boolean = false
		set(value) {
			field = value
			list.visible = field
			list.active = field
			dropButton.icon = if (field) Down else Left
		}

	var itemTip: ((item: String) -> Text)? = null
	var itemTipDirection: ((item: String) -> Direction)? = null

	var onToggle: DropMenu.(origin: String, current: String) -> Unit = { _, _ -> }

	private val currentText: TextLabel = TextLabel(Supplier { literal(this.currentItem) }, width = width, height = fixedHeight - 2.0).apply {
		x = this@DropMenu.x
		y = this@DropMenu.y + 1
		textColor = Color4f.BLACK
		padding.set(Margin(left = 4.0, top = 0.5, right = fixedHeight.d))
		align = Align.CenterLeft
		render = { poseStack: PoseStack, delta: Double ->
			drawTexture(poseStack, this@apply.x, this@apply.y, width, height, TEXT_FIELD)
			onRender(poseStack, delta)
		}
	}

	private val dropButton: IconButton = IconButton(Left).apply {
		handlePriority = 10
		width = fixedHeight - 1.0
		height = fixedHeight.d
		onClick = {
			expanded = !expanded
		}
		x = this@DropMenu.x + this@DropMenu.width - width
		y = this@DropMenu.y
	}

	private val list: ListLayout = ListLayout(width - 8, expandSize().coerceAtMost(items().size) * textRenderer.lineHeight.d).apply {
		x = this@DropMenu.x + 4
		y = this@DropMenu.y + fixedHeight + 4
		visible = false
		active = false
		scrollerBar.amountDelta = { textRenderer.lineHeight.d }
		height = expandSize().coerceAtMost(items().size) * textRenderer.lineHeight.d
		items().forEach { str ->
			textLabel(
				text = Supplier { str },
				width = contentSize,
				onClick = {
					val origin = this@DropMenu.currentItem
					this@DropMenu.currentItem = str
					expanded = false
					onToggle(origin, this@DropMenu.currentItem)
				},
				align = Align.CenterLeft
			) {
				tip = itemTip?.run { { invoke(str) } }
				tipDirection = itemTipDirection?.run { { invoke(str) } }
				textColor = Color4f.BLACK
				render = { poseStack, delta ->
					mouseHover { drawRect(poseStack, x, y - 0.5, this@apply.width - scrollerBar.width, height, Color4f.BLACK.alpha(0.2f)) }
					onRender(poseStack, delta)
				}
			}
		}
		render = { poseStack, delta ->
			drawRect(poseStack, x - 2, y - 2, width + 4, height + 4, Color4f.WHITE)
			onRender(poseStack, delta)
			drawTexture(poseStack, x - 4, y - 4 - 1, width + 8, height + 8 + 2, BORDER)
		}
	}

	init {
		addElement(list)
		addElement(currentText)
		addElement(dropButton)
	}

	override fun init() {
		width += 0
		list.x = this.x + 4
		list.y = this.y + fixedHeight + 4
		currentText.x = this.x
		currentText.y = this.y + 1
		dropButton.x = this.x + this.width - dropButton.width
		dropButton.y = this.y
	}
}