package org.gjdd.batoru.job;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import org.gjdd.batoru.registry.BatoruRegistries;

import java.util.Map;

public final class JobTest implements ModInitializer {
    private final Job testJob = new Job(
            Map.of(),
            Map.of(
                    EquipmentSlot.MAINHAND, Items.FISHING_ROD.getDefaultStack(),
                    EquipmentSlot.CHEST, Items.IRON_CHESTPLATE.getDefaultStack()
            )
    );

    @Override
    public void onInitialize() {
        Registry.register(BatoruRegistries.JOB, "batoru-test:test", testJob);
    }
}
