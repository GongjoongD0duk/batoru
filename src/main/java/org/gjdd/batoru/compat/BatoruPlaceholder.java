package org.gjdd.batoru.compat;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.gjdd.batoru.job.SkillSlot;

public class BatoruPlaceholder {
    public static void register() {
        Placeholders.register(Identifier.of("batoru", "job"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.value("None");

            return PlaceholderResult.value(e.getJob().value().getName());
        });

        Placeholders.register(Identifier.of("batoru", "skill_normal_1"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.invalid("No Job!");

            var skillmap = e.getJob().value().getSkillMap();
            if (skillmap.get(SkillSlot.NORMAL_1) == null) return PlaceholderResult.value("None");

            return PlaceholderResult.value(skillmap.get(SkillSlot.NORMAL_1).value().getName());
        });

        Placeholders.register(Identifier.of("batoru", "skill_normal_2"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.invalid("No Job!");

            var skillmap = e.getJob().value().getSkillMap();
            if (skillmap.get(SkillSlot.NORMAL_2) == null) return PlaceholderResult.value("None");

            return PlaceholderResult.value(skillmap.get(SkillSlot.NORMAL_2).value().getName());
        });

        Placeholders.register(Identifier.of("batoru", "skill_normal_3"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.invalid("No Job!");

            var skillmap = e.getJob().value().getSkillMap();
            if (skillmap.get(SkillSlot.NORMAL_3) == null) return PlaceholderResult.value("None");

            return PlaceholderResult.value(skillmap.get(SkillSlot.NORMAL_3).value().getName());
        });

        Placeholders.register(Identifier.of("batoru", "skill_ultimate"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.invalid("No Job!");

            var skillmap = e.getJob().value().getSkillMap();
            if (skillmap.get(SkillSlot.ULTIMATE) == null) return PlaceholderResult.value("None");

            return PlaceholderResult.value(skillmap.get(SkillSlot.ULTIMATE).value().getName());
        });

        //쿨다운 초 표현
        Placeholders.register(Identifier.of("batoru", "skill_normal_1_cooldown"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.invalid("No Job!");

            var skillmap = e.getJob().value().getSkillMap();
            if (skillmap.get(SkillSlot.NORMAL_1) == null) return PlaceholderResult.value("0");

            return PlaceholderResult.value(String.valueOf(Math.ceil((double) e.getSkillCooldown(skillmap.get(SkillSlot.NORMAL_1))/20)));
        });

        Placeholders.register(Identifier.of("batoru", "skill_normal_2_cooldown"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.invalid("No Job!");

            var skillmap = e.getJob().value().getSkillMap();
            if (skillmap.get(SkillSlot.NORMAL_2) == null) return PlaceholderResult.value("0");

            return PlaceholderResult.value(String.valueOf(Math.ceil((double) e.getSkillCooldown(skillmap.get(SkillSlot.NORMAL_2))/20)));
        });

        Placeholders.register(Identifier.of("batoru", "skill_normal_3_cooldown"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.invalid("No Job!");

            var skillmap = e.getJob().value().getSkillMap();
            if (skillmap.get(SkillSlot.NORMAL_3) == null) return PlaceholderResult.value("0");

            return PlaceholderResult.value(String.valueOf(Math.ceil((double) e.getSkillCooldown(skillmap.get(SkillSlot.NORMAL_3))/20)));
        });

        Placeholders.register(Identifier.of("batoru", "skill_ultimate_cooldown"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.invalid("No Job!");

            var skillmap = e.getJob().value().getSkillMap();
            if (skillmap.get(SkillSlot.ULTIMATE) == null) return PlaceholderResult.value("0");

            return PlaceholderResult.value(String.valueOf(Math.ceil((double) e.getSkillCooldown(skillmap.get(SkillSlot.ULTIMATE))/20)));
        });

        //쿨다운 틱 표현
        Placeholders.register(Identifier.of("batoru", "skill_normal_1_cooldown_tick"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.invalid("No Job!");

            var skillmap = e.getJob().value().getSkillMap();
            if (skillmap.get(SkillSlot.NORMAL_1) == null) return PlaceholderResult.value("0");

            return PlaceholderResult.value(String.valueOf(e.getSkillCooldown(skillmap.get(SkillSlot.NORMAL_1))));
        });

        Placeholders.register(Identifier.of("batoru", "skill_normal_2_cooldown_tick"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.invalid("No Job!");

            var skillmap = e.getJob().value().getSkillMap();
            if (skillmap.get(SkillSlot.NORMAL_2) == null) return PlaceholderResult.value("0");

            return PlaceholderResult.value(String.valueOf(e.getSkillCooldown(skillmap.get(SkillSlot.NORMAL_2))));
        });

        Placeholders.register(Identifier.of("batoru", "skill_normal_3_cooldown_tick"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.invalid("No Job!");

            var skillmap = e.getJob().value().getSkillMap();
            if (skillmap.get(SkillSlot.NORMAL_3) == null) return PlaceholderResult.value("0");

            return PlaceholderResult.value(String.valueOf(e.getSkillCooldown(skillmap.get(SkillSlot.NORMAL_3))));
        });

        Placeholders.register(Identifier.of("batoru", "skill_ultimate_cooldown_tick"), (ctx, arg) -> {
            if (!ctx.hasEntity()) return PlaceholderResult.invalid("No Entity!");
            if (!ctx.entity().isLiving()) return PlaceholderResult.invalid("Not LivingEntity!");

            var e = (LivingEntity) ctx.entity();
            if (e.getJob() == null) return PlaceholderResult.invalid("No Job!");

            var skillmap = e.getJob().value().getSkillMap();
            if (skillmap.get(SkillSlot.ULTIMATE) == null) return PlaceholderResult.value("0");

            return PlaceholderResult.value(String.valueOf(e.getSkillCooldown(skillmap.get(SkillSlot.ULTIMATE))));
        });
    }
}
