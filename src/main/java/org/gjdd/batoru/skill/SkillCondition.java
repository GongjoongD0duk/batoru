package org.gjdd.batoru.skill;

/**
 * 스킬의 조건을 정의하는 인터페이스입니다.
 */
public interface SkillCondition {

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
