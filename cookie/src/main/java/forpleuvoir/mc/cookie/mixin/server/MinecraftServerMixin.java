package forpleuvoir.mc.cookie.mixin.server;

import com.mojang.datafixers.DataFixer;
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
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

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
		System.out.println("server initialized");
	}
}
