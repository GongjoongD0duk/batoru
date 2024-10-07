package org.gjdd.batoru.channeling;

/**
 * 정신 집중을 정의하는 인터페이스입니다.
 */
public interface Channeling {

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
