package org.gjdd.batoru.effect;

import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public final class AirborneStatusEffect extends StatusEffect implements Disarmed, Pushed, Rooted, Silenced, PolymerStatusEffect {
    AirborneStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
}
