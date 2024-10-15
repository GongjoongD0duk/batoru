package org.gjdd.batoru.skill;

/**
 * 스킬의 조건을 정의하는 인터페이스입니다.
 */
public interface SkillCondition {

    /**
     * 일반적인 상황에서 사용되는 스킬 조건을 반환합니다. 이 스킬 조건은 관전자, 사망, 쿨다운,
     * 정신 집중, 침묵 상태를 확인합니다.
     *
     * @return 스킬 조건 객체
     */
    static SkillCondition defaultCondition() {
        return failIfSpectator()
                .and(failIfDead())
                .and(failIfCooldown())
                .and(failIfChanneling())
                .and(failIfSilenced());
    }

    /**
     * 관전자 상태일 때 실패하는 스킬 조건을 반환합니다.
     *
     * @return 스킬 조건 객체
     */
    static SkillCondition failIfSpectator() {
        return context -> context.source().isSpectator() ?
                SkillActionResult.unavailable() :
                SkillActionResult.success();
    }

    /**
     * 사망 상태일 때 실패하는 스킬 조건을 반환합니다.
     *
     * @return 스킬 조건 객체
     */
    static SkillCondition failIfDead() {
        return context -> context.source().isDead() ?
                SkillActionResult.unavailable() :
                SkillActionResult.success();
    }

    /**
     * 쿨다운일 때 실패하는 스킬 조건을 반환합니다.
     *
     * @return 스킬 조건 객체
     */
    static SkillCondition failIfCooldown() {
        return context -> context.source().hasSkillCooldown(context.skill()) ?
                SkillActionResult.cooldown() :
                SkillActionResult.success();
    }

    /**
     * 정신 집중 중일 때 실패하는 스킬 조건을 반환합니다.
     *
     * @return 스킬 조건 객체
     */
    static SkillCondition failIfChanneling() {
        return context -> context.source().isChanneling() ?
                SkillActionResult.channeling() :
                SkillActionResult.success();
    }

    /**
     * 침묵 상태일 때 실패하는 스킬 조건을 반환합니다.
     *
     * @return 스킬 조건 객체
     */
    static SkillCondition failIfSilenced() {
        return context -> context.source().hasSilencedStatusEffect() ?
                SkillActionResult.silenced() :
                SkillActionResult.success();
    }

    /**
     * 스킬을 사용할 수 있는지 여부를 반환합니다.
     *
     * @param context 스킬 컨텍스트
     * @return 스킬 사용 가능 여부
     */
    SkillActionResult canUse(SkillContext context);

    /**
     * 현재 스킬 조건과 주어진 스킬 조건을 모두 만족해야 하는 결합된 스킬 조건을 생성하여 반환합니다.
     * 즉, 두 조건이 모두 성공해야 스킬을 사용할 수 있습니다.
     *
     * @param other 결합할 스킬 조건
     * @return 결합된 {@link SkillCondition} 객체
     */
    default SkillCondition and(SkillCondition other) {
        return context -> canUse(context) instanceof SkillActionResult.Failure failure ?
                failure :
                other.canUse(context);
    }

    /**
     * 현재 스킬 조건과 주어진 스킬 조건 중 하나라도 만족하면 되는 결합된 스킬 조건을 생성하여 반환합니다.
     * 즉, 두 조건 중 하나라도 성공하면 스킬을 사용할 수 있습니다.
     *
     * @param other 결합할 스킬 조건
     * @return 결합된 {@link SkillCondition} 객체
     */
    default SkillCondition or(SkillCondition other) {
        return context -> canUse(context) instanceof SkillActionResult.Success success ?
                success :
                other.canUse(context);
    }
}
