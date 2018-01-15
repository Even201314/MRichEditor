package com.even.mricheditor;

import android.os.Build;
import android.support.annotation.NonNull;
import android.webkit.WebView;

/**
 * Rich Editor Action
 * Created by even.wu on 8/8/17.
 */

public class RichEditorAction {
    private WebView mWebView;

    public RichEditorAction(WebView webView) {
        this.mWebView = webView;
    }

    public void undo() {
        load("javascript:undo()");
    }

    public void redo() {
        load("javascript:redo()");
    }

    public void focus() {
        load("javascript:focus()");
    }

    public void disable() {
        load("javascript:disable()");
    }

    public void enable() {
        load("javascript:enable()");
    }

    /******************** Font ********************/
    public void bold() {
        load("javascript:bold()");
    }

    public void italic() {
        load("javascript:italic()");
    }

    public void underline() {
        load("javascript:underline()");
    }

    public void strikethrough() {
        load("javascript:strikethrough()");
    }

    public void superscript() {
        load("javascript:superscript()");
    }

    public void subscript() {
        load("javascript:subscript()");
    }

    public void backColor(String color) {
        load("javascript:backColor('" + color + "')");
    }

    public void foreColor(String color) {
        load("javascript:foreColor('" + color + "')");
    }

    public void fontName(String fontName) {
        load("javascript:fontName('" + fontName + "')");
    }

    public void fontSize(double foreSize) {
        load("javascript:fontSize(" + foreSize + ")");
    }

    /******************** Paragraph ********************/
    public void justifyLeft() {
        load("javascript:justifyLeft()");
    }

    public void justifyRight() {
        load("javascript:justifyRight()");
    }

    public void justifyCenter() {
        load("javascript:justifyCenter()");
    }

    public void justifyFull() {
        load("javascript:justifyFull()");
    }

    public void insertOrderedList() {
        load("javascript:insertOrderedList()");
    }

    public void insertUnorderedList() {
        load("javascript:insertUnorderedList()");
    }

    public void indent() {
        load("javascript:indent()");
    }

    public void outdent() {
        load("javascript:outdent()");
    }

    public void formatPara() {
        load("javascript:formatPara()");
    }

    public void formatH1() {
        load("javascript:formatH1()");
    }

    public void formatH2() {
        load("javascript:formatH2()");
    }

    public void formatH3() {
        load("javascript:formatH3()");
    }

    public void formatH4() {
        load("javascript:formatH4()");
    }

    public void formatH5() {
        load("javascript:formatH5()");
    }

    public void formatH6() {
        load("javascript:formatH6()");
    }

    public void lineHeight(double lineHeight) {
        load("javascript:lineHeight(" + lineHeight + ")");
    }

    public void insertImageUrl(String imageUrl) {
        load("javascript:insertImageUrl('" + imageUrl + "')");
    }

    public void insertImageData(String fileName, String base64Str) {
        String imageUrl = "data:image/" + fileName.split("\\.")[1] + ";base64," + base64Str;
        load("javascript:insertImageUrl('" + imageUrl + "')");
    }

    public void insertText(String text) {
        load("javascript:insertText('" + text + "')");
    }

    public void createLink(String linkText, String linkUrl) {
        load("javascript:createLink('" + linkText + "','" + linkUrl + "')");
    }

    public void unlink() {
        load("javascript:unlink()");
    }

    public void codeView() {
        load("javascript:codeView()");
    }

    public void insertTable(int colCount, int rowCount) {
        load("javascript:insertTable('" + colCount + "x" + rowCount + "')");
    }

    public void insertHorizontalRule() {
        load("javascript:insertHorizontalRule()");
    }

    public void formatBlockquote() {
        load("javascript:formatBlock('blockquote')");
    }

    public void formatBlockCode() {
        load("javascript:formatBlock('pre')");
    }

    public void insertHtml(String html) {
        load("javascript:pasteHTML('" + html + "')");
    }

    public void refreshHtml(@NonNull RichEditorCallback callback,
        @NonNull RichEditorCallback.OnGetHtmlListener onGetHtmlListener) {
        callback.setOnGetHtmlListener(onGetHtmlListener);
        load("javascript:refreshHTML()");
    }

    private void load(String trigger) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.mWebView.evaluateJavascript(trigger, null);
        } else {
            this.mWebView.loadUrl(trigger);
        }
    }
}