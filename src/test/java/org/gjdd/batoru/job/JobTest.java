package org.gjdd.batoru.job;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import org.gjdd.batoru.registry.BatoruRegistries;

public final class JobTest implements ModInitializer {
    private final Job testJob = Job.builder()
            .equipStack(EquipmentSlot.MAINHAND, Items.FISHING_ROD.getDefaultStack())
            .equipStack(EquipmentSlot.CHEST, Items.IRON_CHESTPLATE.getDefaultStack())
            .build();

    @Override
    public void onInitialize() {
        Registry.register(BatoruRegistries.JOB, "batoru-test:test", testJob);
    }
}
