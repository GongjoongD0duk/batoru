package org.gjdd.batoru.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.gjdd.batoru.input.Action;
import org.gjdd.batoru.job.SkillSlot;

import java.util.Map;

/**
 * 모드의 설정을 담는 레코드입니다.
 *
 * @param skillSlotMappings 동작에 대응하는 스킬 슬롯을 나타내는 맵입니다.
 * @param autoEquipItems    직업이 정해질 때 자동으로 장비를 장착할지 여부입니다.
 */
public record BatoruConfig(
        Map<Action, SkillSlot> skillSlotMappings,
        boolean autoEquipItems
) {

    /**
     * 이 레코드의 {@link Codec}입니다.
     */
    public static final Codec<BatoruConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.unboundedMap(
                                    Codec.STRING.xmap(Action::valueOf, Action::name),
                                    Codec.STRING.xmap(SkillSlot::valueOf, SkillSlot::name)
                            ).fieldOf("skillSlotMappings")
                            .forGetter(BatoruConfig::skillSlotMappings),
                    Codec.BOOL
                            .fieldOf("autoEquipItems")
                            .forGetter(BatoruConfig::autoEquipItems)
            ).apply(instance, BatoruConfig::new)
    );

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
                ),
                true
        );
    }
}
