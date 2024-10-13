package org.gjdd.batoru.skill;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import org.gjdd.batoru.registry.BatoruRegistries;

public final class SkillTest implements ModInitializer {
    private final Skill testSkill = Skill.builder()
            .condition(
                    SkillCondition.builder()
                            .canUse(context -> SkillActionResult.success())
                            .build()
            ).action(
                    SkillAction.builder()
                            .use(context -> {
                                context.source().velocityModified = true;
                                context.source().setVelocity(context.source().getRotationVector());
                                context.source().setSkillCooldown(context.skill(), 50);
                                return SkillActionResult.success();
                            }).build()
            ).build();

    @Override
    public void onInitialize() {
        Registry.register(BatoruRegistries.SKILL, "batoru-test:test", testSkill);
    }
}
