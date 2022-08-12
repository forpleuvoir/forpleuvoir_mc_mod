package forpleuvoir.mc.cookie.mixin.client;

import forpleuvoir.mc.library.event.Events;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 项目名 forpleuvoir_mc_mod
 * <p>
 * 包名 forpleuvoir.mc.cookie.mixin.client
 * <p>
 * 文件名 MainMixin
 * <p>
 * 创建时间 2022/7/18 1:22
 *
 * @author forpleuvoir
 */
@Mixin(Main.class)
public class MainMixin {

	@Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/SharedConstants;tryDetectVersion()V", shift = At.Shift.AFTER))
	private static void main(String[] strings, boolean bl, CallbackInfo ci) {
		Events.INSTANCE.init();
	}

}
