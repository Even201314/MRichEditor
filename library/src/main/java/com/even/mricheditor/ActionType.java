package com.even.mricheditor;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Action Type
 * Created by even.wu on 8/8/17.
 */

public enum ActionType {
    NONE(0),

    // FONT
    FAMILY(1), SIZE(2), LINE_HEIGHT(3), FORE_COLOR(4), BACK_COLOR(5),

    // Format
    BOLD(6), ITALIC(7), UNDERLINE(8), SUBSCRIPT(9), SUPERSCRIPT(10), STRIKETHROUGH(11),

    // Style
    NORMAL(12), H1(13), H2(14), H3(15), H4(16), H5(17), H6(18),

    //Justify
    JUSTIFY_LEFT(19), JUSTIFY_CENTER(20), JUSTIFY_RIGHT(21), JUSTIFY_FULL(22),

    // List Style
    ORDERED(23), UNORDERED(24),

    INDENT(25), OUTDENT(26),

    // Insert
    IMAGE(27), LINK(28), TABLE(29), LINE(30),

    BLOCK_QUOTE(31), BLOCK_CODE(32),

    CODE_VIEW(33);

    private int value;

    ActionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    private static final Map<Integer, ActionType> actionTypeMap = new HashMap<>();

    static {
        for (ActionType actionType : values()) {
            actionTypeMap.put(actionType.getValue(), actionType);
        }
    }

    public static ActionType fromInteger(int key) {
        return actionTypeMap.get(key);
    }
}
