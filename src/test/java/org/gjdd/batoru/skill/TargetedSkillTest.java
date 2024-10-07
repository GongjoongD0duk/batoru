package org.gjdd.batoru.skill;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registry;
import org.gjdd.batoru.registry.BatoruRegistries;

public final class TargetedSkillTest implements ModInitializer {
    private final Skill targetedTestSkill = new Skill(
            context -> new SkillActionResult.Success(),
            new TargetedSkillAction<MobEntity>() {
                @Override
                protected Class<MobEntity> getTargetClass() {
                    return MobEntity.class;
                }

                @Override
                protected double getDistance() {
                    return 16;
                }

                @Override
                protected SkillActionResult performUse(SkillContext context, MobEntity target) {
                    target.damage(context.source().getDamageSources().magic(), 5);
                    context.source().setSkillCooldown(context.skill(), 30);
                    return new SkillActionResult.Success();
                }
            }
    );

    @Override
    public void onInitialize() {
        Registry.register(BatoruRegistries.SKILL, "batoru-test:targeted_test", targetedTestSkill);
    }
}
