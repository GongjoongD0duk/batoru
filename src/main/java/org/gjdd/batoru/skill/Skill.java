package org.gjdd.batoru.skill;

import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.gjdd.batoru.registry.BatoruRegistries;
import org.gjdd.batoru.skill.builder.SkillBuilder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * 스킬을 나타내는 클래스입니다.
 */
public final class Skill {
    private final SkillCondition condition;
    private final SkillAction action;

    private @Nullable String translationKey;
    private @Nullable Text name;

    /**
     * 기본 생성자입니다.
     *
     * @param condition 스킬의 조건
     * @param action    스킬의 동작
     */
    public Skill(SkillCondition condition, SkillAction action) {
        this.condition = condition;
        this.action = action;
    }

    /**
     * 이 클래스의 빌더 객체를 생성하여 반환합니다.
     *
     * @return 빌더 객체
     */
    @ApiStatus.Experimental
    static SkillBuilder builder() {
        return new SkillBuilder();
    }

    /**
     * 스킬의 조건을 반환합니다.
     *
     * @return SkillCondition 객체
     */
    public SkillCondition getCondition() {
        return condition;
    }

    /**
     * 스킬의 동작을 반환합니다.
     *
     * @return SkillAction 객체
     */
    public SkillAction getAction() {
        return action;
    }

    /**
     * 스킬의 번역 키를 반환합니다.
     *
     * @return 번역 키 문자열
     */
    public String getTranslationKey() {
        if (translationKey == null) {
            translationKey = Util.createTranslationKey("skill", BatoruRegistries.SKILL.getId(this));
        }

        return translationKey;
    }

    /**
     * 스킬의 이름을 반환합니다.
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
