package forpleuvoir.mc.cookie.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import forpleuvoir.mc.library.event.EventBus;
import forpleuvoir.mc.library.event.events.client.render.GameRenderEvent;
import forpleuvoir.mc.library.gui.screen.ScreenHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
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
 * 文件名 GameRendererMixin
 * <p>
 * 创建时间 2022/7/18 0:57
 *
 * @author forpleuvoir
 */
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

	@Shadow
	@Final
	private Minecraft minecraft;

	@Shadow
	public abstract void tick();

	@Inject(method = "render", at = @At("RETURN"))
	public void renderScreen(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
		ScreenHandler.hasScreen(screen -> {
			if (screen.getVisible())
				screen.getRender().invoke(new PoseStack(), (double) minecraft.getDeltaFrameTime());
		});
		EventBus.getINSTANCE().broadcast(new GameRenderEvent(new PoseStack(), tickDelta, startTime, tick));
	}

}
