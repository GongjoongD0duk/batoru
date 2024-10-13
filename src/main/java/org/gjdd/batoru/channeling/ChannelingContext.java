package org.gjdd.batoru.channeling;

import net.minecraft.entity.LivingEntity;

/**
 * 정신 집중의 컨텍스트를 나타내는 클래스입니다.
 *
 * @param channeling 정신 집중 객체
 * @param source     정신 집중을 사용하는 엔티티
 * @param time       정신 집중이 시작되고 흐른 시간
 */
public record ChannelingContext(Channeling channeling, LivingEntity source, int time) {
}
