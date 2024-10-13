package org.gjdd.batoru.channeling;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public final class ChannelingTest implements ModInitializer {
    private final Channeling channeling = Channeling.builder()
            .onTick(context -> {
                context.source().sendMessage(Text.literal("channeling time: " + context.time()));
                if (context.time() >= 50) {
                    context.source().stopChanneling();
                }
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
