package forpleuvoir.mc.cookie.mixin.client;

import forpleuvoir.mc.library.event.EventBus;
import forpleuvoir.mc.library.event.events.client.input.MousePressEvent;
import forpleuvoir.mc.library.event.events.client.input.MouseReleaseEvent;
import forpleuvoir.mc.library.gui.foundation.ElementExtensionKt;
import forpleuvoir.mc.library.gui.foundation.HandleStatus;
import forpleuvoir.mc.library.gui.screen.ScreenHandler;
import forpleuvoir.mc.library.input.InputHandler;
import forpleuvoir.mc.library.input.KeyEnvironmentKt;
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
				var mousePressedEvent = new MousePressEvent(button, mods, KeyEnvironmentKt.currentEnv());
				EventBus.getINSTANCE().broadcast(mousePressedEvent);
				mousePressedEvent.isCanceled(ci::cancel);
				if (InputHandler.keyPress(button) == HandleStatus.Interrupt) ci.cancel();
				ScreenHandler.hasScreen(screen -> {
					if (screen.getActive()) {
						screen.getMouseClick().invoke(ElementExtensionKt.getMouseX(), ElementExtensionKt.getMouseY(), button);
						ci.cancel();
					}
				});
			} else {
				this.activeButton = -1;
				var MouseReleaseEvent = new MouseReleaseEvent(button, mods, KeyEnvironmentKt.currentEnv());
				EventBus.getINSTANCE().broadcast(MouseReleaseEvent);
				MouseReleaseEvent.isCanceled(ci::cancel);
				if (InputHandler.keyRelease(button) == HandleStatus.Interrupt) ci.cancel();
				ScreenHandler.hasScreen(screen -> {
					if (screen.getActive()) {
						screen.getMouseRelease().invoke(ElementExtensionKt.getMouseX(), ElementExtensionKt.getMouseY(), button);
						ci.cancel();
					}
				});
			}
		}
	}

	@Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
	public void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
		if (window == this.minecraft.getWindow().getWindow()) {
			ScreenHandler.hasScreen(screen -> {
				if (screen.getActive()) {
					double amount = (this.minecraft.options.discreteMouseScroll().get() ? Math.signum(vertical) : vertical) * this.minecraft.options.mouseWheelSensitivity().get();
					screen.getMouseScrolling().invoke(ElementExtensionKt.getMouseX(), ElementExtensionKt.getMouseY(), amount);
					ci.cancel();
				}
			});
		}
	}

	@Inject(method = "onMove", at = @At("HEAD"))
	public void onCursorPos(long window, double x, double y, CallbackInfo ci) {
		if (window == this.minecraft.getWindow().getWindow()) {
			ScreenHandler.hasScreen(screen -> screen.getMouseMove().invoke(ElementExtensionKt.getMouseX(), ElementExtensionKt.getMouseY()));
			if (this.activeButton != -1 && this.mousePressedTime > 0.0) {
				double deltaX = (x - this.xpos) * (double) this.minecraft.getWindow().getGuiScaledWidth() / (double) this.minecraft.getWindow().getWidth();
				double deltaY = (y - this.ypos) * (double) this.minecraft.getWindow().getGuiScaledHeight() / (double) this.minecraft.getWindow().getHeight();
				ScreenHandler.hasScreen(screen -> {
							if (screen.getActive()) {
								screen.getMouseDragging().invoke(
										ElementExtensionKt.getMouseX(),
										ElementExtensionKt.getMouseY(),
										this.activeButton,
										deltaX,
										deltaY
								);
							}
						}
				);
			}
		}
	}
}
