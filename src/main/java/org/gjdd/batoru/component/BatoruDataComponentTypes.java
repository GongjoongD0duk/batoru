package org.gjdd.batoru.component;

import eu.pb4.polymer.core.api.other.PolymerComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import org.gjdd.batoru.job.Job;
import org.gjdd.batoru.registry.BatoruRegistries;

public final class BatoruDataComponentTypes {
    public static final ComponentType<RegistryEntry<Job>> USABLE_JOB = ComponentType.<RegistryEntry<Job>>builder()
            .codec(BatoruRegistries.JOB.getEntryCodec())
            .build();

    public static void register() {
        Registry.register(Registries.DATA_COMPONENT_TYPE, "batoru:usable_job", USABLE_JOB);
        PolymerComponent.registerDataComponent(USABLE_JOB);
    }
}
