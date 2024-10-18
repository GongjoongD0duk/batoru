package org.gjdd.batoru.internal;

import net.minecraft.entity.effect.StatusEffect;
import org.jetbrains.annotations.ApiStatus;

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

    /**
     * 상태 효과에 대상이 공격할 수 없도록 하는 속성 수정자들을 추가합니다.
     *
     * @return 자기 자신 객체
     */
    @ApiStatus.Experimental
    default StatusEffect addDisarmedAttributeModifiers() {
        return null;
    }

    /**
     * 상태 효과에 대상이 움직일 수 없도록 하는 속성 수정자들을 추가합니다.
     *
     * @return 자기 자신 객체
     */
    @ApiStatus.Experimental
    default StatusEffect addRootedAttributeModifiers() {
        return null;
    }
}
