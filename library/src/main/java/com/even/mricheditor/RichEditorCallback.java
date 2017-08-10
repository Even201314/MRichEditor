package com.even.mricheditor;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import com.google.gson.Gson;

/**
 * Rich Editor Callback
 * Created by even.wu on 8/8/17.
 */

public abstract class RichEditorCallback {
    private Gson gson = new Gson();
    private FontStyle mFontStyle = new FontStyle();

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
                notifyFontFamilyChange(font);
            }
        }

        if (mFontStyle.getFontSize() != fontStyle.getFontSize()) {
            notifyFontSizeChange(fontStyle.getFontSize());
        }

        if (mFontStyle.getTextAlign() != fontStyle.getTextAlign()) {
            notifyJustifyChange(fontStyle.getTextAlign());
        }

        if (mFontStyle.getLineHeight() != fontStyle.getLineHeight()) {
            notifyLineHeightChange(fontStyle.getLineHeight());
        }

        if (mFontStyle.isBold() != fontStyle.isBold()) {
            notifyBoldChange(fontStyle.isBold());
        }

        if (mFontStyle.isItalic() != fontStyle.isItalic()) {
            notifyItalicChange(fontStyle.isItalic());
        }

        if (mFontStyle.isUnderline() != fontStyle.isUnderline()) {
            notifyUnderlineChange(fontStyle.isUnderline());
        }

        if (mFontStyle.isSubscript() != fontStyle.isSubscript()) {
            notifySubscriptChange(fontStyle.isSubscript());
        }

        if (mFontStyle.isSuperscript() != fontStyle.isSuperscript()) {
            notifySuperscriptChange(fontStyle.isSuperscript());
        }

        if (mFontStyle.isStrikethrough() != fontStyle.isStrikethrough()) {
            notifyStrikethroughChange(fontStyle.isStrikethrough());
        }

        if (mFontStyle.getFontBlock() != fontStyle.getFontBlock()) {
            notifyFontBlockChange(fontStyle.getFontBlock());
        }

        if (mFontStyle.getListStyle() != fontStyle.getListStyle()) {
            notifyListStyleChange(fontStyle.getListStyle());
        }

        mFontStyle = fontStyle;
    }

    public abstract void notifyFontFamilyChange(String fontFamily);

    public abstract void notifyJustifyChange(ActionType type);

    public abstract void notifyFontSizeChange(double fontSize);

    public abstract void notifyLineHeightChange(double lineHeight);

    public abstract void notifyBoldChange(boolean isBold);

    public abstract void notifyItalicChange(boolean isItalic);

    public abstract void notifyUnderlineChange(boolean isUnderline);

    public abstract void notifySubscriptChange(boolean isSubscript);

    public abstract void notifySuperscriptChange(boolean isSuperscript);

    public abstract void notifyStrikethroughChange(boolean isStrikethrough);

    public abstract void notifyFontBlockChange(ActionType type);

    public abstract void notifyListStyleChange(ActionType type);
}
