package forpleuvoir.mc.cookie.mixin.client;

import com.mojang.blaze3d.platform.Window;
import forpleuvoir.mc.library.gui.screen.ScreenManager;
import forpleuvoir.mc.library.input.InputHandler;
import net.minecraft.client.Minecraft;
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
 * 文件名 MinecraftMixin
 * <p>
 * 创建时间 2022/7/18 1:01
 *
 * @author forpleuvoir
 */
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

	@Shadow
	@Final
	private Window window;

	@Shadow
	private volatile boolean pause;

	@Inject(method = "tick", at = @At("RETURN"))
	public void tickEnd(CallbackInfo ci) {
		InputHandler.INSTANCE.tick();
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/SoundManager;tick(Z)V", shift = At.Shift.BEFORE))
	public void screenTick(CallbackInfo ci) {
		ScreenManager.INSTANCE.tick();
	}

	@Inject(method = "resizeDisplay", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setGuiScale(D)V", shift = At.Shift.AFTER))
	public void screenResize(CallbackInfo ci) {
		ScreenManager.hasScreen(screen -> {
			screen.setHeight(this.window.getGuiScaledHeight());
			screen.setWidth(this.window.getGuiScaledWidth());
		});
	}

	@Inject(method = "pauseGame", at = @At(value = "HEAD"), cancellable = true)
	public void openPauseMenu(CallbackInfo ci) {
		if (ScreenManager.hasScreen()) ci.cancel();
	}

	@Inject(method = "setScreen", at = @At(value = "HEAD"), cancellable = true)
	public void setScreen(CallbackInfo ci) {
		if (ScreenManager.hasScreen()) ci.cancel();
	}

	@Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setErrorSection(Ljava/lang/String;)V", shift = At.Shift.AFTER))
	public void paused(boolean tick, CallbackInfo ci) {
		pause = ScreenManager.hasScreen() && ScreenManager.INSTANCE.getCurrent().getPauseScreen();
	}
}
