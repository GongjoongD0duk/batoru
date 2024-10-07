package org.gjdd.batoru.skill;

import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * {@link Skill} 클래스의 타겟팅 유틸리티 클래스입니다.
 */
public final class TargetedSkillUtil {

    /**
     * 엔티티가 조준하는 대상 엔티티를 찾습니다.
     *
     * @param entity    엔티티 객체
     * @param distance  최대 거리
     * @param margin    타겟팅 마진
     * @param clazz     대상 엔티티 클래스
     * @param predicate 대상 엔티티 조건
     * @param <T>       대상 엔티티 타입
     * @return 대상 엔티티 객체
     */
    @Nullable
    public static <T extends Entity> T findCrosshairTarget(
            Entity entity,
            double distance,
            double margin,
            Class<T> clazz,
            Predicate<T> predicate
    ) {
        return clazz.cast(
                findCrosshairTarget(
                        entity,
                        distance,
                        margin,
                        target -> clazz.isInstance(target) && predicate.test(clazz.cast(target))
                )
        );
    }

    /**
     * 엔티티가 조준하는 대상 엔티티를 찾습니다.
     *
     * @param entity    엔티티 객체
     * @param distance  최대 거리
     * @param margin    타겟팅 마진
     * @param predicate 대상 엔티티 조건
     * @return 대상 엔티티 객체
     */
    @Nullable
    public static Entity findCrosshairTarget(
            Entity entity,
            double distance,
            double margin,
            Predicate<Entity> predicate
    ) {
        var min = entity.getCameraPosVec(1);
        var max = entity.raycast(distance, 1, false).getPos();
        var hitResult = raycast(
                entity,
                min,
                max,
                entity.getBoundingBox().stretch(max.subtract(min)).expand(1.0),
                max.squaredDistanceTo(min),
                margin,
                predicate
        );
        return hitResult == null ? null : hitResult.getEntity();
    }

    @Nullable
    private static EntityHitResult raycast(
            Entity entity,
            Vec3d min,
            Vec3d max,
            Box box,
            double distance,
            double margin,
            Predicate<Entity> predicate
    ) {
        double closestDistance = distance;
        Entity closestEntity = null;
        Vec3d closestPos = null;

        for (var currentEntity : entity.getWorld().getOtherEntities(entity, box, predicate)) {
            var currentBox = currentEntity.getBoundingBox().expand(margin);
            var optional = currentBox.raycast(min, max);
            if (currentBox.contains(min)) {
                if (closestDistance < 0.0) {
                    continue;
                }

                closestDistance = 0.0;
                closestEntity = currentEntity;
                closestPos = optional.orElse(min);
                continue;
            }

            if (optional.isEmpty()) {
                continue;
            }

            var currentPos = optional.get();
            var currentDistance = min.squaredDistanceTo(currentPos);
            if (currentDistance >= closestDistance && closestDistance != 0.0) {
                continue;
            }

            closestDistance = currentDistance;
            closestEntity = currentEntity;
            closestPos = currentPos;
        }

        return closestEntity == null ? null : new EntityHitResult(closestEntity, closestPos);
    }
}
