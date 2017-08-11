package com.even.mricheditor;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Font Style
 * Created by even.wu on 9/8/17.
 */

public class FontStyle {

    /**
     * font-family : "Helvetica Neue", Helvetica, Arial, sans-serif
     * font-size : 36
     * text-align : start
     * list-style-type : disc
     * line-height : 1.1
     * font-bold : normal
     * font-italic : normal
     * font-underline : normal
     * font-subscript : normal
     * font-superscript : normal
     * font-strikethrough : normal
     * list-style : none
     * anchor : false
     * ancestors : [{},{}]
     * range : {"sc":{},"so":4,"ec":{},"eo":4}
     */

    @SerializedName("font-family") private String fontFamily;
    @SerializedName("font-size") private int fontSize;
    @SerializedName("font-backColor") private String fontBackColor;
    @SerializedName("font-foreColor") private String fontForeColor;
    @SerializedName("text-align") private String textAlign;
    @SerializedName("list-style-type") private String listStyleType;
    @SerializedName("line-height") private String lineHeight;
    @SerializedName("font-bold") private String fontBold;
    @SerializedName("font-italic") private String fontItalic;
    @SerializedName("font-underline") private String fontUnderline;
    @SerializedName("font-subscript") private String fontSubscript;
    @SerializedName("font-superscript") private String fontSuperscript;
    @SerializedName("font-strikethrough") private String fontStrikethrough;
    @SerializedName("font-block") private String fontBlock;
    @SerializedName("list-style") private String listStyle;
    private boolean anchor;
    private RangeBean range;
    private List<AncestorsBean> ancestors;

    public String getFontFamily() {
        return fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public ActionType getTextAlign() {
        if (TextUtils.isEmpty(textAlign)) {
            return null;
        }

        ActionType type;
        switch (textAlign) {
            case "left":
                type = ActionType.JUSTIFY_LEFT;
                break;
            case "center":
                type = ActionType.JUSTIFY_CENTER;
                break;
            case "right":
                type = ActionType.JUSTIFY_RIGHT;
                break;
            case "justify":
                type = ActionType.JUSTIFY_FULL;
                break;
            default:
                type = ActionType.JUSTIFY_FULL;
                break;
        }
        return type;
    }

    public String getListStyleType() {
        return listStyleType;
    }

    public double getLineHeight() {
        double height = 0;

        if (TextUtils.isEmpty(lineHeight)) {
            return height;
        }

        try {
            height = Double.valueOf(lineHeight);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return height;
    }

    public ActionType getFontBlock() {
        ActionType type = ActionType.STYLE_NONE;
        if (TextUtils.isEmpty(fontBlock)) {
            return type;
        }

        if ("p".equals(fontBlock)) {
            type = ActionType.NORMAL;
        } else if ("h1".equals(fontBlock)) {
            type = ActionType.H1;
        } else if ("h2".equals(fontBlock)) {
            type = ActionType.H2;
        } else if ("h3".equals(fontBlock)) {
            type = ActionType.H3;
        } else if ("h4".equals(fontBlock)) {
            type = ActionType.H4;
        } else if ("h5".equals(fontBlock)) {
            type = ActionType.H5;
        } else if ("h6".equals(fontBlock)) {
            type = ActionType.H6;
        }

        return type;
    }

    public String getFontBackColor() {
        return fontBackColor;
    }

    public String getFontForeColor() {
        return fontForeColor;
    }

    public boolean isBold() {
        return "bold".equals(fontBold);
    }

    public boolean isItalic() {
        return "italic".equals(fontItalic);
    }

    public boolean isUnderline() {
        return "underline".equals(fontUnderline);
    }

    public boolean isSubscript() {
        return "subscript".equals(fontSubscript);
    }

    public boolean isSuperscript() {
        return "superscript".equals(fontSuperscript);
    }

    public boolean isStrikethrough() {
        return "strikethrough".equals(fontStrikethrough);
    }

    public ActionType getListStyle() {
        if (TextUtils.isEmpty(listStyle)) {
            return null;
        }
        ActionType type;

        if ("ordered".equals(listStyle)) {
            type = ActionType.ORDERED;
        } else if ("unordered".equals(listStyle)) {
            type = ActionType.UNORDERED;
        } else {
            type = ActionType.LIST_STYLE_NONE;
        }

        return type;
    }

    public boolean isAnchor() {
        return anchor;
    }

    public RangeBean getRange() {
        return range;
    }

    public List<AncestorsBean> getAncestors() {
        return ancestors;
    }

    public static class RangeBean {
        /**
         * sc : {}
         * so : 4
         * ec : {}
         * eo : 4
         */

        private ScBean sc;
        private int so;
        private EcBean ec;
        private int eo;

        public ScBean getSc() {
            return sc;
        }

        public void setSc(ScBean sc) {
            this.sc = sc;
        }

        public int getSo() {
            return so;
        }

        public void setSo(int so) {
            this.so = so;
        }

        public EcBean getEc() {
            return ec;
        }

        public void setEc(EcBean ec) {
            this.ec = ec;
        }

        public int getEo() {
            return eo;
        }

        public void setEo(int eo) {
            this.eo = eo;
        }

        public static class ScBean {
        }

        public static class EcBean {
        }
    }

    public static class AncestorsBean {
    }
}
