package org.gjdd.batoru.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import org.gjdd.batoru.job.Job;
import org.gjdd.batoru.job.SkillSlot;
import org.gjdd.batoru.skill.Skill;

public final class BatoruRegistries {
    public static final Registry<Job> JOB = FabricRegistryBuilder.createSimple(BatoruRegistryKeys.JOB).buildAndRegister();
    public static final Registry<Skill> SKILL = FabricRegistryBuilder.createSimple(BatoruRegistryKeys.SKILL).buildAndRegister();
    public static final Registry<SkillSlot> SKILL_SLOT = FabricRegistryBuilder.createSimple(BatoruRegistryKeys.SKILL_SLOT).buildAndRegister();

    public static void register() {
    }
}
