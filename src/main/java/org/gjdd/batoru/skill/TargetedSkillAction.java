package org.gjdd.batoru.skill;

import net.minecraft.entity.Entity;

/**
 * 대상을 요구하는 스킬의 동작을 정의하는 클래스입니다.
 *
 * @param <T> 탐색 대상 타입
 */
public abstract class TargetedSkillAction<T extends Entity> implements SkillAction {

    /**
     * 탐색할 대상의 클래스입니다.
     *
     * @return 탐색 대상 클래스
     */
    protected abstract Class<T> getTargetClass();

    /**
     * 대상을 탐색하는 최대 거리입니다.
     *
     * @return 대상 탐색 거리
     */
    protected abstract double getDistance();

    /**
     * 대상을 탐색하는 마진입니다.
     *
     * @return 대상 탐색 마진
     */
    protected double getMargin() {
        return 0.5;
    }

    /**
     * 주어진 엔티티가 대상이 될 수 있는지 여부를 반환합니다.
     *
     * @param entity 대상 엔티티
     * @return 대상이 될 수 있다면 {@code true}, 그렇지 않다면 {@code false}
     */
    protected boolean canTarget(T entity) {
        return !entity.isSpectator() && entity.canHit();
    }

    /**
     * 대상과 함께 스킬을 사용하고 그 결과를 반환합니다.
     *
     * @param context 스킬 컨텍스트
     * @param target  대상 엔티티
     * @return 스킬 사용 결과
     */
    protected abstract SkillActionResult performUse(SkillContext context, T target);

    /**
     * 대상을 찾아 스킬을 사용하고 그 결과를 반환합니다.
     *
     * @param context 스킬 컨텍스트
     * @return 스킬 사용 결과
     */
    @Override
    public final SkillActionResult use(SkillContext context) {
        var target = TargetedSkillUtil.findCrosshairTarget(
                context.source(),
                getDistance(),
                getMargin(),
                getTargetClass(),
                this::canTarget
        );
        if (target == null) {
            return new SkillActionResult.Failure.NoTarget();
        }

        return performUse(context, target);
    }
}
