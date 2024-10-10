package org.gjdd.batoru.channeling;

import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;

/**
 * 정신 집중의 컨텍스트를 나타내는 클래스입니다.
 */
public final class ChannelingContext {
    private final Channeling channeling;
    private final LivingEntity source;

    private int time = 0;

    @ApiStatus.Internal
    public ChannelingContext(Channeling channeling, LivingEntity source) {
        this.channeling = channeling;
        this.source = source;
    }

    /**
     * 정신 집중 객체를 반환합니다.
     *
     * @return 채널링 객체
     */
    public Channeling channeling() {
        return channeling;
    }

    /**
     * 정신 집중을 사용하는 엔티티를 반환합니다.
     *
     * @return 정신 집중을 사용하는 엔티티
     */
    public LivingEntity source() {
        return source;
    }

    /**
     * 정신 집중이 시작되고 흐른 틱을 반환합니다.
     *
     * @return 정신 집중이 시작되고 흐른 틱
     */
    public int time() {
        return time;
    }

    @ApiStatus.Internal
    public void incrementTime() {
        time++;
    }
}
