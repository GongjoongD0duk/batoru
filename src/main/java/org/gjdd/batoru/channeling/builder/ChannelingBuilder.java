package org.gjdd.batoru.channeling.builder;

import org.gjdd.batoru.channeling.Channeling;
import org.gjdd.batoru.channeling.ChannelingContext;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * {@link Channeling}의 빌더 클래스입니다.
 */
@ApiStatus.Experimental
public final class ChannelingBuilder {
    private Function<ChannelingContext, Integer> maxTime = context -> -1;
    private Predicate<ChannelingContext> immune = context -> false;
    private Consumer<ChannelingContext> onStart = context -> {};
    private Consumer<ChannelingContext> onFinish = context -> {};
    private Consumer<ChannelingContext> onInterrupt = context -> {};
    private Consumer<ChannelingContext> onTick = context -> {};

    /**
     * {@link Channeling#getMaxTime} 메서드를 설정합니다.
     *
     * @param maxTime Function 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder maxTime(Function<ChannelingContext, Integer> maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    /**
     * {@link Channeling#getMaxTime} 메서드가 주어진 int 값을 반환하도록 설정합니다.
     *
     * @param maxTime int 값
     * @return 자기 자신 객체
     */
    public ChannelingBuilder maxTime(int maxTime) {
        return maxTime(context -> maxTime);
    }

    /**
     * {@link Channeling#isImmune} 메서드를 설정합니다.
     *
     * @param immune Predicate 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder immune(Predicate<ChannelingContext> immune) {
        this.immune = immune;
        return this;
    }

    /**
     * {@link Channeling#isImmune} 메서드가 주어진 boolean 값을 반환하도록 설정합니다.
     *
     * @param immune boolean 값
     * @return 자기 자신 객체
     */
    public ChannelingBuilder immune(boolean immune) {
        return immune(context -> immune);
    }

    /**
     * {@link Channeling#onStart} 메서드를 설정합니다.
     *
     * @param onStart Consumer 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder onStart(Consumer<ChannelingContext> onStart) {
        this.onStart = onStart;
        return this;
    }

    /**
     * {@link Channeling#onFinish} 메서드를 설정합니다.
     *
     * @param onFinish Consumer 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder onFinish(Consumer<ChannelingContext> onFinish) {
        this.onFinish = onFinish;
        return this;
    }

    /**
     * {@link Channeling#onInterrupt} 메서드를 설정합니다.
     *
     * @param onInterrupt Consumer 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder onInterrupt(Consumer<ChannelingContext> onInterrupt) {
        this.onInterrupt = onInterrupt;
        return this;
    }

    /**
     * {@link Channeling#onTick} 메서드를 설정합니다.
     *
     * @param onTick Consumer 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder onTick(Consumer<ChannelingContext> onTick) {
        this.onTick = onTick;
        return this;
    }

    /**
     * 현재 설정으로 {@link Channeling} 객체를 생성합니다.
     *
     * @return Skill 객체
     */
    public Channeling build() {
        return new Channeling() {
            @Override
            public int getMaxTime(ChannelingContext context) {
                return maxTime.apply(context);
            }

            @Override
            public boolean isImmune(ChannelingContext context) {
                return immune.test(context);
            }

            @Override
            public void onStart(ChannelingContext context) {
                onStart.accept(context);
            }

            @Override
            public void onFinish(ChannelingContext context) {
                onFinish.accept(context);
            }

            @Override
            public void onInterrupt(ChannelingContext context) {
                onInterrupt.accept(context);
            }

            @Override
            public void onTick(ChannelingContext context) {
                onTick.accept(context);
            }
        };
    }
}
