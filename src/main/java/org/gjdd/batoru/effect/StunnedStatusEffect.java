package org.gjdd.batoru.effect;

import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public final class StunnedStatusEffect extends StatusEffect implements Disarmed, Rooted, Silenced, PolymerStatusEffect {
    StunnedStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
}
