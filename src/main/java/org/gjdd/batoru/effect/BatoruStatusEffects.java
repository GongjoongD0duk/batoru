package org.gjdd.batoru.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public final class BatoruStatusEffects {
    public static final RegistryEntry<StatusEffect> PUSHED = register(
            "pushed",
            new StatusEffectImpl(StatusEffectCategory.HARMFUL, 0)
                    .setPushed(true)
    );
    public static final RegistryEntry<StatusEffect> SILENCED = register(
            "silenced",
            new StatusEffectImpl(StatusEffectCategory.HARMFUL, 0)
                    .setSilenced(true)
    );

    public static void register() {
    }

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of("batoru", id), effect);
    }
}
