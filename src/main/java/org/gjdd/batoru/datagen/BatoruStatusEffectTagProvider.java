package org.gjdd.batoru.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import org.gjdd.batoru.effect.BatoruStatusEffectTags;
import org.gjdd.batoru.effect.BatoruStatusEffects;

import java.util.concurrent.CompletableFuture;

final class BatoruStatusEffectTagProvider extends FabricTagProvider<StatusEffect> {
    BatoruStatusEffectTagProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture
    ) {
        super(output, RegistryKeys.STATUS_EFFECT, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BatoruStatusEffectTags.PUSHED)
                .add(BatoruStatusEffects.PUSHED.value());
        getOrCreateTagBuilder(BatoruStatusEffectTags.SILENCED)
                .add(BatoruStatusEffects.SILENCED.value());
    }
}
