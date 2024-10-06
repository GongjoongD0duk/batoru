package org.gjdd.batoru.job;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import org.gjdd.batoru.registry.BatoruRegistries;

import java.util.Map;

public final class JobTest implements ModInitializer {
    private final Job testJob = new Job(Map.of(), Map.of());

    @Override
    public void onInitialize() {
        Registry.register(BatoruRegistries.JOB, "batoru-test:test", testJob);
    }
}
