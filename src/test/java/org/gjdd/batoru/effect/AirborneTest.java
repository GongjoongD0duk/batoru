package org.gjdd.batoru.effect;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.math.Vec3d;

public final class AirborneTest implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(
                        CommandManager.literal("airbornetest")
                                .executes(context -> {
                                    var player = context.getSource().getPlayerOrThrow();
                                    player.addStatusEffect(
                                            new StatusEffectInstance(
                                                    BatoruStatusEffects.AIRBORNE,
                                                    20
                                            )
                                    );
                                    player.setPushedVelocity(
                                            new Vec3d(
                                                    Math.random() - 0.5,
                                                    Math.random() - 0.5,
                                                    Math.random() - 0.5
                                            )
                                    );
                                    return 1;
                                })
                )
        );
    }
}
