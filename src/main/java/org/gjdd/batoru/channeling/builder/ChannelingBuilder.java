package org.gjdd.batoru.channeling.builder;

import org.gjdd.batoru.channeling.Channeling;
import org.gjdd.batoru.channeling.ChannelingContext;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

/**
 * {@link Channeling}의 빌더 클래스입니다.
 */
@ApiStatus.Experimental
public final class ChannelingBuilder {
    private Consumer<ChannelingContext> onStart = context -> {};
    private Consumer<ChannelingContext> onTick = context -> {};

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
            public void onStart(ChannelingContext context) {
                onStart.accept(context);
            }

            @Override
            public void onTick(ChannelingContext context) {
                onTick.accept(context);
            }
        };
    }
}
