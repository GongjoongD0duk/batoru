package org.gjdd.batoru.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.gjdd.batoru.internal.LivingEntityExtensions;
import org.gjdd.batoru.job.Job;
import org.gjdd.batoru.skill.Skill;
import org.gjdd.batoru.skill.SkillActionResult;
import org.gjdd.batoru.skill.SkillContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin implements LivingEntityExtensions {
    @Unique
    private final Map<RegistryEntry<Skill>, Integer> batoru$skillCooldowns = new HashMap<>();
    @Unique
    private @Nullable RegistryEntry<Job> batoru$job;

    @Override
    public @Nullable RegistryEntry<Job> getJob() {
        return batoru$job;
    }

    @Override
    public void setJob(@Nullable RegistryEntry<Job> job) {
        batoru$job = job;
    }

    @Override
    public boolean hasSkillCooldown(RegistryEntry<Skill> skill) {
        return getSkillCooldown(skill) > 0;
    }

    @Override
    public int getSkillCooldown(RegistryEntry<Skill> skill) {
        return batoru$skillCooldowns.getOrDefault(skill, 0);
    }

    @Override
    public void setSkillCooldown(RegistryEntry<Skill> skill, int cooldown) {
        batoru$skillCooldowns.put(skill, cooldown);
    }

    @Override
    public SkillActionResult canUseSkill(RegistryEntry<Skill> skill) {
        var context = new SkillContext(skill, (LivingEntity) (Object) this);
        if (!skill.value().getCondition().ignoreCooldown(context) && hasSkillCooldown(skill)) {
            return new SkillActionResult.Failure.Cooldown();
        }

        return skill.value().getCondition().canUse(context);
    }

    @Override
    public SkillActionResult useSkill(RegistryEntry<Skill> skill) {
        var actionResult = canUseSkill(skill);
        if (actionResult instanceof SkillActionResult.Failure) {
            return actionResult;
        }

        return skill.value().getAction().use(new SkillContext(skill, (LivingEntity) (Object) this));
    }

    @Inject(method = "tick()V", at = @At(value = "TAIL"))
    private void batoru$injectTick(CallbackInfo info) {
        if (((LivingEntity) (Object) this).getWorld().isClient()) {
            return;
        }

        batoru$skillCooldowns.replaceAll((skill, cooldown) -> cooldown - 1);
        batoru$skillCooldowns.values().removeIf(cooldown -> cooldown <= 0);
    }
}
