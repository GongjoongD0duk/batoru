package org.gjdd.batoru.skill;

/**
 * 성공 결과를 반환하는 {@link SkillCondition}의 구현 클래스입니다.
 */
public record DefaultSkillCondition() implements SkillCondition {

    /**
     * 성공 결과를 반환합니다.
     *
     * @param context 스킬 컨텍스트
     * @return 성공 결과
     */
    @Override
    public SkillActionResult canUse(SkillContext context) {
        return new SkillActionResult.Success();
    }
}
