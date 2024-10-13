package org.gjdd.batoru.channeling;

import org.gjdd.batoru.channeling.builder.ChannelingBuilder;

/**
 * 정신 집중을 정의하는 인터페이스입니다.
 */
public interface Channeling {

    /**
     * 이 인터페이스의 빌더 객체를 생성하여 반환합니다.
     *
     * @return 빌더 객체
     */
    static ChannelingBuilder builder() {
        return new ChannelingBuilder();
    }

    /**
     * 침묵 상태를 무시할지 여부를 반환합니다.
     *
     * @param context 정신 집중 컨텍스트
     * @return 침묵 상태 무시 여부
     */
    default boolean ignoreSilenced(ChannelingContext context) {
        return false;
    }

    /**
     * 정신 집중 중일 때 매 틱마다 호출되는 메서드입니다.
     *
     * @param context 정신 집중 컨텍스트
     */
    void onTick(ChannelingContext context);
}
