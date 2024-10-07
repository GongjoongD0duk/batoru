package org.gjdd.batoru.mixin;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import org.gjdd.batoru.effect.Disarmed;
import org.gjdd.batoru.effect.Rooted;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = StatusEffect.class)
public abstract class StatusEffectMixin {
    @Inject(method = "<init>*", at = @At(value = "TAIL"))
    private void batoru$injectInit(CallbackInfo info) {
        var effect = (StatusEffect) (Object) this;
        if (effect instanceof Disarmed) {
            effect.addAttributeModifier(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    Identifier.of("batoru", "effect.disarmed"),
                    -16.0,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
        }

        if (effect instanceof Rooted) {
            effect.addAttributeModifier(
                    EntityAttributes.GENERIC_MOVEMENT_SPEED,
                    Identifier.of("batoru", "effect.rooted"),
                    -16.0,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
            effect.addAttributeModifier(
                    EntityAttributes.GENERIC_JUMP_STRENGTH,
                    Identifier.of("batoru", "effect.rooted"),
                    -16.0,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
        }
    }
}
