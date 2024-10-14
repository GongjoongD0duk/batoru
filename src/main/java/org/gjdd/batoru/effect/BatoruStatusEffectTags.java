package org.gjdd.batoru.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class BatoruStatusEffectTags {
    public static final TagKey<StatusEffect> PUSHED = of("pushed");
    public static final TagKey<StatusEffect> SILENCED = of("silenced");

    public static void register() {
    }

    private static TagKey<StatusEffect> of(String id) {
        return TagKey.of(RegistryKeys.STATUS_EFFECT, Identifier.of("batoru", id));
    }
}
