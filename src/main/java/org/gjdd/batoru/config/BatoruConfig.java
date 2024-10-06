package org.gjdd.batoru.config;

import org.gjdd.batoru.input.Action;
import org.gjdd.batoru.job.SkillSlot;

import java.util.Map;

/**
 * 모드의 설정을 담는 레코드입니다.
 *
 * @param skillSlotMappings 동작에 대응하는 스킬 슬롯을 나타내는 맵입니다.
 */
public record BatoruConfig(Map<Action, SkillSlot> skillSlotMappings) {

    /**
     * 기본 생성자입니다.
     */
    public BatoruConfig() {
        this(
                Map.of(
                        Action.HOTBAR_1, SkillSlot.NORMAL_1,
                        Action.HOTBAR_2, SkillSlot.NORMAL_2,
                        Action.HOTBAR_3, SkillSlot.NORMAL_3,
                        Action.HOTBAR_4, SkillSlot.ULTIMATE
                )
        );
    }
}
