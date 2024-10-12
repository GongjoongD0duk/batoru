package org.gjdd.batoru.compatibility;

import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.PlaceholderHandler;
import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.gjdd.batoru.job.Job;
import org.gjdd.batoru.job.SkillSlot;
import org.gjdd.batoru.skill.Skill;

import java.util.function.BiFunction;

public final class BatoruPlaceHolders {
    public static void register() {
        if (!FabricLoader.getInstance().isModLoaded("placeholder-api")) {
            return;
        }

        register("job", (context, argument) ->
                handleJob(
                        context,
                        (entity, job) -> PlaceholderResult.value(
                                job.value().getName()
                        )
                )
        );
        register("skill", (context, argument) -> {
            if (argument == null) {
                return PlaceholderResult.invalid("Invalid argument!");
            }

            return handleSkill(
                    context,
                    argument,
                    (entity, skill) -> PlaceholderResult.value(
                            skill.value().getName()
                    )
            );
        });
        register("skill_cooldown", (context, argument) -> {
            if (argument == null) {
                return PlaceholderResult.invalid("Invalid argument!");
            }

            return handleSkill(
                    context,
                    argument,
                    (entity, skill) -> PlaceholderResult.value(
                            String.valueOf(
                                    Math.ceil(entity.getSkillCooldown(skill) / 20.0)
                            )
                    )
            );
        });
        register("skill_condition", (context, argument) -> {
            if (argument == null) {
                return PlaceholderResult.invalid("Invalid argument!");
            }

            return handleSkill(
                    context,
                    argument,
                    (entity, skill) -> PlaceholderResult.value(
                            entity.canUseSkill(skill).getClass().getSimpleName()
                    )
            );
        });
    }

    private static void register(String id, PlaceholderHandler handler) {
        Placeholders.register(Identifier.of("batoru", id), handler);
    }

    private static PlaceholderResult handleJob(
            PlaceholderContext context,
            BiFunction<LivingEntity, RegistryEntry<Job>, PlaceholderResult> function
    ) {
        if (!(context.entity() instanceof LivingEntity entity)) {
            return PlaceholderResult.invalid("No entity!");
        }

        var job = entity.getJob();
        if (job == null) {
            return PlaceholderResult.invalid("No job!");
        }

        return function.apply(entity, job);
    }

    private static PlaceholderResult handleSkill(
            PlaceholderContext context,
            String argument,
            BiFunction<LivingEntity, RegistryEntry<Skill>, PlaceholderResult> function
    ) {
        return handleJob(context, (entity, job) -> {
            SkillSlot skillSlot;
            try {
                skillSlot = SkillSlot.valueOf(argument);
            } catch (IllegalArgumentException exception) {
                return PlaceholderResult.invalid("Invalid skill slot!");
            }

            var skill = job.value().getSkillMap().get(skillSlot);
            if (skill == null) {
                return PlaceholderResult.invalid("No skill!");
            }

            return function.apply(entity, skill);
        });
    }
}
