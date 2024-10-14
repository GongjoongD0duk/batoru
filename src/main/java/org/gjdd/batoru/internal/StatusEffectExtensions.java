package org.gjdd.batoru.internal;

import net.minecraft.entity.effect.StatusEffect;

/**
 * {@link net.minecraft.entity.effect.StatusEffect} 클래스에 주입되는 인터페이스입니다.
 */
public interface StatusEffectExtensions {

    /**
     * 상태 효과가 밀쳐짐 효과인지 여부를 반환합니다.
     *
     * @return 밀쳐짐 효과 여부
     */
    default boolean isPushed() {
        return false;
    }

    /**
     * 상태 효과가 침묵 효과인지 여부를 반환합니다.
     *
     * @return 침묵 효과 여부
     */
    default boolean isSilenced() {
        return false;
    }

    /**
     * 상태 효과가 밀쳐짐 효과인지 여부를 설정합니다.
     *
     * @param pushed 밀쳐짐 효과 여부
     * @return 자기 자신 객체
     */
    default StatusEffect setPushed(boolean pushed) {
        return null;
    }

    /**
     * 상태 효과가 침묵 효과인지 여부를 설정합니다.
     *
     * @param silenced 침묵 효과 여부
     * @return 자기 자신 객체
     */
    default StatusEffect setSilenced(boolean silenced) {
        return null;
    }
}
