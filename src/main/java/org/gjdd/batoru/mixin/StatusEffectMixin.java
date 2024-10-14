package org.gjdd.batoru.mixin;

import net.minecraft.entity.effect.StatusEffect;
import org.gjdd.batoru.internal.StatusEffectExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = StatusEffect.class)
public abstract class StatusEffectMixin implements StatusEffectExtensions {
    @Unique
    private boolean batoru$isPushed = false;
    @Unique
    private boolean batoru$isSilenced = false;

    @Override
    public boolean isPushed() {
        return batoru$isPushed;
    }

    @Override
    public boolean isSilenced() {
        return batoru$isSilenced;
    }

    @Override
    public StatusEffect setPushed(boolean pushed) {
        batoru$isPushed = pushed;
        return (StatusEffect) (Object) this;
    }

    @Override
    public StatusEffect setSilenced(boolean silenced) {
        batoru$isSilenced = silenced;
        return (StatusEffect) (Object) this;
    }
}
