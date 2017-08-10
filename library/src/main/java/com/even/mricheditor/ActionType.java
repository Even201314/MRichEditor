package com.even.mricheditor;

/**
 * Action Type
 * Created by even.wu on 8/8/17.
 */

public enum ActionType {
    //FONT
    SIZE, LINE_HEIGHT, TEXT_COLOR, HIGHLIGHT,

    // Format
    BOLD, ITALIC, UNDERLINE, SUBSCRIPT, SUPERSCRIPT, STRIKETHROUGH,

    // Style
    NORMAL, H1, H2, H3, H4, H5, H6,

    //Justify
    JUSTIFY_LEFT, JUSTIFY_CENTER, JUSTIFY_RIGHT, JUSTIFY_FULL,

    // List Style
    ORDERED, UNORDERED,

    INDENT, OUTDENT,

    // Insert
    IMAGE, LINK, TABLE, LINE,

    BLOCKQUOTE, CODE_BLOCK,

    CODEVIEW
}
