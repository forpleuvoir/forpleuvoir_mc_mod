package forpleuvoir.mc.cookie.mixin.client;

import com.mojang.blaze3d.platform.Window;
import forpleuvoir.mc.library.event.EventBus;
import forpleuvoir.mc.library.event.events.client.ClientStartedEvent;
import forpleuvoir.mc.library.event.events.client.ClientStartingEvent;
import forpleuvoir.mc.library.event.events.client.ClientTickEndEvent;
import forpleuvoir.mc.library.event.events.client.ClientTickStartEvent;
import forpleuvoir.mc.library.gui.screen.ScreenHandler;
import forpleuvoir.mc.library.input.InputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

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
@SuppressWarnings("ALL")
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

	@Shadow
	@Final
	private Window window;

	@Shadow
	private volatile boolean pause;

	@Inject(method = "<init>", at = @At("RETURN"))
	public void init(GameConfig gameConfig, CallbackInfo ci) {}

	@Inject(method = "run", at = @At("HEAD"))
	public void runStarting(CallbackInfo ci) {
		EventBus.getINSTANCE().broadcast(new ClientStartingEvent((Minecraft) (Object) this));
	}

	@Inject(method = "run", at = @At("RETURN"))
	public void runStarted(CallbackInfo ci) {
		EventBus.getINSTANCE().broadcast(new ClientStartedEvent((Minecraft) (Object) this));
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void tickStart(CallbackInfo ci) {
		EventBus.getINSTANCE().broadcast(new ClientTickStartEvent((Minecraft) (Object) this));
	}

	@Inject(method = "tick", at = @At("RETURN"))
	public void tickEnd(CallbackInfo ci) {
		InputHandler.INSTANCE.tick();
		EventBus.getINSTANCE().broadcast(new ClientTickEndEvent((Minecraft) (Object) this));
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/SoundManager;tick(Z)V", shift = At.Shift.BEFORE))
	public void screenTick(CallbackInfo ci) {
		ScreenHandler.INSTANCE.tick();
	}

	@Inject(method = "resizeDisplay", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setGuiScale(D)V", shift = At.Shift.AFTER))
	public void screenResize(CallbackInfo ci) {
		ScreenHandler.hasScreen(screen -> {
			screen.setHeight(this.window.getGuiScaledHeight());
			screen.setWidth(this.window.getGuiScaledWidth());
			screen.getResize().invoke(this.window.getGuiScaledWidth(), this.window.getGuiScaledHeight());
		});
	}

	@Inject(method = "pauseGame", at = @At(value = "HEAD"), cancellable = true)
	public void openPauseMenu(CallbackInfo ci) {
		if (ScreenHandler.hasScreen()) ci.cancel();
	}

	@Inject(method = "setScreen", at = @At(value = "HEAD"), cancellable = true)
	public void setScreen(CallbackInfo ci) {
		if (ScreenHandler.hasScreen()) ci.cancel();
	}

	@Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setErrorSection(Ljava/lang/String;)V", shift = At.Shift.AFTER))
	public void paused(boolean tick, CallbackInfo ci) {
		pause = ScreenHandler.hasScreen() && Objects.requireNonNull(ScreenHandler.INSTANCE.getCurrent()).getPauseScreen();
	}
}
