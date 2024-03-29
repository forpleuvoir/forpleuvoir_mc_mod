package forpleuvoir.mc.library.config.command

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import forpleuvoir.mc.library.api.impl.ClientSavable
import forpleuvoir.mc.library.api.impl.ServerSavable
import forpleuvoir.mc.library.config.modconfig.ModConfig
import forpleuvoir.mc.library.utils.parseToJsonElement
import forpleuvoir.mc.library.utils.text.Text
import forpleuvoir.mc.library.utils.text.serverText
import forpleuvoir.mc.library.utils.text.translatable
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.commands.arguments.NbtPathArgument
import net.minecraft.commands.arguments.NbtPathArgument.NbtPath
import java.util.concurrent.CompletableFuture

/**
 *

 * 项目名 forpleuvoir_mc_mod

 * 包名 forpleuvoir.mc.library.config.command

 * 文件名 ConfigCommandHandler

 * 创建时间 2022/8/13 16:02

 * @author forpleuvoir

 */
abstract class ConfigCommandHelper<S : SharedSuggestionProvider>(
	protected val name: String,
	protected val modConfig: ModConfig,
	protected val requires: (S) -> Boolean = { true },
) {

	companion object {

		@JvmStatic
		fun <T> client(
			name: String,
			modConfig: T,
			requires: (FabricClientCommandSource) -> Boolean = { true },
		): ConfigCommandHelper<FabricClientCommandSource> where T : ModConfig, T : ClientSavable =
			object : ConfigCommandHelper<FabricClientCommandSource>(name, modConfig, requires) {
				override fun sendFeedback(text: Text, context: CommandContext<FabricClientCommandSource>) {
					context.source.sendFeedback(text)
				}
			}

		@JvmStatic
		fun <T> server(
			name: String,
			modConfig: T,
			requires: (CommandSourceStack) -> Boolean = { true },
		): ConfigCommandHelper<CommandSourceStack> where T : ModConfig, T : ServerSavable =
			object : ConfigCommandHelper<CommandSourceStack>(name, modConfig, requires) {
				override fun sendFeedback(text: Text, context: CommandContext<CommandSourceStack>) {
					context.source.sendSuccess(text, false)
				}
			}

	}

	fun command(): LiteralArgumentBuilder<S> {
		return literal(name)
			.requires { requires(it) }
			.then(literal("load").executes(::configLoad))
			.then(literal("save").executes(::configSave))
			.then(
				literal("set").then(
					argument("config_category", StringArgumentType.string())
						.suggests(::categorySuggests).then(
							argument("config_key", StringArgumentType.string())
								.suggests(::keySuggests).then(
									argument("config_value", NbtPathArgument.nbtPath())
										.executes(::setValue)
								)
						)
				)
			)
			.then(
				literal("get").then(
					argument("config_category", StringArgumentType.string())
						.suggests(::categorySuggests).then(
							argument("config_key", StringArgumentType.string())
								.suggests(::keySuggests)
								.executes(::getValue)
						)
				)
			)
	}

	protected fun literal(name: String): LiteralArgumentBuilder<S> {
		return LiteralArgumentBuilder.literal(name)
	}

	protected fun <T> argument(name: String, type: ArgumentType<T>): RequiredArgumentBuilder<S, T> {
		return RequiredArgumentBuilder.argument(name, type)
	}

	protected abstract fun sendFeedback(text: Text, context: CommandContext<S>)

	protected fun translatable(context: CommandContext<S>, key: String, vararg params: Any): Text {
		if (context.source is CommandSourceStack) {
			return serverText(key, *params)
		}
		return translatable(key, *params)
	}

	protected fun configLoad(context: CommandContext<S>): Int {
		modConfig.loadAsync()
		sendFeedback(translatable(context, "cookie.command.config.load.success"), context)
		return 1
	}

	protected fun configSave(context: CommandContext<S>): Int {
		modConfig.saveAsync()
		sendFeedback(translatable(context, "cookie.command.config.save.success"), context)
		return 1
	}

	@Suppress("unused_parameter")
	protected fun categorySuggests(context: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
		val category = ArrayList<String>().apply {
			modConfig.allCategory.forEach { add(it.name) }
		}
		return SharedSuggestionProvider.suggest(category, builder)
	}

	protected fun keySuggests(context: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
		val category = context.getArgument("config_category", String::class.java)
		val keys = ArrayList<String>()
		modConfig.allCategory
			.find { it.name == category }
			?.allConfigs?.forEach { keys.add(it.key) }
		return SharedSuggestionProvider.suggest(keys, builder)
	}

	protected fun setValue(context: CommandContext<S>): Int {
		val category = context.getArgument("config_category", String::class.java)
		val key = context.getArgument("config_key", String::class.java)
		val value = context.getArgument("config_value", NbtPath::class.java)
		modConfig
			.allCategory.find { it.name == category }
			?.allConfigs?.find { it.key == key }
			?.run {
				val origin = this.toString()
				deserialize(value.toString().parseToJsonElement)
				onChanged()
				sendFeedback(translatable(context, "cookie.command.config.set_value", this.key, origin, toString()), context)
			}
		return 1
	}

	protected fun getValue(context: CommandContext<S>): Int {
		val category = context.getArgument("config_category", String::class.java)
		val key = context.getArgument("config_key", String::class.java)
		modConfig
			.allCategory.find { it.name == category }
			?.allConfigs?.find { it.key == key }
			?.run {
				sendFeedback(translatable(context, "cookie.command.config.get_value", this.key, toString()), context)
			}
		return 1
	}
}