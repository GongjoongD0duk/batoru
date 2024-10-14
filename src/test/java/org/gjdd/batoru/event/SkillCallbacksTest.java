package org.gjdd.batoru.event;

import net.fabricmc.api.ModInitializer;
import net.minecraft.text.Text;
import org.gjdd.batoru.skill.SkillActionResult;

public final class SkillCallbacksTest implements ModInitializer {
    @Override
    public void onInitialize() {
        SkillCallbacks.PRE_CONDITION.register(context -> {
            if (context.source().isSneaking()) {
                context.source().sendMessage(Text.literal("pre condition called (prevent)"));
                return SkillActionResult.failure(Text.literal("Don't sneak!"));
            }

            context.source().sendMessage(Text.literal("pre condition called (pass)"));
            return SkillActionResult.success();
        });
        SkillCallbacks.PRE_ACTION.register(context ->
                context.source().sendMessage(Text.literal("pre action called"))
        );
    }
}
