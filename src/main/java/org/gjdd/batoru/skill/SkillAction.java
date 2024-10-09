package org.gjdd.batoru.skill;

import org.gjdd.batoru.skill.builder.SkillActionBuilder;
import org.jetbrains.annotations.ApiStatus;

/**
 * 스킬의 동작을 정의하는 인터페이스입니다.
 */
public interface SkillAction {

    /**
     * 이 클래스의 빌더 객체를 생성하여 반환합니다.
     *
     * @return 빌더 객체
     */
    @ApiStatus.Experimental
    static SkillActionBuilder builder() {
        return new SkillActionBuilder();
    }

    /**
     * 스킬을 사용하고 그 결과를 반환합니다.
     *
     * @param context 스킬 컨텍스트
     * @return 스킬 사용 결과
     */
    SkillActionResult use(SkillContext context);
}
