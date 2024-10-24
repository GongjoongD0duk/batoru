package org.gjdd.batoru.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.gjdd.batoru.channeling.Channeling;
import org.gjdd.batoru.channeling.ChannelingContext;
import org.gjdd.batoru.component.BatoruDataComponentTypes;
import org.gjdd.batoru.config.BatoruConfigManager;
import org.gjdd.batoru.event.JobCallbacks;
import org.gjdd.batoru.event.SkillCallbacks;
import org.gjdd.batoru.input.Action;
import org.gjdd.batoru.input.ActionUtil;
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
import java.util.function.Predicate;

@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin implements LivingEntityExtensions {
    @Unique
    private final Map<RegistryEntry<Skill>, Integer> batoru$skillCooldowns = new HashMap<>();
    @Unique
    private @Nullable RegistryEntry<Job> batoru$job;
    @Unique
    private @Nullable Channeling batoru$channeling;
    @Unique
    private int batoru$channelingTime = -1;
    @Unique
    private Vec3d batoru$pushedVelocity = Vec3d.ZERO;

    @Override
    public Vec3d getPushedVelocity() {
        return batoru$pushedVelocity;
    }

    @Override
    public void setPushedVelocity(Vec3d pushedVelocity) {
        batoru$pushedVelocity = pushedVelocity;
    }

    @Override
    public boolean hasPushedStatusEffect() {
        return batoru$hasStatusEffect(StatusEffect::isPushed);
    }

    @Override
    public boolean hasSilencedStatusEffect() {
        return batoru$hasStatusEffect(StatusEffect::isSilenced);
    }

    @Override
    public boolean isChanneling() {
        return batoru$channeling != null;
    }

    @Override
    public boolean startChanneling(Channeling channeling) {
        if (batoru$channeling != null || hasSilencedStatusEffect()) {
            return false;
        }

        batoru$channelingTime = 0;
        batoru$channeling = channeling;
        return true;
    }

    @Override
    public boolean stopChanneling() {
        if (batoru$channeling == null) {
            return false;
        }

        batoru$channelingTime = -1;
        batoru$channeling = null;
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

        var oldJob = batoru$job;
        batoru$job = job;
        if (job == null) {
            batoru$onJobRemoved();
            JobCallbacks.AFTER_REMOVE.invoker().afterRemove((LivingEntity) (Object) this, oldJob);
        } else {
            batoru$onJobSet(job);
            JobCallbacks.AFTER_SET.invoker().afterSet((LivingEntity) (Object) this, oldJob, job);
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
        return skill.value().getCondition().canUse(skillContext(skill));
    }

    @Override
    public SkillActionResult useSkill(RegistryEntry<Skill> skill) {
        var context = skillContext(skill);
        var callbackResult = SkillCallbacks.PRE_CONDITION.invoker().preCondition(context);
        if (callbackResult instanceof SkillActionResult.Failure) {
            return callbackResult;
        }

        var conditionResult = canUseSkill(skill);
        if (conditionResult instanceof SkillActionResult.Failure) {
            return conditionResult;
        }

        SkillCallbacks.PRE_ACTION.invoker().preAction(context);
        return skill.value().getAction().use(context);
    }

    @Inject(method = "tick()V", at = @At(value = "TAIL"))
    private void batoru$injectTick(CallbackInfo info) {
        var entity = (LivingEntity) (Object) this;
        if (entity.getWorld().isClient() || entity.isDead() || entity.isSpectator()) {
            return;
        }

        batoru$skillCooldownsTick();
        batoru$channelingTick();
        batoru$pushedStatusEffectTick();
    }

    @Unique
    private void batoru$skillCooldownsTick() {
        if (batoru$skillCooldowns.isEmpty()) {
            return;
        }

        batoru$skillCooldowns.replaceAll((skill, cooldown) -> cooldown - 1);
        batoru$skillCooldowns.values().removeIf(cooldown -> cooldown <= 0);
    }

    @Unique
    private void batoru$channelingTick() {
        if (batoru$channeling == null) {
            return;
        }

        batoru$channeling.onTick(channelingContext(batoru$channeling));
        batoru$channelingTime++;
    }

    @Unique
    private void batoru$pushedStatusEffectTick() {
        if (!hasPushedStatusEffect()) {
            return;
        }

        var entity = (LivingEntity) (Object) this;
        entity.setVelocity(getPushedVelocity());
        entity.velocityDirty = true;
        entity.velocityModified = true;
    }

    @Unique
    private void batoru$onJobSet(RegistryEntry<Job> job) {
        if (!BatoruConfigManager.INSTANCE.getConfig().autoEquipItems()) {
            return;
        }

        var entity = (LivingEntity) (Object) this;
        if (entity instanceof ServerPlayerEntity player) {
            var action = batoru$findUnusedHotbarAction();
            if (action != null) {
                player.getInventory().selectedSlot = action.slot;
                player.networkHandler.sendPacket(new UpdateSelectedSlotS2CPacket(action.slot));
            }
        }

        var usableSlots = BatoruConfigManager.INSTANCE.getConfig().usableEquipmentSlots();
        job.value()
                .getItemStackMap()
                .forEach((slot, itemStack) -> {
                    var copied = itemStack.copy();
                    if (usableSlots.contains(slot)) {
                        copied.set(BatoruDataComponentTypes.USABLE_JOB, job);
                    }

                    entity.dropStack(entity.getEquippedStack(slot));
                    entity.equipStack(slot, copied);
                });
    }

    @Unique
    private void batoru$onJobRemoved() {
    }

    @Unique
    @Nullable
    private Action batoru$findUnusedHotbarAction() {
        var skillMappings = BatoruConfigManager.INSTANCE.getConfig().skillSlotMappings();
        return ActionUtil.getHotbarActions()
                .stream()
                .filter(action -> !skillMappings.containsKey(action))
                .findFirst()
                .orElse(null);
    }

    @Unique
    private boolean batoru$hasStatusEffect(Predicate<StatusEffect> predicate) {
        return ((LivingEntity) (Object) this).getActiveStatusEffects()
                .keySet()
                .stream()
                .map(RegistryEntry::value)
                .anyMatch(predicate);
    }

    @Unique
    private ChannelingContext channelingContext(Channeling channeling) {
        return new ChannelingContext(channeling, (LivingEntity) (Object) this, batoru$channelingTime);
    }

    @Unique
    private SkillContext skillContext(RegistryEntry<Skill> skill) {
        return new SkillContext(skill, (LivingEntity) (Object) this);
    }
}
