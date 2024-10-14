package org.gjdd.batoru.channeling.builder;

import org.gjdd.batoru.channeling.Channeling;
import org.gjdd.batoru.channeling.ChannelingContext;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * {@link Channeling}의 빌더 클래스입니다. 이 빌더 클래스는 기본적으로 침묵 상태일 때
 * 정신 집중을 중단하도록 설정되어 있습니다.
 */
public final class ChannelingBuilder {
    private Predicate<ChannelingContext> stopOnSilenced = context -> true;
    private Predicate<ChannelingContext> stopWhen = context -> false;
    private Consumer<ChannelingContext> onTick = context -> {};

    /**
     * 침묵 상태일 때, 지정된 조건을 만족할 경우, 정신 집중을 중단하도록 설정합니다.
     *
     * @param stopOnSilenced Predicate 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder stopOnSilenced(Predicate<ChannelingContext> stopOnSilenced) {
        this.stopOnSilenced = stopOnSilenced;
        return this;
    }

    /**
     * 침묵 상태일 때, 정신 집중을 중단할지 설정합니다.
     *
     * @param stopOnSilenced boolean 값
     * @return 자기 자신 객체
     */
    public ChannelingBuilder stopOnSilenced(boolean stopOnSilenced) {
        return stopOnSilenced(context -> stopOnSilenced);
    }

    /**
     * 지정된 조건을 만족할 경우, 정신 집중을 중단하도록 설정합니다.
     *
     * @param stopWhen Predicate 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder stopWhen(Predicate<ChannelingContext> stopWhen) {
        this.stopWhen = stopWhen;
        return this;
    }

    /**
     * {@link Channeling#onTick} 메서드를 주어진 람다로 설정합니다.
     *
     * @param onTick Consumer 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder onTick(Consumer<ChannelingContext> onTick) {
        this.onTick = onTick;
        return this;
    }

    /**
     * 현재 설정으로 {@link Channeling} 객체를 생성하여 반환합니다.
     *
     * @return Channeling 객체
     */
    public Channeling build() {
        return context -> {
            if (stopOnSilenced.test(context) && context.source().hasSilencedStatusEffect()) {
                context.source().stopChanneling();
            } else {
                onTick.accept(context);
                if (stopWhen.test(context)) {
                    context.source().stopChanneling();
                }
            }
        };
    }
}
