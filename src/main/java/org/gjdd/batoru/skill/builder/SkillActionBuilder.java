package org.gjdd.batoru.skill.builder;

import org.gjdd.batoru.skill.SkillAction;
import org.gjdd.batoru.skill.SkillActionResult;
import org.gjdd.batoru.skill.SkillContext;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * {@link SkillAction}의 빌더 클래스입니다.
 */
public final class SkillActionBuilder {
    private Function<SkillContext, SkillActionResult> use = context -> SkillActionResult.success();

    /**
     * {@link SkillAction#use} 메서드를 주어진 람다로 설정합니다.
     *
     * @param use Function 객체
     * @return 자기 자신 객체
     */
    public SkillActionBuilder use(Function<SkillContext, SkillActionResult> use) {
        this.use = use;
        return this;
    }

    /**
     * {@link SkillAction#use} 메서드를 주어진 람다로 설정합니다. 이때, 해당 메서드는 항상 성공합니다.
     *
     * @param useWithSuccess Consumer 객체
     * @return 자기 자신 객체
     */
    public SkillActionBuilder useWithSuccess(Consumer<SkillContext> useWithSuccess) {
        return use(context -> {
            useWithSuccess.accept(context);
            return SkillActionResult.success();
        });
    }

    /**
     * 현재 설정으로 {@link SkillAction} 객체를 생성합니다.
     *
     * @return Skill 객체
     */
    public SkillAction build() {
        return context -> use.apply(context);
    }
}
