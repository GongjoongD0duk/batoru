package org.gjdd.batoru.job.builder;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.gjdd.batoru.job.Job;
import org.gjdd.batoru.job.SkillSlot;
import org.gjdd.batoru.skill.Skill;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link Job}의 빌더 클래스입니다.
 */
public final class JobBuilder {
    private final Map<SkillSlot, RegistryEntry<Skill>> skillMap = new HashMap<>();
    private final Map<EquipmentSlot, ItemStack> itemStackMap = new HashMap<>();

    /**
     * 주어진 스킬 맵을 설정에 포함합니다.
     *
     * @param skillMap 스킬 맵
     * @return 자기 자신 객체
     */
    public JobBuilder skillMap(Map<SkillSlot, RegistryEntry<Skill>> skillMap) {
        this.skillMap.putAll(skillMap);
        return this;
    }

    /**
     * 주어진 아이템 스택 맵을 설정에 포함합니다.
     *
     * @param itemStackMap 아이템 스택 맵
     * @return 자기 자신 객체
     */
    public JobBuilder itemStackMap(Map<EquipmentSlot, ItemStack> itemStackMap) {
        this.itemStackMap.putAll(itemStackMap);
        return this;
    }

    /**
     * 주어진 스킬 매핑을 설정에 포함합니다.
     *
     * @param slot  스킬 슬롯
     * @param skill 스킬 객체
     * @return 자기 자신 객체
     */
    public JobBuilder equipSkill(SkillSlot slot, RegistryEntry<Skill> skill) {
        skillMap.put(slot, skill);
        return this;
    }

    /**
     * 주어진 아이템 스택 매핑을 설정에 포함합니다.
     *
     * @param slot  장비 슬롯
     * @param stack 아이템 스택
     * @return 자기 자신 객체
     */
    public JobBuilder equipStack(EquipmentSlot slot, ItemStack stack) {
        itemStackMap.put(slot, stack);
        return this;
    }

    /**
     * 현재 설정으로 {@link Job} 객체를 생성하여 반환합니다.
     *
     * @return Job 객체
     */
    public Job build() {
        return new Job(skillMap, itemStackMap);
    }
}
