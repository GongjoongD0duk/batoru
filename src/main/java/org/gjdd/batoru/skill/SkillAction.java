package org.gjdd.batoru.skill;

/**
 * 스킬의 동작을 정의하는 인터페이스입니다.
 */
public interface SkillAction {

    /**
     * 스킬을 사용하고 그 결과를 반환합니다.
     *
     * @param context 스킬 컨텍스트
     * @return 스킬 사용 결과
     */
    SkillActionResult use(SkillContext context);
}
