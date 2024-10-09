package org.gjdd.batoru.channeling;

import org.gjdd.batoru.channeling.builder.ChannelingBuilder;
import org.jetbrains.annotations.ApiStatus;

/**
 * 정신 집중을 정의하는 인터페이스입니다.
 */
public interface Channeling {

    /**
     * 이 클래스의 빌더 객체를 생성하여 반환합니다.
     *
     * @return 빌더 객체
     */
    @ApiStatus.Experimental
    static ChannelingBuilder builder() {
        return new ChannelingBuilder();
    }

    /**
     * 정신 집중 시작 시 호출되는 메서드입니다.
     *
     * @param context 정신 집중 컨텍스트
     */
    default void onStart(ChannelingContext context) {
    }

    /**
     * 정신 집중 중일 때 매 틱마다 호출되는 메서드입니다.
     *
     * @param context 정신 집중 컨텍스트
     */
    void onTick(ChannelingContext context);
}
