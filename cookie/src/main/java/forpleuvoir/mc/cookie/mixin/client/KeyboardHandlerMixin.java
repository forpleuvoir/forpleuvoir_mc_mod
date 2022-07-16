package forpleuvoir.mc.cookie.mixin.client;

import forpleuvoir.mc.library.input.InputHandler;
import net.minecraft.client.KeyboardHandler;
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
 * 文件名 KeyboardHandlerMixin
 * <p>
 * 创建时间 2022/7/16 11:18
 *
 * @author forpleuvoir
 */
@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {

	@Shadow
	@Final
	private Minecraft minecraft;

	@Shadow
	private boolean sendRepeatsToGui;

	@Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
	public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
		if (window == this.minecraft.getWindow().getWindow()) {
			if (action == 1 || action == 2 && this.sendRepeatsToGui) {
				if (InputHandler.keyPress(key)) ci.cancel();
//				ScreenManager.hasScreen(screen -> {
//					screen.keyPress(key, modifiers);
//					ci.cancel();
//				});
			} else {
				if (InputHandler.keyRelease(key)) ci.cancel();
//				ScreenManager.hasScreen(screen -> {
//					ci.cancel();
//					screen.keyRelease(key, modifiers);
//				});
			}
		}
	}


}
