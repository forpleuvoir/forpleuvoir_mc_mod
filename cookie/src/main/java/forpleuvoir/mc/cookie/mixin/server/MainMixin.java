package forpleuvoir.mc.cookie.mixin.server;

import forpleuvoir.mc.library.event.Events;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 项目名 forpleuvoir_mc_mod
 * <p>
 * 包名 forpleuvoir.mc.cookie.mixin.server
 * <p>
 * 文件名 MainMixin
 * <p>
 * 创建时间 2022/7/18 1:24
 *
 * @author forpleuvoir
 */
@Mixin(Main.class)
public class MainMixin {

	@Inject(method = "main", at = @At(value = "INVOKE", target = "Lnet/minecraft/SharedConstants;tryDetectVersion()V", shift = At.Shift.AFTER))
	private static void main(String[] args, CallbackInfo ci) {
		Events.INSTANCE.init();
	}
}
