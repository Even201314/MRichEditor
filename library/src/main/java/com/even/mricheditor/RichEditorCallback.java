package com.even.mricheditor;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;

/**
 * Rich Editor Callback
 * Created by even.wu on 8/8/17.
 */

public abstract class RichEditorCallback {
    private Gson gson = new Gson();
    private FontStyle mFontStyle = new FontStyle();
    private String html;
    private OnGetHtmlListener onGetHtmlListener;

    private List<ActionType> mFontBlockGroup =
        Arrays.asList(ActionType.NORMAL, ActionType.H1, ActionType.H2, ActionType.H3, ActionType.H4,
            ActionType.H5, ActionType.H6);
    private List<ActionType> mTextAlignGroup =
        Arrays.asList(ActionType.JUSTIFY_LEFT, ActionType.JUSTIFY_CENTER, ActionType.JUSTIFY_RIGHT,
            ActionType.JUSTIFY_FULL);
    private List<ActionType> mListStyleGroup =
        Arrays.asList(ActionType.ORDERED, ActionType.UNORDERED);

    @JavascriptInterface public void returnHtml(String html) {
        this.html = html;
        if (onGetHtmlListener != null) {
            onGetHtmlListener.getHtml(html);
        }
    }

    @JavascriptInterface public void updateCurrentStyle(String currentStyle) {
        FontStyle fontStyle = gson.fromJson(currentStyle, FontStyle.class);
        if (fontStyle != null) {
            updateStyle(fontStyle);
        }
    }

    private void updateStyle(FontStyle fontStyle) {
        if (mFontStyle.getFontFamily() == null || !mFontStyle.getFontFamily()
            .equals(fontStyle.getFontFamily())) {
            if (!TextUtils.isEmpty(fontStyle.getFontFamily())) {
                String font = fontStyle.getFontFamily().split(",")[0].replace("\"", "");
                notifyFontStyleChange(ActionType.FAMILY, font);
            }
        }

        if (mFontStyle.getFontForeColor() == null || !mFontStyle.getFontForeColor()
            .equals(fontStyle.getFontForeColor())) {
            if (!TextUtils.isEmpty(fontStyle.getFontForeColor())) {
                notifyFontStyleChange(ActionType.FORE_COLOR, fontStyle.getFontForeColor());
            }
        }

        if (mFontStyle.getFontBackColor() == null || !mFontStyle.getFontBackColor()
            .equals(fontStyle.getFontBackColor())) {
            if (!TextUtils.isEmpty(fontStyle.getFontBackColor())) {
                notifyFontStyleChange(ActionType.BACK_COLOR, fontStyle.getFontBackColor());
            }
        }

        if (mFontStyle.getFontSize() != fontStyle.getFontSize()) {
            notifyFontStyleChange(ActionType.SIZE, String.valueOf(fontStyle.getFontSize()));
        }

        if (mFontStyle.getTextAlign() != fontStyle.getTextAlign()) {
            for (int i = 0, size = mTextAlignGroup.size(); i < size; i++) {
                ActionType type = mTextAlignGroup.get(i);
                notifyFontStyleChange(type, String.valueOf(type == fontStyle.getTextAlign()));
            }
        }

        if (mFontStyle.getLineHeight() != fontStyle.getLineHeight()) {
            notifyFontStyleChange(ActionType.LINE_HEIGHT,
                String.valueOf(fontStyle.getLineHeight()));
        }

        if (mFontStyle.isBold() != fontStyle.isBold()) {
            notifyFontStyleChange(ActionType.BOLD, String.valueOf(fontStyle.isBold()));
        }

        if (mFontStyle.isItalic() != fontStyle.isItalic()) {
            notifyFontStyleChange(ActionType.ITALIC, String.valueOf(fontStyle.isItalic()));
        }

        if (mFontStyle.isUnderline() != fontStyle.isUnderline()) {
            notifyFontStyleChange(ActionType.UNDERLINE, String.valueOf(fontStyle.isUnderline()));
        }

        if (mFontStyle.isSubscript() != fontStyle.isSubscript()) {
            notifyFontStyleChange(ActionType.SUBSCRIPT, String.valueOf(fontStyle.isSubscript()));
        }

        if (mFontStyle.isSuperscript() != fontStyle.isSuperscript()) {
            notifyFontStyleChange(ActionType.SUPERSCRIPT,
                String.valueOf(fontStyle.isSuperscript()));
        }

        if (mFontStyle.isStrikethrough() != fontStyle.isStrikethrough()) {
            notifyFontStyleChange(ActionType.STRIKETHROUGH,
                String.valueOf(fontStyle.isStrikethrough()));
        }

        if (mFontStyle.getFontBlock() != fontStyle.getFontBlock()) {
            for (int i = 0, size = mFontBlockGroup.size(); i < size; i++) {
                ActionType type = mFontBlockGroup.get(i);
                notifyFontStyleChange(type, String.valueOf(type == fontStyle.getFontBlock()));
            }
        }

        if (mFontStyle.getListStyle() != fontStyle.getListStyle()) {
            for (int i = 0, size = mListStyleGroup.size(); i < size; i++) {
                ActionType type = mListStyleGroup.get(i);
                notifyFontStyleChange(type, String.valueOf(type == fontStyle.getListStyle()));
            }
        }

        mFontStyle = fontStyle;
    }

    public abstract void notifyFontStyleChange(ActionType type, String value);

    public interface OnGetHtmlListener {
        void getHtml(String html);
    }

    public OnGetHtmlListener getOnGetHtmlListener() {
        return onGetHtmlListener;
    }

    public void setOnGetHtmlListener(OnGetHtmlListener onGetHtmlListener) {
        this.onGetHtmlListener = onGetHtmlListener;
    }
}
