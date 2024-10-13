package org.gjdd.batoru.channeling.builder;

import org.gjdd.batoru.channeling.Channeling;
import org.gjdd.batoru.channeling.ChannelingContext;

import java.util.function.Consumer;

/**
 * {@link Channeling}의 빌더 클래스입니다.
 */
public final class ChannelingBuilder {
    private Consumer<ChannelingContext> onTick = context -> {};

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
        return context -> onTick.accept(context);
    }
}
