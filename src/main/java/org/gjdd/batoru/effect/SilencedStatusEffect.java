package org.gjdd.batoru.effect;

import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public final class SilencedStatusEffect extends StatusEffect implements Silenced, PolymerStatusEffect {
    SilencedStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
}
