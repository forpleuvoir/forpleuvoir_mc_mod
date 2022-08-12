package forpleuvoir.mc.cookie.mixin.server;

import com.mojang.datafixers.DataFixer;
import forpleuvoir.mc.cookie.Cookie;
import forpleuvoir.mc.library.event.EventBus;
import forpleuvoir.mc.library.event.events.server.ServerStartedEvent;
import forpleuvoir.mc.library.event.events.server.ServerStartingEvent;
import forpleuvoir.mc.library.event.events.server.ServerStoppedEvent;
import forpleuvoir.mc.library.event.events.server.ServerStoppingEvent;
import forpleuvoir.mc.library.utils.ModLogger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.Proxy;

/**
 * 项目名 forpleuvoir_mc_mod
 * <p>
 * 包名 forpleuvoir.mc.cookie.mixin.server
 * <p>
 * 文件名 MinecraftServerMixin
 * <p>
 * 创建时间 2022/8/6 12:26
 *
 * @author forpleuvoir
 */
@SuppressWarnings("ALL")
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

	private static final ModLogger log = new ModLogger(MinecraftServerMixin.class, Cookie.name);

	@Inject(method = "<init>", at = @At("RETURN"))
	public void init(
			Thread thread,
			LevelStorageSource.LevelStorageAccess levelStorageAccess,
			PackRepository packRepository,
			WorldStem worldStem,
			Proxy proxy,
			DataFixer dataFixer,
			Services services,
			ChunkProgressListenerFactory chunkProgressListenerFactory,
			CallbackInfo ci
	) {
		log.info("server initialized");
	}

	@Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;initServer()Z"))
	private void beforeSetupServer(CallbackInfo info) {
		EventBus.getINSTANCE().broadcast(new ServerStartingEvent((MinecraftServer) (Object) this));
	}

	@Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;updateStatusIcon(Lnet/minecraft/network/protocol/status/ServerStatus;)V"))
	private void afterSetupServer(CallbackInfo info) {
		EventBus.getINSTANCE().broadcast(new ServerStartedEvent((MinecraftServer) (Object) this));
	}

	@Inject(method = "stopServer", at = @At("HEAD"))
	private void beforeShutdownServer(CallbackInfo info) {
		EventBus.getINSTANCE().broadcast(new ServerStoppingEvent((MinecraftServer) (Object) this));
	}

	@Inject(method = "stopServer", at = @At("TAIL"))
	private void afterShutdownServer(CallbackInfo info) {EventBus.getINSTANCE().broadcast(new ServerStoppedEvent((MinecraftServer) (Object) this));}

	@Inject(method = "saveEverything", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;saveAllChunks(ZZZ)Z", shift = At.Shift.AFTER))
	private void saveEverything(boolean bl, boolean bl2, boolean bl3, CallbackInfoReturnable<Boolean> cir) {
		EventBus.getINSTANCE().broadcast(new ServerStoppingEvent((MinecraftServer) (Object) this));
	}

}
