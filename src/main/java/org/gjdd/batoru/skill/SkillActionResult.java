package org.gjdd.batoru.skill;

/**
 * 스킬 사용의 결과를 나타내는 인터페이스입니다.
 */
public sealed interface SkillActionResult {

    /**
     * 스킬 사용에 성공했음을 나타냅니다.
     */
    record Success() implements SkillActionResult {
    }

    /**
     * 특정 이유로 스킬 사용에 실패했음을 나타냅니다.
     */
    sealed interface Failure extends SkillActionResult {

        /**
         * 쿨다운으로 인해 스킬을 사용할 수 없음을 나타냅니다.
         */
        record Cooldown() implements Failure {
        }

        /**
         * 정신 집중으로 인해 스킬을 사용할 수 없음을 나타냅니다.
         */
        record InProgress() implements Failure {
        }

        /**
         * 지금은 스킬을 사용할 수 없음을 나타냅니다.
         */
        record Unavailable() implements Failure {
        }
    }
}
