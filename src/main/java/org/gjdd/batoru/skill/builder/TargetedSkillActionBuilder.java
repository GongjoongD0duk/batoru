package org.gjdd.batoru.skill.builder;

import net.minecraft.entity.Entity;
import org.gjdd.batoru.skill.SkillActionResult;
import org.gjdd.batoru.skill.SkillContext;
import org.gjdd.batoru.skill.TargetedSkillAction;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.*;

/**
 * {@link TargetedSkillAction}의 빌더 클래스입니다.
 *
 * @param <T> 대상 객체 타입
 */
@ApiStatus.Experimental
public final class TargetedSkillActionBuilder<T extends Entity> {
    private final Class<T> targetClass;

    private Function<SkillContext, Double> distance = context -> 0.0;
    private Function<SkillContext, Double> margin = context -> 0.5;
    private BiPredicate<SkillContext, T> canTarget = (context, entity) -> !entity.isSpectator() && entity.canHit();
    private BiFunction<SkillContext, T, SkillActionResult> use = (context, target) -> new SkillActionResult.Failure.Unavailable();

    /**
     * 기본 생성자입니다.
     *
     * @param targetClass 탐색 대상 클래스
     */
    public TargetedSkillActionBuilder(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    /**
     * {@link TargetedSkillAction}의 거리를 주어진 람다로 설정합니다.
     *
     * @param distance Function 객체
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> distance(Function<SkillContext, Double> distance) {
        this.distance = distance;
        return this;
    }

    /**
     * {@link TargetedSkillAction}의 거리를 주어진 double 값으로 설정합니다.
     *
     * @param distance double 값
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> distance(double distance) {
        return distance(context -> distance);
    }

    /**
     * {@link TargetedSkillAction}의 마진을 주어진 람다로 설정합니다.
     *
     * @param margin Function 객체
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> margin(Function<SkillContext, Double> margin) {
        this.margin = margin;
        return this;
    }

    /**
     * {@link TargetedSkillAction}의 마진을 주어진 double 값으로 설정합니다.
     *
     * @param margin double 값
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> margin(double margin) {
        return margin(context -> margin);
    }

    /**
     * {@link TargetedSkillAction}의 대상 조건을 주어진 람다로 설정합니다.
     *
     * @param canTarget BiPredicate 객체
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> canTarget(BiPredicate<SkillContext, T> canTarget) {
        this.canTarget = canTarget;
        return this;
    }

    /**
     * {@link TargetedSkillAction}의 대상 조건을 주어진 boolean 값으로 설정합니다.
     *
     * @param canTarget boolean 값
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> canTarget(boolean canTarget) {
        return canTarget((context, entity) -> canTarget);
    }

    /**
     * {@link TargetedSkillAction}의 동작을 주어진 람다로 설정합니다.
     *
     * @param use BiFunction 객체
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> use(BiFunction<SkillContext, T, SkillActionResult> use) {
        this.use = use;
        return this;
    }

    /**
     * {@link TargetedSkillAction}의 동작을 주어진 람다로 설정합니다. 이때, 이 동작은 항상 성공을 반환하게 됩니다.
     *
     * @param use BiConsumer 객체
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> useWithSuccess(BiConsumer<SkillContext, T> use) {
        return use((context, target) -> {
            use.accept(context, target);
            return new SkillActionResult.Success();
        });
    }

    /**
     * 현재 설정으로 {@link TargetedSkillAction} 객체를 생성합니다.
     *
     * @return TargetedSkillAction 객체
     */
    public TargetedSkillAction<T> build() {
        return new TargetedSkillAction<>(targetClass) {
            @Override
            protected double getDistance(SkillContext context) {
                return distance.apply(context);
            }

            @Override
            protected double getMargin(SkillContext context) {
                return margin.apply(context);
            }

            @Override
            protected boolean canTarget(SkillContext context, T entity) {
                return canTarget.test(context, entity);
            }

            @Override
            protected SkillActionResult useImpl(SkillContext context, T target) {
                return use.apply(context, target);
            }
        };
    }
}
