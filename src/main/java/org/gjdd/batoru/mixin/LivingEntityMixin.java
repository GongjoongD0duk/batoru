package org.gjdd.batoru.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.gjdd.batoru.channeling.Channeling;
import org.gjdd.batoru.channeling.ChannelingContext;
import org.gjdd.batoru.config.BatoruConfigManager;
import org.gjdd.batoru.effect.BatoruStatusEffectTags;
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

    @Override
    public boolean isSilenced() {
        return batoru$hasStatusEffect(BatoruStatusEffectTags.SILENCED);
    }

    @Override
    public boolean isChanneling() {
        return batoru$channeling != null;
    }

    @Override
    public boolean startChanneling(Channeling channeling) {
        if (batoru$channeling != null) {
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
            return SkillActionResult.cooldown();
        }

        if (!skill.value().getCondition().ignoreChanneling(context) && isChanneling()) {
            return SkillActionResult.channeling();
        }

        if (!skill.value().getCondition().ignoreSilenced(context) && isSilenced()) {
            return SkillActionResult.silenced();
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
        var entity = (LivingEntity) (Object) this;
        if (entity.getWorld().isClient()) {
            return;
        }

        batoru$skillCooldowns.replaceAll((skill, cooldown) -> cooldown - 1);
        batoru$skillCooldowns.values().removeIf(cooldown -> cooldown <= 0);
        if (batoru$channeling != null) {
            batoru$channeling.onTick(new ChannelingContext(batoru$channeling, entity, batoru$channelingTime++));
        }
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
    private boolean batoru$hasStatusEffect(TagKey<StatusEffect> key) {
        return ((LivingEntity) (Object) this).getActiveStatusEffects()
                .keySet()
                .stream()
                .anyMatch(effect -> effect.isIn(key));
    }
}
