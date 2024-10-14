package org.gjdd.batoru.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.gjdd.batoru.skill.SkillActionResult;
import org.gjdd.batoru.skill.SkillContext;

public interface SkillCallbacks {

    /**
     * 스킬을 시전할 때, 스킬의 조건을 평가하기 전 호출되는 콜백입니다.
     * 이 콜백이 실패를 반환하면, 스킬은 해당 사유로 실패하게 됩니다.
     */
    Event<PreCondition> PRE_CONDITION = EventFactory.createArrayBacked(
            PreCondition.class,
            callbacks -> context -> {
                for (var callback : callbacks) {
                    var result = callback.preCondition(context);
                    if (result instanceof SkillActionResult.Failure) {
                        return result;
                    }
                }

                return SkillActionResult.success();
            }
    );

    /**
     * 스킬을 시전할 때, 스킬의 동작이 수행되기 전 호출되는 콜백입니다.
     */
    Event<PreAction> PRE_ACTION = EventFactory.createArrayBacked(
            PreAction.class,
            callbacks -> context -> {
                for (var callback : callbacks) {
                    callback.preAction(context);
                }
            }
    );

    interface PreCondition {
        SkillActionResult preCondition(SkillContext context);
    }

    interface PreAction {
        void preAction(SkillContext context);
    }
}
