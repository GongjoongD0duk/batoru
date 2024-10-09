package org.gjdd.batoru.skill;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registry;
import org.gjdd.batoru.registry.BatoruRegistries;

public final class TargetedSkillTest implements ModInitializer {
    private final Skill targetedTestSkill = Skill.builder()
            .condition(
                    SkillCondition.builder()
                            .build()
            ).action(
                    TargetedSkillAction.builder(MobEntity.class)
                            .distance(16)
                            .useWithSuccess((context, target) -> {
                                target.damage(context.source().getDamageSources().magic(), 5);
                                context.source().setSkillCooldown(context.skill(), 30);
                            }).build()
            ).build();

    @Override
    public void onInitialize() {
        Registry.register(BatoruRegistries.SKILL, "batoru-test:targeted_test", targetedTestSkill);
    }
}
