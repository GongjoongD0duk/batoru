package org.gjdd.batoru.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.gjdd.batoru.job.Job;
import org.jetbrains.annotations.Nullable;

public interface JobCallbacks {

    /**
     * 직업이 정해진 후 호출되는 콜백입니다.
     */
    Event<AfterSet> AFTER_SET = EventFactory.createArrayBacked(
            AfterSet.class,
            callbacks -> (entity, oldJob, newJob) -> {
                for (var callback : callbacks) {
                    callback.afterSet(entity, oldJob, newJob);
                }
            }
    );

    /**
     * 직업이 지워진 후 호출되는 콜백입니다.
     */
    Event<AfterRemove> AFTER_REMOVE = EventFactory.createArrayBacked(
            AfterRemove.class,
            callbacks -> (entity, oldJob) -> {
                for (var callback : callbacks) {
                    callback.afterRemove(entity, oldJob);
                }
            }
    );

    interface AfterSet {
        void afterSet(LivingEntity entity, @Nullable RegistryEntry<Job> oldJob, RegistryEntry<Job> newJob);
    }

    interface AfterRemove {
        void afterRemove(LivingEntity entity, RegistryEntry<Job> oldJob);
    }
}
