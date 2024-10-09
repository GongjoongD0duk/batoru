package org.gjdd.batoru.skill.builder;

import org.gjdd.batoru.skill.SkillAction;
import org.gjdd.batoru.skill.SkillActionResult;
import org.gjdd.batoru.skill.SkillContext;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * {@link SkillAction}의 빌더 클래스입니다.
 */
@ApiStatus.Experimental
public final class SkillActionBuilder {
    private Function<SkillContext, SkillActionResult> use = context -> new SkillActionResult.Failure.Unavailable();

    /**
     * {@link SkillAction#use} 메서드를 설정합니다.
     *
     * @param use Function 객체
     * @return 자기 자신 객체
     */
    public SkillActionBuilder use(Function<SkillContext, SkillActionResult> use) {
        this.use = use;
        return this;
    }

    /**
     * {@link SkillAction#use} 메서드를 설정합니다. 이때, 이 메서드는 항상 성공을 반환하게 됩니다.
     *
     * @param use Consumer 객체
     * @return 자기 자신 객체
     */
    public SkillActionBuilder useWithSuccess(Consumer<SkillContext> use) {
        return use(context -> {
            use.accept(context);
            return new SkillActionResult.Success();
        });
    }

    /**
     * 현재 설정으로 {@link SkillAction} 객체를 생성합니다.
     *
     * @return SkillAction 객체
     */
    public SkillAction build() {
        return context -> use.apply(context);
    }
}
