package org.gjdd.batoru.input;

/**
 * 플레이어의 동작을 나타내는 열거형입니다.
 */
public enum Action {

    /**
     * 아이템을 버리는 동작.
     */
    DROP,

    /**
     * 핫바를 선택하는 동작.
     */
    HOTBAR_1(0),
    HOTBAR_2(1),
    HOTBAR_3(2),
    HOTBAR_4(3),
    HOTBAR_5(4),
    HOTBAR_6(5),
    HOTBAR_7(6),
    HOTBAR_8(7),
    HOTBAR_9(8),

    /**
     * 웅크리는 동작.
     */
    SNEAK,

    /**
     * 손을 바꾸는 동작.
     */
    SWAP_OFFHAND;

    /**
     * 이 동작에 해당하는 핫바 슬롯입니다.
     * 핫바 선택 동작이 아니면 {@code -1}입니다.
     */
    public final int slot;

    Action(int slot) {
        this.slot = slot;
    }

    Action() {
        this(-1);
    }
}
