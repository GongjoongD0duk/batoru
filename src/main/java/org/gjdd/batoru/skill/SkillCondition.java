package org.gjdd.batoru.skill;

/**
 * 스킬의 조건을 정의하는 인터페이스입니다.
 */
public interface SkillCondition {

    /**
     * 쿨다운을 무시할지 여부를 반환합니다.
     *
     * @param context 스킬 컨텍스트
     * @return 쿨다운 무시 여부
     */
    default boolean ignoreCooldown(SkillContext context) {
        return false;
    }

    /**
     * 스킬을 사용할 수 있는지 여부를 반환합니다.
     *
     * @param context 스킬 컨텍스트
     * @return 스킬 사용 가능 여부
     */
    SkillActionResult canUse(SkillContext context);
}
