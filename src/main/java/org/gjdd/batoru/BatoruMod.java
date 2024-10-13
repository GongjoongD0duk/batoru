package org.gjdd.batoru;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.gjdd.batoru.command.JobCommand;
import org.gjdd.batoru.command.SkillCommand;
import org.gjdd.batoru.compatibility.BatoruPlaceholderApi;
import org.gjdd.batoru.component.BatoruDataComponentTypes;
import org.gjdd.batoru.config.BatoruConfigManager;
import org.gjdd.batoru.effect.BatoruStatusEffectTags;
import org.gjdd.batoru.effect.BatoruStatusEffects;
import org.gjdd.batoru.registry.BatoruRegistries;
import org.gjdd.batoru.registry.BatoruRegistryKeys;

public final class BatoruMod implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            JobCommand.register(dispatcher);
            SkillCommand.register(dispatcher);
        });
        BatoruDataComponentTypes.register();
        BatoruConfigManager.INSTANCE.loadConfig();
        BatoruStatusEffectTags.register();
        BatoruStatusEffects.register();
        BatoruRegistries.register();
        BatoruRegistryKeys.register();

        if (FabricLoader.getInstance().isModLoaded("placeholder-api")) {
            BatoruPlaceholderApi.initialize();
        }
    }
}
