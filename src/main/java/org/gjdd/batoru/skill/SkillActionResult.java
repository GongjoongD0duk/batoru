package org.gjdd.batoru.skill;

import net.minecraft.text.Text;

/**
 * 스킬 사용의 결과를 나타내는 인터페이스입니다.
 */
public sealed interface SkillActionResult {

    /**
     * 성공 결과를 반환합니다.
     *
     * @return 성공 결과
     */
    static SkillActionResult success() {
        return new Success();
    }

    /**
     * 실패 결과를 반환합니다.
     *
     * @param reason 실패한 이유
     * @return 실패 결과
     */
    static SkillActionResult failure(Text reason) {
        return new Failure(reason);
    }

    /**
     * 쿨다운으로 인한 실패 결과를 반환합니다.
     *
     * @return 실패 결과
     */
    static SkillActionResult cooldown() {
        return failure(Text.translatable("skill.cooldown"));
    }

    /**
     * 정신 집중으로 인한 실패 결과를 반환합니다.
     *
     * @return 실패 결과
     */
    static SkillActionResult channeling() {
        return failure(Text.translatable("skill.channeling"));
    }

    /**
     * 스킬 사용에 성공했음을 나타내는 클래스입니다.
     */
    record Success() implements SkillActionResult {
    }

    /**
     * 스킬 사용에 실패했음을 나타내는 클래스입니다.
     *
     * @param reason 실패한 이유
     */
    record Failure(Text reason) implements SkillActionResult {
    }
}
