package org.gjdd.batoru.job;

/**
 * 스킬 슬롯을 나타내는 열거형입니다.
 */
public enum SkillSlot {

    /**
     * 보통 스킬.
     */
    NORMAL_1, NORMAL_2, NORMAL_3, NORMAL_4,
    NORMAL_5, NORMAL_6, NORMAL_7, NORMAL_8,
    NORMAL_9, NORMAL_10, NORMAL_11, NORMAL_12,
    NORMAL_13, NORMAL_14, NORMAL_15, NORMAL_16,

    /**
     * 궁극기 스킬.
     */
    @Deprecated(forRemoval = true) ULTIMATE
}
