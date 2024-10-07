package org.gjdd.batoru.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.gjdd.batoru.channeling.Channeling;
import org.gjdd.batoru.channeling.ChannelingContext;
import org.gjdd.batoru.config.BatoruConfigManager;
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
    @Unique
    private @Nullable ChannelingContext batoru$channelingContext;

    @Override
    public boolean isChanneling() {
        return batoru$channelingContext != null;
    }

    @Override
    public boolean startChanneling(Channeling channeling) {
        if (isChanneling()) {
            return false;
        }

        batoru$channelingContext = new ChannelingContext(channeling, (LivingEntity) (Object) this);
        batoru$channelingContext.channeling().onStart(batoru$channelingContext);
        return true;
    }

    @Override
    public boolean stopChanneling() {
        if (!isChanneling()) {
            return false;
        }

        batoru$channelingContext = null;
        return true;
    }

    @Override
    public @Nullable RegistryEntry<Job> getJob() {
        return batoru$job;
    }

    @Override
    public void setJob(@Nullable RegistryEntry<Job> job) {
        if (batoru$job == job) {
            return;
        }

        batoru$job = job;
        if (job == null) {
            batoru$onJobRemoved();
        } else {
            batoru$onJobSet(job);
        }
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

        if (!skill.value().getCondition().ignoreChanneling(context) && isChanneling()) {
            return new SkillActionResult.Failure.InProgress();
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
        if (batoru$channelingContext != null) {
            batoru$channelingContext.channeling().onTick(batoru$channelingContext);
            if (batoru$channelingContext != null) {
                batoru$channelingContext.time(batoru$channelingContext.time() + 1);
            }
        }
    }

    @Unique
    private void batoru$onJobSet(RegistryEntry<Job> job) {
        if (!BatoruConfigManager.INSTANCE.getConfig().autoEquipItems()) {
            return;
        }

        var entity = (LivingEntity) (Object) this;
        job.value()
                .getItemStackMap()
                .forEach((slot, itemStack) -> {
                    entity.dropStack(entity.getEquippedStack(slot));
                    entity.equipStack(slot, itemStack.copy());
                });
    }

    @Unique
    private void batoru$onJobRemoved() {
    }
}
