package org.gjdd.batoru.channeling.builder;

import org.gjdd.batoru.channeling.Channeling;
import org.gjdd.batoru.channeling.ChannelingContext;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * {@link Channeling}의 빌더 클래스입니다.
 */
public final class ChannelingBuilder {
    private Predicate<ChannelingContext> ignoreSilenced = context -> false;
    private Consumer<ChannelingContext> onTick = context -> {};

    /**
     * {@link Channeling#ignoreSilenced} 메서드를 주어진 람다로 설정합니다.
     *
     * @param ignoreSilenced Predicate 객체
     * @return 자기 자신 객체
     */
    public ChannelingBuilder ignoreSilenced(Predicate<ChannelingContext> ignoreSilenced) {
        this.ignoreSilenced = ignoreSilenced;
        return this;
    }

    /**
     * {@link Channeling#ignoreSilenced} 메서드가 항상 주어진 boolean 값을 반환하도록 설정합니다.
     *
     * @param ignoreSilenced boolean 값
     * @return 자기 자신 객체
     */
    public ChannelingBuilder ignoreSilenced(boolean ignoreSilenced) {
        return ignoreSilenced(context -> ignoreSilenced);
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
        return new Channeling() {
            @Override
            public boolean ignoreSilenced(ChannelingContext context) {
                return ignoreSilenced.test(context);
            }

            @Override
            public void onTick(ChannelingContext context) {
                onTick.accept(context);
            }
        };
    }
}
