package com.even.mricheditor;

import android.webkit.JavascriptInterface;

/**
 * Rich Editor Callback
 * Created by even.wu on 8/8/17.
 */

public abstract class RichEditorCallback {

    @JavascriptInterface public void updateAction(final String type, final boolean isActive) {
        switch (type) {
            case ".note-btn-bold":
                updateActionStates(ActionType.BOLD, isActive);
                break;
            case ".note-btn-italic":
                updateActionStates(ActionType.ITALIC, isActive);
                break;
            case ".note-btn-underline":
                updateActionStates(ActionType.UNDERLINE, isActive);
                break;
            case ".note-btn-subscript":
                updateActionStates(ActionType.SUBSCRIPT, isActive);
                break;
            case ".note-btn-superscript":
                updateActionStates(ActionType.SUPERSCRIPT, isActive);
                break;
            case ".note-btn-strikethrough":
                updateActionStates(ActionType.STRIKETHROUGH, isActive);
                break;
            default:
                break;
        }
    }

    public abstract void updateActionStates(ActionType type, boolean isActive);
}
