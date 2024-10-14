package org.gjdd.batoru.event;

import net.fabricmc.api.ModInitializer;
import net.minecraft.text.Text;

public final class JobCallbacksTest implements ModInitializer {
    @Override
    public void onInitialize() {
        JobCallbacks.AFTER_SET.register((entity, oldJob, newJob) ->
                entity.sendMessage(
                        Text.empty()
                                .append("job changed: ")
                                .append(
                                        oldJob == null ?
                                                Text.literal("null") :
                                                oldJob.value().getName()
                                ).append(" => ")
                                .append(newJob.value().getName())
                )
        );
        JobCallbacks.AFTER_REMOVE.register((entity, oldJob) ->
                entity.sendMessage(
                        Text.empty()
                                .append("job removed: ")
                                .append(oldJob.value().getName())
                )
        );
    }
}
