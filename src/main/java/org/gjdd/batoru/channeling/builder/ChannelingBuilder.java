package org.gjdd.batoru.channeling.builder;

import org.gjdd.batoru.channeling.Channeling;
import org.gjdd.batoru.channeling.ChannelingContext;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * {@link Channeling}의 빌더 클래스입니다.
 */
public final class ChannelingBuilder {
    private Predicate<ChannelingContext> stopIfDead = context -> true;
    private Predicate<ChannelingContext> stopIfSpectator = context -> true;
    private Predicate<ChannelingContext> stopIfSilenced = context -> true;
    private Predicate<ChannelingContext> stopWhen = context -> false;
    private Consumer<ChannelingContext> onTick = context -> {};

    /**
     * 사망 상태일 때, 지정된 조건을 만족할 경우, 정신 집중을 중단하도록 설정합니다.
     *
     * @param stopIfDead Predicate 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder stopIfDead(Predicate<ChannelingContext> stopIfDead) {
        this.stopIfDead = stopIfDead;
        return this;
    }

    /**
     * 사망 상태일 때, 정신 집중을 중단할지 여부를 설정합니다.
     *
     * @param stopIfDead boolean 값
     * @return 자기 자신 객체
     */
    public ChannelingBuilder stopIfDead(boolean stopIfDead) {
        return stopIfDead(context -> stopIfDead);
    }

    /**
     * 관전 상태일 때, 지정된 조건을 만족할 경우, 정신 집중을 중단하도록 설정합니다.
     *
     * @param stopIfSpectator Predicate 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder stopIfSpectator(Predicate<ChannelingContext> stopIfSpectator) {
        this.stopIfSpectator = stopIfSpectator;
        return this;
    }

    /**
     * 관전 상태일 때, 정신 집중을 중단할지 여부를 설정합니다.
     *
     * @param stopIfSpectator boolean 값
     * @return 자기 자신 객체
     */
    public ChannelingBuilder stopIfSpectator(boolean stopIfSpectator) {
        return stopIfSpectator(context -> stopIfSpectator);
    }

    /**
     * 침묵 상태일 때, 지정된 조건을 만족할 경우, 정신 집중을 중단하도록 설정합니다.
     *
     * @param stopIfSilenced Predicate 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder stopIfSilenced(Predicate<ChannelingContext> stopIfSilenced) {
        this.stopIfSilenced = stopIfSilenced;
        return this;
    }

    /**
     * 침묵 상태일 때, 정신 집중을 중단할지 여부를 설정합니다.
     *
     * @param stopIfSilenced boolean 값
     * @return 자기 자신 객체
     */
    public ChannelingBuilder stopIfSilenced(boolean stopIfSilenced) {
        return stopIfSilenced(context -> stopIfSilenced);
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
            if (stopIfSpectator.test(context) && context.source().isSpectator() ||
                    stopIfSilenced.test(context) && context.source().hasSilencedStatusEffect() ||
                    stopIfDead.test(context) && context.source().isDead() ||
                    stopWhen.test(context)) {
                context.source().stopChanneling();
            } else {
                onTick.accept(context);
            }
        };
    }
}
