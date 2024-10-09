package org.gjdd.batoru.skill.builder;

import net.minecraft.entity.Entity;
import org.gjdd.batoru.skill.SkillActionResult;
import org.gjdd.batoru.skill.SkillContext;
import org.gjdd.batoru.skill.TargetedSkillAction;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * {@link TargetedSkillAction}의 빌더 클래스입니다.
 *
 * @param <T> 대상 객체 타입
 */
@ApiStatus.Experimental
public final class TargetedSkillActionBuilder<T extends Entity> {
    private final Class<T> targetClass;

    private Supplier<Double> distance = () -> 0.0;
    private Supplier<Double> margin = () -> 0.5;
    private Predicate<T> canTarget = entity -> !entity.isSpectator() && entity.canHit();
    private BiFunction<SkillContext, T, SkillActionResult> performUse = (context, target) -> new SkillActionResult.Failure.Unavailable();

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
     * @param distance Supplier 객체
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> distance(Supplier<Double> distance) {
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
        return distance(() -> distance);
    }

    /**
     * {@link TargetedSkillAction}의 마진을 주어진 람다로 설정합니다.
     *
     * @param margin Supplier 객체
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> margin(Supplier<Double> margin) {
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
        return margin(() -> margin);
    }

    /**
     * {@link TargetedSkillAction}의 대상 조건을 주어진 람다로 설정합니다.
     *
     * @param canTarget Predicate 객체
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> canTarget(Predicate<T> canTarget) {
        this.canTarget = canTarget;
        return this;
    }

    /**
     * {@link TargetedSkillAction}의 대상 조건을 주어진 boolean 값으로 설정합니다.
     *
     * @param canTarget Predicate 객체
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> canTarget(boolean canTarget) {
        return canTarget(entity -> canTarget);
    }

    /**
     * {@link TargetedSkillAction}의 동작을 주어진 람다로 설정합니다.
     *
     * @param performUse BiFunction 객체
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> performUse(BiFunction<SkillContext, T, SkillActionResult> performUse) {
        this.performUse = performUse;
        return this;
    }

    /**
     * {@link TargetedSkillAction}의 동작을 주어진 람다로 설정합니다. 이때, 이 동작은 항상 성공을 반환하게 됩니다.
     *
     * @param performUse BiFunction 객체
     * @return 자기 자신 객체
     */
    public TargetedSkillActionBuilder<T> performUseWithSuccess(BiConsumer<SkillContext, T> performUse) {
        return performUse((context, target) -> {
            performUse.accept(context, target);
            return new SkillActionResult.Success();
        });
    }

    /**
     * 현재 설정으로 {@link TargetedSkillAction} 객체를 생성합니다.
     *
     * @return TargetedSkillAction 객체
     */
    public TargetedSkillAction<T> build() {
        return new TargetedSkillAction<>() {
            @Override
            protected Class<T> getTargetClass() {
                return targetClass;
            }

            @Override
            protected double getDistance() {
                return distance.get();
            }

            @Override
            protected double getMargin() {
                return margin.get();
            }

            @Override
            protected boolean canTarget(T entity) {
                return canTarget.test(entity);
            }

            @Override
            protected SkillActionResult performUse(SkillContext context, T target) {
                return performUse.apply(context, target);
            }
        };
    }
}
