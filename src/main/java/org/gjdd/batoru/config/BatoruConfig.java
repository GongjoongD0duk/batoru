package org.gjdd.batoru.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.entry.RegistryEntry;
import org.gjdd.batoru.input.Action;
import org.gjdd.batoru.job.SkillSlot;
import org.gjdd.batoru.registry.BatoruRegistries;

import java.util.List;
import java.util.Map;

/**
 * 모드의 설정을 담는 레코드입니다.
 *
 * @param skillSlotMappings    동작에 대응하는 스킬 슬롯을 나타내는 맵입니다. 플레이어가 동작을 수행하면, 해당 동작에
 *                             매핑된 스킬 슬롯이 작동합니다. 이때, 기존의 동작은 가능한 경우 취소됩니다. 핫바를 선택하는
 *                             동작을 매핑한 경우, 해당 인벤토리 슬롯은 사용할 수 없게 됩니다.
 * @param usableEquipmentSlots {@link #autoEquipItems} 설정으로 직업의 장비를 자동으로 장착할 때, 지정된 슬롯에
 *                             있는 아이템의 `batoru:usable_job` 컴포넌트를 해당 직업으로 설정하도록 합니다.
 * @param autoEquipItems       직업이 정해질 때 자동으로 장비를 장착할지 여부입니다.
 */
public record BatoruConfig(
        Map<Action, RegistryEntry<SkillSlot>> skillSlotMappings,
        List<EquipmentSlot> usableEquipmentSlots,
        boolean autoEquipItems
) {

    /**
     * 이 레코드의 {@link Codec}입니다.
     */
    public static final Codec<BatoruConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.unboundedMap(
                                    Codec.STRING.xmap(Action::valueOf, Action::name),
                                    BatoruRegistries.SKILL_SLOT.getEntryCodec()
                            ).fieldOf("skillSlotMappings")
                            .forGetter(BatoruConfig::skillSlotMappings),
                    Codec.STRING
                            .xmap(EquipmentSlot::valueOf, EquipmentSlot::name)
                            .listOf()
                            .fieldOf("usableEquipmentSlots")
                            .forGetter(BatoruConfig::usableEquipmentSlots),
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
                Map.of(),
                List.of(
                        EquipmentSlot.MAINHAND,
                        EquipmentSlot.OFFHAND
                ),
                true
        );
    }
}
