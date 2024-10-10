package org.gjdd.batoru.job;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.gjdd.batoru.job.builder.JobBuilder;
import org.gjdd.batoru.registry.BatoruRegistries;
import org.gjdd.batoru.skill.Skill;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * 직업을 나타내는 클래스입니다.
 */
public final class Job {
    private final Map<SkillSlot, RegistryEntry<Skill>> skillMap;
    private final Map<EquipmentSlot, ItemStack> itemStackMap;

    private @Nullable String translationKey;
    private @Nullable Text name;

    /**
     * 기본 생성자입니다.
     *
     * @param skillMap     스킬 맵
     * @param itemStackMap 아이템 스택 맵
     */
    public Job(Map<SkillSlot, RegistryEntry<Skill>> skillMap, Map<EquipmentSlot, ItemStack> itemStackMap) {
        this.skillMap = skillMap;
        this.itemStackMap = itemStackMap;
    }

    /**
     * 이 클래스의 빌더 객체를 생성하여 반환합니다.
     *
     * @return 빌더 객체
     */
    @ApiStatus.Experimental
    public static JobBuilder builder() {
        return new JobBuilder();
    }

    /**
     * 직업의 스킬 맵을 반환합니다.
     *
     * @return 스킬 맵
     */
    public Map<SkillSlot, RegistryEntry<Skill>> getSkillMap() {
        return skillMap;
    }

    /**
     * 직업의 아이템 스택 맵을 반환합니다.
     *
     * @return 아이템 스택 맵
     */
    public Map<EquipmentSlot, ItemStack> getItemStackMap() {
        return itemStackMap;
    }

    /**
     * 직업의 번역 키를 반환합니다.
     *
     * @return 번역 키 문자열
     */
    public String getTranslationKey() {
        if (translationKey == null) {
            translationKey = Util.createTranslationKey("job", BatoruRegistries.JOB.getId(this));
        }

        return translationKey;
    }

    /**
     * 직업의 이름을 반환합니다.
     *
     * @return Text 객체
     */
    public Text getName() {
        if (name == null) {
            name = Text.translatable(getTranslationKey());
        }

        return name;
    }
}
