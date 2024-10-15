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
    private Predicate<SkillContext> failIfDead = context -> true;
    private Predicate<SkillContext> failIfSpectator = context -> true;
    private Predicate<SkillContext> failIfCooldown = context -> true;
    private Predicate<SkillContext> failIfChanneling = context -> true;
    private Predicate<SkillContext> failIfSilenced = context -> true;
    private Function<SkillContext, SkillActionResult> canUse = context -> SkillActionResult.success();

    /**
     * 지정된 조건을 만족할 경우, 사망 상태일 때 실패하도록 설정합니다.
     *
     * @param failIfDead Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder failIfDead(Predicate<SkillContext> failIfDead) {
        this.failIfDead = failIfDead;
        return this;
    }

    /**
     * 사망 상태일 때 실패할지 여부를 설정합니다.
     *
     * @param failIfDead boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder failIfDead(boolean failIfDead) {
        return failIfDead(context -> failIfDead);
    }

    /**
     * 지정된 조건을 만족할 경우, 관전 상태일 때 실패하도록 설정합니다.
     *
     * @param failIfSpectator Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder failIfSpectator(Predicate<SkillContext> failIfSpectator) {
        this.failIfSpectator = failIfSpectator;
        return this;
    }

    /**
     * 관전 상태일 때 실패할지 여부를 설정합니다.
     *
     * @param failIfSpectator boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder failIfSpectator(boolean failIfSpectator) {
        return failIfSpectator(context -> failIfSpectator);
    }

    /**
     * 지정된 조건을 만족할 경우, 쿨다운 상태일 때 실패하도록 설정합니다.
     *
     * @param failIfCooldown Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder failIfCooldown(Predicate<SkillContext> failIfCooldown) {
        this.failIfCooldown = failIfCooldown;
        return this;
    }

    /**
     * 쿨다운 상태일 때 실패할지 여부를 설정합니다.
     *
     * @param failIfCooldown boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder failIfCooldown(boolean failIfCooldown) {
        return failIfCooldown(context -> failIfCooldown);
    }

    /**
     * 지정된 조건을 만족할 경우, 정신 집중 중일 때 실패하도록 설정합니다.
     *
     * @param failIfChanneling Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder failIfChanneling(Predicate<SkillContext> failIfChanneling) {
        this.failIfChanneling = failIfChanneling;
        return this;
    }

    /**
     * 정신 집중 중일 때 실패할지 여부를 설정합니다.
     *
     * @param failIfChanneling boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder failIfChanneling(boolean failIfChanneling) {
        return failIfChanneling(context -> failIfChanneling);
    }

    /**
     * 지정된 조건을 만족할 경우, 침묵 상태일 때 실패하도록 설정합니다.
     *
     * @param failIfSilenced Predicate 객체
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder failIfSilenced(Predicate<SkillContext> failIfSilenced) {
        this.failIfSilenced = failIfSilenced;
        return this;
    }

    /**
     * 침묵 상태일 때 실패할지 여부를 설정합니다.
     *
     * @param failIfSilenced boolean 값
     * @return 자기 자신 객체
     */
    public SkillConditionBuilder failIfSilenced(boolean failIfSilenced) {
        return failIfSilenced(context -> failIfSilenced);
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
            if (failIfDead.test(context) && context.source().isDead()) {
                return SkillActionResult.unavailable();
            }

            if (failIfSpectator.test(context) && context.source().isSpectator()) {
                return SkillActionResult.unavailable();
            }

            if (failIfCooldown.test(context) && context.source().hasSkillCooldown(context.skill())) {
                return SkillActionResult.cooldown();
            }

            if (failIfChanneling.test(context) && context.source().isChanneling()) {
                return SkillActionResult.channeling();
            }

            if (failIfSilenced.test(context) && context.source().hasSilencedStatusEffect()) {
                return SkillActionResult.silenced();
            }

            return canUse.apply(context);
        };
    }
}
