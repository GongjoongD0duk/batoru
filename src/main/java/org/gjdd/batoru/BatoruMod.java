package org.gjdd.batoru;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.gjdd.batoru.command.JobCommand;
import org.gjdd.batoru.command.SkillCommand;
import org.gjdd.batoru.compatibility.BatoruPlaceHolders;
import org.gjdd.batoru.component.BatoruDataComponentTypes;
import org.gjdd.batoru.config.BatoruConfigManager;
import org.gjdd.batoru.registry.BatoruRegistries;
import org.gjdd.batoru.registry.BatoruRegistryKeys;

public final class BatoruMod implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            JobCommand.register(dispatcher);
            SkillCommand.register(dispatcher);
        });
        BatoruPlaceHolders.register();
        BatoruDataComponentTypes.register();
        BatoruConfigManager.INSTANCE.loadConfig();
        BatoruRegistries.register();
        BatoruRegistryKeys.register();
    }
}
