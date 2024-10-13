package org.gjdd.batoru.skill.builder;

import org.gjdd.batoru.skill.SkillActionResult;
import org.gjdd.batoru.skill.SkillCondition;
import org.gjdd.batoru.skill.SkillContext;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * {@link SkillCondition}의 빌더 클래스입니다.
 */
public final class SkillConditionBuilder {
    private Predicate<SkillContext> ignoreCooldown = context -> false;
    private Predicate<SkillContext> ignoreChanneling = context -> false;
    private Function<SkillContext, SkillActionResult> canUse = context -> SkillActionResult.success();

    /**
     * {@link SkillCondition#ignoreCooldown} 메서드를 주어진 람다로 설정합니다.
     *
     * @param ignoreCooldown Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder ignoreCooldown(Predicate<SkillContext> ignoreCooldown) {
        this.ignoreCooldown = ignoreCooldown;
        return this;
    }

    /**
     * {@link SkillCondition#ignoreCooldown} 메서드가 항상 주어진 boolean 값을 반환하도록 설정합니다.
     *
     * @param ignoreCooldown boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder ignoreCooldown(boolean ignoreCooldown) {
        return ignoreCooldown(context -> ignoreCooldown);
    }

    /**
     * {@link SkillCondition#ignoreChanneling} 메서드를 주어진 람다로 설정합니다.
     *
     * @param ignoreChanneling Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder ignoreChanneling(Predicate<SkillContext> ignoreChanneling) {
        this.ignoreChanneling = ignoreChanneling;
        return this;
    }

    /**
     * {@link SkillCondition#ignoreChanneling} 메서드가 항상 주어진 boolean 값을 반환하도록 설정합니다.
     *
     * @param ignoreChanneling boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder ignoreChanneling(boolean ignoreChanneling) {
        return ignoreChanneling(context -> ignoreChanneling);
    }

    /**
     * {@link SkillCondition#canUse} 메서드를 주어진 람다로 설정합니다.
     *
     * @param canUse Function 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder canUse(Function<SkillContext, SkillActionResult> canUse) {
        this.canUse = canUse;
        return this;
    }

    /**
     * 현재 설정으로 {@link SkillCondition} 객체를 생성합니다.
     *
     * @return Skill 객체
     */
    public SkillCondition build() {
        return new SkillCondition() {
            @Override
            public boolean ignoreCooldown(SkillContext context) {
                return ignoreCooldown.test(context);
            }

            @Override
            public boolean ignoreChanneling(SkillContext context) {
                return ignoreChanneling.test(context);
            }

            @Override
            public SkillActionResult canUse(SkillContext context) {
                return canUse.apply(context);
            }
        };
    }
}
