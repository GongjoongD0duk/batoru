package org.gjdd.batoru.channeling;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public final class ChannelingTest implements ModInitializer {
    private final Channeling channeling = Channeling.builder()
            .maxTime(10)
            .onStart(context ->
                    context.source().sendMessage(Text.literal("onStart, " + context.time()))
            ).onFinish(context ->
                    context.source().sendMessage(Text.literal("onFinish, " + context.time()))
            ).onInterrupt(context ->
                    context.source().sendMessage(Text.literal("onInterrupt, " + context.time()))
            ).onTick(context -> {
                context.source().sendMessage(Text.literal("onTick, " + context.time()));
                if (context.source().isSneaking()) {
                    context.source().interruptChanneling();
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
