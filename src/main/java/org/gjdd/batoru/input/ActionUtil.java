package org.gjdd.batoru.input;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {@link Action} 열거형의 유틸리티 클래스입니다.
 */
public final class ActionUtil {
    private static final List<Action> HOTBAR_ACTIONS = Arrays.stream(Action.values())
            .filter(action -> action.slot != -1)
            .toList();
    private static final Map<Integer, Action> SLOT_TO_ACTION = HOTBAR_ACTIONS.stream()
            .collect(Collectors.toMap(action -> action.slot, action -> action));

    /**
     * 모든 핫바 선택 동작의 목록을 반환합니다.
     *
     * @return 핫바 선택 동작 리스트
     */
    public static List<Action> getHotbarActions() {
        return HOTBAR_ACTIONS;
    }

    /**
     * 주어진 슬롯에 해당하는 핫바 선택 동작을 반환합니다.
     *
     * @param slot 슬롯
     * @return 슬롯에 해당하는 동작, 없으면 {@code null}
     */
    @Nullable
    public static Action getHotbarAction(int slot) {
        return SLOT_TO_ACTION.get(slot);
    }
}
