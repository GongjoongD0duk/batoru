package org.gjdd.batoru.skill.builder;

import org.gjdd.batoru.skill.SkillActionResult;
import org.gjdd.batoru.skill.SkillCondition;
import org.gjdd.batoru.skill.SkillContext;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * {@link SkillCondition}의 빌더 클래스입니다. 이 빌더는 기본적으로 쿨다운, 정신 집중,
 * 침묵 여부를 확인하도록 설정되어 있습니다.
 */
public final class SkillConditionBuilder {
    private Predicate<SkillContext> ignoreCooldown = context -> false;
    private Predicate<SkillContext> ignoreChanneling = context -> false;
    private Predicate<SkillContext> ignoreSilenced = context -> false;
    private Function<SkillContext, SkillActionResult> canUse = context -> SkillActionResult.success();

    /**
     * 주어진 컨텍스트에서 쿨다운을 무시할지 설정합니다.
     *
     * @param ignoreCooldown Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder ignoreCooldown(Predicate<SkillContext> ignoreCooldown) {
        this.ignoreCooldown = ignoreCooldown;
        return this;
    }

    /**
     * 주어진 컨텍스트에서 쿨다운을 무시할지 설정합니다.
     *
     * @param ignoreCooldown boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder ignoreCooldown(boolean ignoreCooldown) {
        return ignoreCooldown(context -> ignoreCooldown);
    }

    /**
     * 주어진 컨텍스트에서 정신 집중을 무시할지 설정합니다.
     *
     * @param ignoreChanneling Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder ignoreChanneling(Predicate<SkillContext> ignoreChanneling) {
        this.ignoreChanneling = ignoreChanneling;
        return this;
    }

    /**
     * 주어진 컨텍스트에서 정신 집중을 무시할지 설정합니다.
     *
     * @param ignoreChanneling boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder ignoreChanneling(boolean ignoreChanneling) {
        return ignoreChanneling(context -> ignoreChanneling);
    }

    /**
     * 주어진 컨텍스트에서 침묵 상태를 무시할지 설정합니다.
     *
     * @param ignoreSilenced Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder ignoreSilenced(Predicate<SkillContext> ignoreSilenced) {
        this.ignoreSilenced = ignoreSilenced;
        return this;
    }

    /**
     * 주어진 컨텍스트에서 침묵 상태를 무시할지 설정합니다.
     *
     * @param ignoreSilenced boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder ignoreSilenced(boolean ignoreSilenced) {
        return ignoreSilenced(context -> ignoreSilenced);
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
        return context -> {
            if (!ignoreCooldown.test(context) && context.source().hasSkillCooldown(context.skill())) {
                return SkillActionResult.cooldown();
            }

            if (!ignoreChanneling.test(context) && context.source().isChanneling()) {
                return SkillActionResult.cooldown();
            }

            if (!ignoreSilenced.test(context) && context.source().hasSilencedStatusEffect()) {
                return SkillActionResult.cooldown();
            }

            return canUse.apply(context);
        };
    }
}
