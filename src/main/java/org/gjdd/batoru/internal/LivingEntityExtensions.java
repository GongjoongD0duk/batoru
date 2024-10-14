package org.gjdd.batoru.internal;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import org.gjdd.batoru.channeling.Channeling;
import org.gjdd.batoru.job.Job;
import org.gjdd.batoru.skill.Skill;
import org.gjdd.batoru.skill.SkillActionResult;
import org.jetbrains.annotations.Nullable;

/**
 * {@link net.minecraft.entity.LivingEntity} 클래스에 주입되는 인터페이스입니다.
 */
public interface LivingEntityExtensions {

    /**
     * 엔티티가 밀쳐짐 상태일 때 고정되는 속도를 반환합니다.
     *
     * @return 고정되는 속도
     */
    default Vec3d getPushedVelocity() {
        return null;
    }

    /**
     * 엔티티가 밀쳐짐 상태일 때 고정되는 속도를 설정합니다.
     *
     * @param pushedVelocity 고정되는 속도
     */
    default void setPushedVelocity(Vec3d pushedVelocity) {
    }

    /**
     * 엔티티가 밀쳐짐 상태인지 여부를 반환합니다.
     *
     * @return 밀쳐짐 상태 여부
     */
    default boolean hasPushedStatusEffect() {
        return false;
    }

    /**
     * 엔티티가 침묵 상태인지 여부를 반환합니다.
     *
     * @return 침묵 상태 여부
     */
    default boolean hasSilencedStatusEffect() {
        return false;
    }

    /**
     * 엔티티가 정신 집중 중인지 여부를 반환합니다.
     *
     * @return 엔티티 정신 집중 여부
     */
    default boolean isChanneling() {
        return false;
    }

    /**
     * 엔티티가 정신 집중을 시작하도록 합니다.
     *
     * @param channeling 정신 집중 객체
     * @return 정신 집중을 시작했다면 {@code true}, 그렇지 않으면 {@code false}
     */
    default boolean startChanneling(Channeling channeling) {
        return false;
    }

    /**
     * 엔티티가 정신 집중을 중단하도록 합니다.
     *
     * @return 정신 집중을 중단했다면 {@code true}, 그렇지 않으면 {@code false}
     */
    default boolean stopChanneling() {
        return false;
    }

    /**
     * 엔티티의 직업을 반환합니다.
     *
     * @return 직업 객체
     */
    default @Nullable RegistryEntry<Job> getJob() {
        return null;
    }

    /**
     * 엔티티의 직업을 설정합니다.
     *
     * @param job 직업 객체. 직업을 제거하려면 {@code null}을 전달하세요.
     */
    default void setJob(@Nullable RegistryEntry<Job> job) {
    }

    /**
     * 주어진 스킬이 쿨다운 상태인지 확인합니다.
     *
     * @param skill 스킬 객체
     * @return 스킬이 쿨다운 상태면 {@code true}, 그렇지 않으면 {@code false}
     */
    default boolean hasSkillCooldown(RegistryEntry<Skill> skill) {
        return false;
    }

    /**
     * 주어진 스킬의 남은 쿨다운 틱을 반환합니다.
     *
     * @param skill 스킬 객체
     * @return 스킬의 남은 쿨다운 틱
     */
    default int getSkillCooldown(RegistryEntry<Skill> skill) {
        return 0;
    }

    /**
     * 주어진 스킬의 쿨다운을 설정합니다.
     *
     * @param skill    스킬 객체
     * @param cooldown 설정할 쿨다운 틱
     */
    default void setSkillCooldown(RegistryEntry<Skill> skill, int cooldown) {
    }

    /**
     * 스킬을 사용할 수 있는지의 여부를 반환합니다.
     *
     * @param skill 스킬 객체
     * @return 스킬 사용 가능 여부
     */
    default SkillActionResult canUseSkill(RegistryEntry<Skill> skill) {
        return null;
    }

    /**
     * 스킬을 사용하도록 시도하고 그 결과를 반환합니다.
     *
     * @param skill 스킬 객체
     * @return 스킬 사용 결과
     */
    default SkillActionResult useSkill(RegistryEntry<Skill> skill) {
        return null;
    }
}
