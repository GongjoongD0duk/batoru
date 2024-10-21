package org.gjdd.batoru.channeling;

import java.util.function.Predicate;

/**
 * 정신 집중을 정의하는 인터페이스입니다.
 */
public interface Channeling {

    /**
     * 정신 집중 중일 때 매 틱마다 호출되는 메서드입니다.
     *
     * @param context 정신 집중 컨텍스트
     */
    void onTick(ChannelingContext context);

    /**
     * 현재의 정신 집중 동작에, 밀쳐짐 상태일 경우 정신 집중을 중단하는 동작을 추가한 새로운 정신 집중을
     * 생성하여 반환합니다.
     *
     * @return 정신 집중 객체
     */
    default Channeling stopIfPushed() {
        return stopIf(context -> context.source().hasPushedStatusEffect());
    }

    /**
     * 현재의 정신 집중 동작에, 침묵 상태일 경우 정신 집중을 중단하는 동작을 추가한 새로운 정신 집중을
     * 생성하여 반환합니다.
     *
     * @return 정신 집중 객체
     */
    default Channeling stopIfSilenced() {
        return stopIf(context -> context.source().hasSilencedStatusEffect());
    }

    /**
     * 현재의 정신 집중 동작에, 지정된 조건을 만족할 경우 정신 집중을 중단하는 동작을 추가한 새로운 정신
     * 집중을 생성하여 반환합니다.
     *
     * @param predicate 정신 집중을 중단하는 조건
     * @return 결합된 {@link Channeling} 객체
     */
    default Channeling stopIf(Predicate<ChannelingContext> predicate) {
        return context -> {
            if (predicate.test(context)) {
                context.source().stopChanneling();
            } else {
                onTick(context);
            }
        };
    }
}
