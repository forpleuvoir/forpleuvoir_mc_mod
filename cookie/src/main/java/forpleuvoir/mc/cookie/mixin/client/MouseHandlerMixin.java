package forpleuvoir.mc.cookie.mixin.client;

import forpleuvoir.mc.library.gui.foundation.ElementExtensionKt;
import forpleuvoir.mc.library.gui.screen.ScreenManager;
import forpleuvoir.mc.library.input.InputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 项目名 forpleuvoir_mc_mod
 * <p>
 * 包名 forpleuvoir.mc.cookie.mixin.client
 * <p>
 * 文件名 MouseHandlerMixin
 * <p>
 * 创建时间 2022/7/16 11:26
 *
 * @author forpleuvoir
 */
@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

	@Shadow
	@Final
	private Minecraft minecraft;

	@Shadow
	private int activeButton;

	@Shadow
	private double mousePressedTime;

	@Shadow
	private double xpos;

	@Shadow
	private double ypos;

	@Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
	public void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
		if (window == this.minecraft.getWindow().getWindow()) {
			if (action == 1) {
				this.activeButton = button;
				if (InputHandler.keyPress(button)) ci.cancel();
				ScreenManager.hasScreen(screen -> {
					screen.getMouseClick().invoke(ElementExtensionKt.getMouseX(), ElementExtensionKt.getMouseY(), button);
					ci.cancel();
				});
			} else {
				this.activeButton = -1;
				if (InputHandler.keyRelease(button)) ci.cancel();
				ScreenManager.hasScreen(screen -> {
					screen.getMouseRelease().invoke(ElementExtensionKt.getMouseX(), ElementExtensionKt.getMouseY(), button);
					ci.cancel();
				});
			}
		}
	}

	@Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
	public void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
		if (window == this.minecraft.getWindow().getWindow()) {
			ScreenManager.hasScreen(screen -> {
				double amount = (this.minecraft.options.discreteMouseScroll().get() ? Math.signum(vertical) : vertical) * this.minecraft.options.mouseWheelSensitivity().get();
				screen.getMouseScrolling().invoke(ElementExtensionKt.getMouseX(), ElementExtensionKt.getMouseY(), amount);
				ci.cancel();
			});
		}
	}

	@Inject(method = "onMove", at = @At("HEAD"))
	public void onCursorPos(long window, double x, double y, CallbackInfo ci) {
		if (window == this.minecraft.getWindow().getWindow()) {
			ScreenManager.hasScreen(screen -> screen.getMouseMove().invoke(ElementExtensionKt.getMouseX(), ElementExtensionKt.getMouseY()));
			if (this.activeButton != -1 && this.mousePressedTime > 0.0) {
				double deltaX = (x - this.xpos) * (double) this.minecraft.getWindow().getGuiScaledWidth() / (double) this.minecraft.getWindow().getWidth();
				double deltaY = (y - this.ypos) * (double) this.minecraft.getWindow().getGuiScaledHeight() / (double) this.minecraft.getWindow().getHeight();
				ScreenManager.hasScreen(screen ->
						screen.getMouseDragging().invoke(
								ElementExtensionKt.getMouseX(),
								ElementExtensionKt.getMouseY(),
								this.activeButton,
								deltaX,
								deltaY
						)
				);
			}
		}
	}
}
