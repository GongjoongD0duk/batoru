package org.gjdd.batoru.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.gjdd.batoru.job.Job;
import org.gjdd.batoru.registry.BatoruRegistryKeys;

import java.util.Collection;
import java.util.List;

public final class JobCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(
                CommandManager.literal("job")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(
                                CommandManager.literal("get")
                                        .executes(context ->
                                                executeGet(
                                                        context.getSource(),
                                                        context.getSource().getPlayerOrThrow()
                                                )
                                        ).then(
                                                CommandManager.argument("target", EntityArgumentType.player())
                                                        .executes(context ->
                                                                executeGet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayer(context, "target")
                                                                )
                                                        )
                                        )
                        ).then(
                                CommandManager.literal("set")
                                        .then(
                                                CommandManager.argument("job", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, BatoruRegistryKeys.JOB))
                                                        .executes(context ->
                                                                executeSet(
                                                                        context.getSource(),
                                                                        RegistryEntryReferenceArgumentType.getRegistryEntry(context, "job", BatoruRegistryKeys.JOB),
                                                                        List.of(context.getSource().getPlayerOrThrow())
                                                                )
                                                        ).then(
                                                                CommandManager.argument("targets", EntityArgumentType.players())
                                                                        .executes(context ->
                                                                                executeSet(
                                                                                        context.getSource(),
                                                                                        RegistryEntryReferenceArgumentType.getRegistryEntry(context, "job", BatoruRegistryKeys.JOB),
                                                                                        EntityArgumentType.getPlayers(context, "targets")
                                                                                )
                                                                        )
                                                        )
                                        )
                        ).then(
                                CommandManager.literal("remove")
                                        .executes(context ->
                                                executeRemove(
                                                        context.getSource(),
                                                        List.of(context.getSource().getPlayerOrThrow())
                                                )
                                        ).then(
                                                CommandManager.argument("targets", EntityArgumentType.player())
                                                        .executes(context ->
                                                                executeRemove(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, "targets")
                                                                )
                                                        )
                                        )
                        )
        );
    }

    private static int executeGet(ServerCommandSource source, ServerPlayerEntity target) {
        var job = target.getJob();
        if (job == null) {
            source.sendFeedback(() -> Text.translatable("commands.job.get.empty", target.getDisplayName()), false);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.job.get.success", target.getDisplayName(), job.value().getName()), false);
        }

        return 1;
    }

    private static int executeSet(ServerCommandSource source, RegistryEntry<Job> job, Collection<ServerPlayerEntity> targets) {
        for (var target : targets) {
            target.setJob(job);
        }

        if (targets.size() == 1) {
            source.sendFeedback(() -> Text.translatable("commands.job.set.success.single", targets.iterator().next().getDisplayName(), job.value().getName()), true);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.job.set.success.multiple", targets.size(), job.value().getName()), true);
        }

        return targets.size();
    }

    private static int executeRemove(ServerCommandSource source, Collection<ServerPlayerEntity> targets) {
        for (var target : targets) {
            target.setJob(null);
        }

        if (targets.size() == 1) {
            source.sendFeedback(() -> Text.translatable("commands.job.remove.success.single", targets.iterator().next().getDisplayName()), true);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.job.remove.success.multiple", targets.size()), true);
        }

        return targets.size();
    }
}
