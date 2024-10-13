package org.gjdd.batoru.skill.builder;

import org.gjdd.batoru.skill.Skill;
import org.gjdd.batoru.skill.SkillAction;
import org.gjdd.batoru.skill.SkillActionResult;
import org.gjdd.batoru.skill.SkillCondition;

/**
 * {@link Skill}의 빌더 클래스입니다.
 */
public final class SkillBuilder {
    private SkillCondition condition = context -> SkillActionResult.success();
    private SkillAction action = context -> SkillActionResult.success();

    /**
     * 스킬의 조건을 설정합니다.
     *
     * @param condition SkillCondition 객체
     * @return 자기 자신 객체
     */
    public SkillBuilder condition(SkillCondition condition) {
        this.condition = condition;
        return this;
    }

    /**
     * 스킬의 동작을 설정합니다.
     *
     * @param action SkillAction 객체
     * @return 자기 자신 객체
     */
    public SkillBuilder action(SkillAction action) {
        this.action = action;
        return this;
    }

    /**
     * 현재 설정으로 {@link Skill} 객체를 생성하여 반환합니다.
     *
     * @return Skill 객체
     */
    public Skill build() {
        return new Skill(condition, action);
    }
}
