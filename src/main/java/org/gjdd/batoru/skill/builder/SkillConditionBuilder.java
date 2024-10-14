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
    private Predicate<SkillContext> checkCooldown = context -> true;
    private Predicate<SkillContext> checkChanneling = context -> true;
    private Predicate<SkillContext> checkSilenced = context -> true;
    private Function<SkillContext, SkillActionResult> canUse = context -> SkillActionResult.success();

    /**
     * 지정된 조건을 만족할 경우, 쿨다운 상태일 때 실패하도록 설정합니다.
     *
     * @param checkCooldown Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder checkCooldown(Predicate<SkillContext> checkCooldown) {
        this.checkCooldown = checkCooldown;
        return this;
    }

    /**
     * 쿨다운 상태일 때 실패할지 여부를 설정합니다.
     *
     * @param checkCooldown boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder checkCooldown(boolean checkCooldown) {
        return checkCooldown(context -> checkCooldown);
    }

    /**
     * 지정된 조건을 만족할 경우, 정신 집중 중일 때 실패하도록 설정합니다.
     *
     * @param checkChanneling Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder checkChanneling(Predicate<SkillContext> checkChanneling) {
        this.checkChanneling = checkChanneling;
        return this;
    }

    /**
     * 정신 집중 중일 때 실패할지 여부를 설정합니다.
     *
     * @param checkChanneling boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder checkChanneling(boolean checkChanneling) {
        return checkChanneling(context -> checkChanneling);
    }

    /**
     * 지정된 조건을 만족할 경우, 침묵 상태일 때 실패하도록 설정합니다.
     *
     * @param checkSilenced Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder checkSilenced(Predicate<SkillContext> checkSilenced) {
        this.checkSilenced = checkSilenced;
        return this;
    }

    /**
     * 침묵 상태일 때 실패할지 여부를 설정합니다.
     *
     * @param checkSilenced boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder checkSilenced(boolean checkSilenced) {
        return checkSilenced(context -> checkSilenced);
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
            if (checkCooldown.test(context) && context.source().hasSkillCooldown(context.skill())) {
                return SkillActionResult.cooldown();
            }

            if (checkChanneling.test(context) && context.source().isChanneling()) {
                return SkillActionResult.channeling();
            }

            if (checkSilenced.test(context) && context.source().hasSilencedStatusEffect()) {
                return SkillActionResult.silenced();
            }

            return canUse.apply(context);
        };
    }
}
