package org.gjdd.batoru.skill.builder;

import org.gjdd.batoru.skill.Skill;
import org.gjdd.batoru.skill.SkillAction;
import org.gjdd.batoru.skill.SkillActionResult;
import org.gjdd.batoru.skill.SkillCondition;
import org.jetbrains.annotations.ApiStatus;

/**
 * {@link Skill}의 빌더 클래스입니다.
 */
@ApiStatus.Experimental
public final class SkillBuilder {
    private SkillCondition condition = context -> new SkillActionResult.Success();
    private SkillAction action = context -> new SkillActionResult.Failure.Unavailable();

    /**
     * {@link Skill}의 조건을 설정합니다.
     *
     * @param condition SkillCondition 객체
     * @return 자기 자신 객체
     */
    public SkillBuilder condition(SkillCondition condition) {
        this.condition = condition;
        return this;
    }

    /**
     * {@link Skill}의 동작을 설정합니다.
     *
     * @param action SkillAction 객체
     * @return 자기 자신 객체
     */
    public SkillBuilder action(SkillAction action) {
        this.action = action;
        return this;
    }

    /**
     * 현재 설정으로 {@link Skill} 객체를 생성합니다.
     *
     * @return Skill 객체
     */
    public Skill build() {
        return new Skill(condition, action);
    }
}
