package org.gjdd.batoru.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.gjdd.batoru.registry.BatoruRegistries;
import org.gjdd.batoru.skill.Skill;

import java.util.Collection;
import java.util.List;

public final class SkillCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("skill")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(
                                CommandManager.literal("use")
                                        .then(
                                                CommandManager.argument("skill", IdentifierArgumentType.identifier())
                                                        .suggests((context, builder) -> CommandSource.suggestIdentifiers(BatoruRegistries.SKILL.getIds(), builder))
                                                        .executes(context ->
                                                                executeUse(
                                                                        context.getSource(),
                                                                        BatoruRegistries.SKILL
                                                                                .getEntry(IdentifierArgumentType.getIdentifier(context, "skill"))
                                                                                .orElseThrow(),
                                                                        List.of(context.getSource().getPlayerOrThrow())
                                                                )
                                                        ).then(
                                                                CommandManager.argument("targets", EntityArgumentType.players())
                                                                        .executes(context ->
                                                                                executeUse(
                                                                                        context.getSource(),
                                                                                        BatoruRegistries.SKILL
                                                                                                .getEntry(IdentifierArgumentType.getIdentifier(context, "skill"))
                                                                                                .orElseThrow(),
                                                                                        EntityArgumentType.getPlayers(context, "targets")
                                                                                )
                                                                        )
                                                        )
                                        )
                        )
        );
    }

    private static int executeUse(ServerCommandSource source, RegistryEntry<Skill> skill, Collection<ServerPlayerEntity> targets) {
        for (var target : targets) {
            target.useSkill(skill);
        }

        if (targets.size() == 1) {
            source.sendFeedback(() -> Text.translatable("commands.skill.use.success.single", targets.iterator().next().getDisplayName()), true);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.skill.use.success.multiple", targets.size()), true);
        }

        return targets.size();
    }
}
