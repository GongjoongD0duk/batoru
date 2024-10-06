package org.gjdd.batoru.skill;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;

/**
 * 스킬의 컨텍스트를 나타내는 클래스입니다.
 *
 * @param skill  스킬 객체
 * @param source 스킬을 사용하는 엔티티
 */
public record SkillContext(RegistryEntry<Skill> skill, LivingEntity source) {
}
