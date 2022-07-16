package forpleuvoir.mc.cookie.mixin.client;

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

	@Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
	public void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
		if (window == this.minecraft.getWindow().getWindow()) {
			if (action == 1) {
				this.activeButton = button;
				if (InputHandler.keyPress(button)) ci.cancel();
//				ScreenManager.hasScreen(screen -> {
//					screen.mouseClick(ElementKt.getMouseX(), ElementKt.getMouseY(), button);
//					ci.cancel();
//				});
			} else {
				this.activeButton = -1;
				if (InputHandler.keyRelease(button)) ci.cancel();
//				ScreenManager.hasScreen(screen -> {
//					screen.mouseRelease(ElementKt.getMouseX(), ElementKt.getMouseY(), button);
//					ci.cancel();
//				});
			}
		}
	}

	@Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
	public void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
		if (window == this.minecraft.getWindow().getWindow()) {
//			ScreenManager.hasScreen(screen -> {
//				double amount = (this.client.options.discreteMouseScroll ? Math.signum(vertical) : vertical) * this.client.options.mouseWheelSensitivity;
//				screen.mouseScrolling(ElementKt.getMouseX(), ElementKt.getMouseY(), amount);
//				ci.cancel();
//			});
		}
	}

	@Inject(method = "onMove", at = @At("HEAD"))
	public void onCursorPos(long window, double x, double y, CallbackInfo ci) {
		if (window == this.minecraft.getWindow().getWindow()) {
//			ScreenManager.hasScreen(screen -> screen.mouseMove(ElementKt.getMouseX(), ElementKt.getMouseY()));
//			if (this.activeButton != -1 && this.glfwTime > 0.0) {
//				double deltaX = (x - this.x) * (double) this.client.getWindow().getScaledWidth() / (double) this.client.getWindow().getWidth();
//				double deltaY = (y - this.y) * (double) this.client.getWindow().getScaledHeight() / (double) this.client.getWindow().getHeight();
//				ScreenManager.hasScreen(screen -> screen.mouseDragging(ElementKt.getMouseX(), ElementKt.getMouseY(), this.activeButton, deltaX, deltaY));
//			}
		}
	}
}
