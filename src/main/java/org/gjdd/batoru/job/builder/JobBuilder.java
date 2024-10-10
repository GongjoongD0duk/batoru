package org.gjdd.batoru.job.builder;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.gjdd.batoru.job.Job;
import org.gjdd.batoru.job.SkillSlot;
import org.gjdd.batoru.skill.Skill;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;

/**
 * {@link Job}의 빌더 클래스입니다.
 */
@ApiStatus.Experimental
public final class JobBuilder {
    private HashMap<SkillSlot, RegistryEntry<Skill>> skillMap = new HashMap<>();
    private HashMap<EquipmentSlot, ItemStack> itemStackMap = new HashMap<>();

    /**
     * {@link Job}의 스킬 맵을 설정합니다.
     *
     * @param skillMap 스킬 맵
     * @return 자기 자신 객체
     */
    public JobBuilder skillMap(HashMap<SkillSlot, RegistryEntry<Skill>> skillMap) {
        this.skillMap = skillMap;
        return this;
    }

    /**
     * {@link Job}의 아이템 스택 맵을 설정합니다.
     *
     * @param itemStackMap 아이템 스택 맵
     * @return 자기 자신 객체
     */
    public JobBuilder itemStackMap(HashMap<EquipmentSlot, ItemStack> itemStackMap) {
        this.itemStackMap = itemStackMap;
        return this;
    }

    /**
     * 현재 설정의 {@link Job} 아이템 스택 맵에 주어진 매핑을 추가합니다.
     *
     * @param equipmentSlot 아이템 슬롯
     * @param itemStack     아이템 스택
     * @return 자기 자신 객체
     */
    public JobBuilder equipStack(EquipmentSlot equipmentSlot, ItemStack itemStack) {
        this.itemStackMap.put(equipmentSlot, itemStack);
        return this;
    }

    /**
     * 현재 설정으로 {@link Job} 객체를 생성합니다.
     *
     * @return Skill 객체
     */
    public Job build() {
        return new Job(skillMap, itemStackMap);
    }
}
