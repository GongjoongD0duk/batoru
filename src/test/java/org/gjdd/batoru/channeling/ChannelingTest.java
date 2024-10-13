package org.gjdd.batoru.channeling;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

public final class ChannelingTest implements ModInitializer {
    private final Channeling channeling = Channeling.builder()
            .onTick(context -> {
                if (context.time() >= 10) {
                    context.source().stopChanneling();
                }

                context.source().velocityModified = true;
                context.source().setVelocity(context.source().getRotationVector());
            }).build();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(
                        CommandManager.literal("channelingtest")
                                .executes(context -> {
                                    context.getSource().getPlayerOrThrow().startChanneling(channeling);
                                    return 1;
                                })
                )
        );
    }
}
