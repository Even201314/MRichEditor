package com.even.sample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.even.mricheditor.ActionType;
import com.even.mricheditor.RichEditorAction;
import com.even.mricheditor.RichEditorCallback;
import com.even.sample.fragment.EditorMenuFragment;
import com.even.sample.keyboard.KeyboardHeightObserver;
import com.even.sample.keyboard.KeyboardHeightProvider;
import com.even.sample.keyboard.KeyboardUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import java.util.ArrayList;

@SuppressLint("SetJavaScriptEnabled") public class RichEditorActivity extends AppCompatActivity
    implements KeyboardHeightObserver, EditorMenuFragment.OnActionClickListener {
    private WebView mWebView;
    @BindView(R.id.fl_action) FrameLayout flAction;

    /** The keyboard height provider */
    private KeyboardHeightProvider keyboardHeightProvider;
    private boolean isKeyboardShowing;

    private RichEditorAction mRichEditorAction;
    private RichEditorCallback mRichEditorCallback;

    private EditorMenuFragment mEditorMenuFragment;

    @BindView(R.id.iv_action_bold) ImageView ivBold;
    @BindView(R.id.iv_action_italic) ImageView ivItalic;
    @BindView(R.id.iv_action_underline) ImageView ivUnderline;
    @BindView(R.id.iv_action_strikethrough) ImageView ivStrikethrough;

    @BindView(R.id.iv_action_justify_left) ImageView ivJustifyLeft;
    @BindView(R.id.iv_action_justify_center) ImageView ivJustifyCenter;
    @BindView(R.id.iv_action_justify_right) ImageView ivJustifyRight;
    @BindView(R.id.iv_action_justify_full) ImageView ivJustifyFull;

    @BindView(R.id.iv_action_insert_numbers) ImageView ivOrdered;
    @BindView(R.id.iv_action_insert_bullets) ImageView ivUnOrdered;

    @BindView(R.id.iv_action_indent) ImageView ivIndent;
    @BindView(R.id.iv_action_outdent) ImageView ivOutdent;

    @BindView(R.id.iv_action_subscript) ImageView ivSubScript;
    @BindView(R.id.iv_action_superscript) ImageView ivSuperScript;

    @BindView(R.id.iv_action_insert_image) ImageView ivImage;
    @BindView(R.id.iv_action_insert_link) ImageView ivLink;
    @BindView(R.id.iv_action_table) ImageView ivTable;
    @BindView(R.id.iv_action_line) ImageView ivLine;

    @BindView(R.id.iv_action_blockquote) ImageView ivBlockQuote;
    @BindView(R.id.iv_action_code_block) ImageView ivCodeBlock;

    @BindView(R.id.iv_action_code_view) ImageView ivCodeView;

    @BindView(R.id.iv_action_heading1) ImageView ivH1;
    @BindView(R.id.iv_action_heading2) ImageView ivH2;
    @BindView(R.id.iv_action_heading3) ImageView ivH3;

    private static final int REQUEST_CODE_CHOOSE = 0;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initCallBack();
        initImageLoader();
        initWebView();
        initView();

        mEditorMenuFragment = new EditorMenuFragment();
        mEditorMenuFragment.setActionClickListener(this);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
            .add(R.id.fl_action, mEditorMenuFragment, EditorMenuFragment.class.getName())
            .commit();
    }

    private void initCallBack() {
        mRichEditorCallback = new MRichEditorCallback();
    }

    private void initImageLoader() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setSaveRectangle(true);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setFocusWidth(800);
        imagePicker.setFocusHeight(800);
        imagePicker.setOutPutX(256);
        imagePicker.setOutPutY(256);
    }

    public void onChangeStyleStates(ActionType selectedStyle, ActionType deselectedStyle) {
        changeStyleBackground(selectedStyle, true);
        changeStyleBackground(deselectedStyle, false);
    }

    private void changeStyleBackground(ActionType style, boolean isSelected) {
        if (style == null) {
            return;
        }

        if (mEditorMenuFragment != null) {
            mEditorMenuFragment.updateStyleStates(style, isSelected);
        }

        switch (style) {
            case H1:
                updateButtonStates(ivH1, isSelected);
                break;
            case H2:
                updateButtonStates(ivH2, isSelected);
                break;
            case H3:
                updateButtonStates(ivH3, isSelected);
                break;
            default:
                break;
        }
    }

    @Override public void onFontSizeChange(double size) {
        mRichEditorAction.fontSize(size);
    }

    @Override public void onFontLineHeightChange(double size) {
        mRichEditorAction.lineHeight(size);
    }

    @Override public void onFontColorChange(ActionType type, String color) {
        if (type == ActionType.TEXT_COLOR) {
            mRichEditorAction.foreColor(color);
        } else if (type == ActionType.HIGHLIGHT) {
            mRichEditorAction.backColor(color);
        }
    }

    @Override public void onFontFamilyChange(String font) {
        mRichEditorAction.fontName(font);
    }

    @Override public void onActionClick(ActionType type) {
        switch (type) {
            case BOLD:
                ivBold.performClick();
                break;
            case ITALIC:
                ivItalic.performClick();
                break;
            case UNDERLINE:
                ivUnderline.performClick();
                break;
            case SUBSCRIPT:
                ivSubScript.performClick();
                break;
            case SUPERSCRIPT:
                ivSuperScript.performClick();
                break;
            case STRIKETHROUGH:
                ivStrikethrough.performClick();
                break;
            case JUSTIFY_LEFT:
                ivJustifyLeft.performClick();
                break;
            case JUSTIFY_CENTER:
                ivJustifyCenter.performClick();
                break;
            case JUSTIFY_RIGHT:
                ivJustifyRight.performClick();
                break;
            case JUSTIFY_FULL:
                ivJustifyFull.performClick();
                break;
            case CODEVIEW:
                ivCodeView.performClick();
                break;
            case ORDERED:
                ivOrdered.performClick();
                break;
            case UNORDERED:
                ivUnOrdered.performClick();
                break;
            case INDENT:
                ivIndent.performClick();
                break;
            case OUTDENT:
                ivOutdent.performClick();
                break;
            case IMAGE:
                ivImage.performClick();
                break;
            case LINK:
                ivLink.performClick();
                break;
            case TABLE:
                ivTable.performClick();
                break;
            case LINE:
                ivLine.performClick();
                break;
            case BLOCKQUOTE:
                ivBlockQuote.performClick();
                break;
            case CODE_BLOCK:
                ivCodeBlock.performClick();
                break;
            case NORMAL:
                //TODO
                break;
            case H1:
                ivH1.performClick();
                break;
            case H2:
                ivH2.performClick();
                break;
            case H3:
                ivH3.performClick();
                break;
            default:
                break;
        }
    }

    private void updateButtonStates(final ImageView iv, final boolean isActive) {
        mWebView.post(new Runnable() {
            @Override public void run() {
                if (isActive) {
                    iv.setColorFilter(
                        ContextCompat.getColor(RichEditorActivity.this, R.color.colorAccent));
                } else {
                    iv.setColorFilter(
                        ContextCompat.getColor(RichEditorActivity.this, R.color.tintColor));
                }
            }
        });
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.wv_container);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.addJavascriptInterface(mRichEditorCallback, "RichEditor");
        mWebView.loadUrl("file:///android_asset/richEditor.html");
    }

    private void initView() {
        mRichEditorAction = new RichEditorAction(mWebView);
        keyboardHeightProvider = new KeyboardHeightProvider(this);
        findViewById(R.id.fl_container).post(new Runnable() {
            @Override public void run() {
                keyboardHeightProvider.start();
            }
        });
    }

    @OnClick(R.id.iv_action) void onClickAction() {
        if (flAction.getVisibility() == View.VISIBLE) {
            flAction.setVisibility(View.GONE);
        } else {
            if (isKeyboardShowing) {
                KeyboardUtils.hideSoftInput(RichEditorActivity.this);
            }
            flAction.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.iv_keyboard) void onClickKeyboard() {

    }

    @OnClick(R.id.iv_action_undo) void onClickUndo() {
        mRichEditorAction.undo();
    }

    @OnClick(R.id.iv_action_redo) void onClickRedo() {
        mRichEditorAction.redo();
    }

    @OnClick(R.id.iv_action_bold) void onClickBold() {
        mRichEditorAction.bold();
    }

    @OnClick(R.id.iv_action_italic) void onClickItalic() {
        mRichEditorAction.italic();
    }

    @OnClick(R.id.iv_action_subscript) void onClickSubscript() {
        mRichEditorAction.subscript();
    }

    @OnClick(R.id.iv_action_superscript) void onClickSuperscript() {
        mRichEditorAction.superscript();
    }

    @OnClick(R.id.iv_action_strikethrough) void onClickStrikethrough() {
        mRichEditorAction.strikethrough();
    }

    @OnClick(R.id.iv_action_underline) void onClickUnderline() {
        mRichEditorAction.underline();
    }

    @OnClick(R.id.iv_action_heading1) void onClickH1() {
        mRichEditorAction.formatH1();
    }

    @OnClick(R.id.iv_action_heading2) void onClickH2() {
        mRichEditorAction.formatH2();
    }

    @OnClick(R.id.iv_action_heading3) void onClickH3() {
        mRichEditorAction.formatH3();
    }

    @OnClick(R.id.iv_action_heading4) void onClickH4() {
        mRichEditorAction.formatH4();
    }

    @OnClick(R.id.iv_action_heading5) void onClickH5() {
        mRichEditorAction.formatH5();
    }

    @OnClick(R.id.iv_action_heading6) void onClickH6() {
        mRichEditorAction.formatH6();
    }

    @OnClick(R.id.iv_action_txt_color) void onClickTextColor() {
        mRichEditorAction.foreColor("blue");
    }

    @OnClick(R.id.iv_action_txt_bg_color) void onClickHighlight() {
        mRichEditorAction.backColor("red");
    }

    @OnClick(R.id.iv_action_indent) void onClickIndent() {
        mRichEditorAction.indent();
    }

    @OnClick(R.id.iv_action_outdent) void onClickOutdent() {
        mRichEditorAction.outdent();
    }

    @OnClick(R.id.iv_action_justify_left) void onClickJustifyLeft() {
        mRichEditorAction.justifyLeft();
    }

    @OnClick(R.id.iv_action_justify_center) void onClickJustifyCenter() {
        mRichEditorAction.justifyCenter();
    }

    @OnClick(R.id.iv_action_justify_right) void onClickJustifyRight() {
        mRichEditorAction.justifyRight();
    }

    @OnClick(R.id.iv_action_justify_full) void onClickJustifyFull() {
        mRichEditorAction.justifyFull();
    }

    @OnClick(R.id.iv_action_insert_bullets) void onClickUnOrdered() {
        mRichEditorAction.insertUnorderedList();
    }

    @OnClick(R.id.iv_action_insert_numbers) void onClickOrdered() {
        mRichEditorAction.insertOrderedList();
    }

    @OnClick(R.id.iv_action_code_view) void onClickCodeView() {
        mRichEditorAction.codeReview();
    }

    @OnClick(R.id.iv_action_line_height) void onClickLineHeight() {
        mRichEditorAction.lineHeight(20);
    }

    @OnClick(R.id.iv_action_insert_image) void onClickInsertImage() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CHOOSE);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS
            && data != null
            && requestCode == REQUEST_CODE_CHOOSE) {
            ArrayList<ImageItem> images =
                (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null && !images.isEmpty()) {
                //TODO
                mRichEditorAction.insertImage(
                    "https://avatars0.githubusercontent.com/u/5581118?v=4&u=b7ea903e397678b3675e2a15b0b6d0944f6f129e&s=400");
            }
        }
    }

    @OnClick(R.id.iv_action_insert_link) void onClickInsertLink() {
        KeyboardUtils.hideSoftInput(RichEditorActivity.this);
        EditHyperlinkFragment fragment = new EditHyperlinkFragment();
        fragment.setOnHyperlinkListener(new EditHyperlinkFragment.OnHyperlinkListener() {
            @Override public void onHyperlinkOK(String address, String text) {
                mRichEditorAction.createLink(text, address);
            }
        });
        getSupportFragmentManager().beginTransaction()
            .add(R.id.fl_container, fragment, EditHyperlinkFragment.class.getName())
            .commit();
    }

    @OnClick(R.id.iv_action_table) void onClickInsertTable() {
        KeyboardUtils.hideSoftInput(RichEditorActivity.this);
        EditTableFragment fragment = new EditTableFragment();
        fragment.setOnTableListener(new EditTableFragment.OnTableListener() {
            @Override public void onTableOK(int rows, int cols) {
                mRichEditorAction.insertTable(rows, cols);
            }
        });
        getSupportFragmentManager().beginTransaction()
            .add(R.id.fl_container, fragment, EditHyperlinkFragment.class.getName())
            .commit();
    }

    @OnClick(R.id.iv_action_line) void onClickInsertLine() {
        mRichEditorAction.insertHorizontalRule();
    }

    @OnClick(R.id.iv_action_blockquote) void onClickBlockQuote() {
        mRichEditorAction.formatBlockquote();
    }

    @OnClick(R.id.iv_action_code_block) void onClickCodeBlock() {
        mRichEditorAction.formatBlockCode();
    }

    @Override public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
        if (flAction.getVisibility() == View.INVISIBLE) {
            flAction.setVisibility(View.GONE);
        }
    }

    @Override public void onResume() {
        super.onResume();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();
    }

    @Override public void onKeyboardHeightChanged(int height, int orientation) {
        isKeyboardShowing = height > 0;
        if (height != 0) {
            flAction.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = flAction.getLayoutParams();
            params.height = height;
            flAction.setLayoutParams(params);
        } else if (flAction.getVisibility() != View.VISIBLE) {
            flAction.setVisibility(View.GONE);
        }
    }

    class MRichEditorCallback extends RichEditorCallback {

        @Override public void notifyFontFamilyChange(String fontFamily) {
            updateFontFamilyStates(fontFamily);
        }

        @Override public void notifyJustifyChange(ActionType type) {
            updateJustifyStates(type);
        }

        @Override public void notifyFontSizeChange(double fontSize) {
            updateFontSizeStates(fontSize);
        }

        @Override public void notifyLineHeightChange(double lineHeight) {
            updateLineHeightStates(lineHeight);
        }

        @Override public void notifyBoldChange(boolean isBold) {
            updateButtonStates(ActionType.BOLD, isBold);
        }

        @Override public void notifyItalicChange(boolean isItalic) {
            updateButtonStates(ActionType.ITALIC, isItalic);
        }

        @Override public void notifyUnderlineChange(boolean isUnderline) {
            updateButtonStates(ActionType.UNDERLINE, isUnderline);
        }

        @Override public void notifySubscriptChange(boolean isSubscript) {
            updateButtonStates(ActionType.SUBSCRIPT, isSubscript);
        }

        @Override public void notifySuperscriptChange(boolean isSuperscript) {
            updateButtonStates(ActionType.SUPERSCRIPT, isSuperscript);
        }

        @Override public void notifyStrikethroughChange(boolean isStrikethrough) {
            updateButtonStates(ActionType.STRIKETHROUGH, isStrikethrough);
        }

        @Override public void notifyListStyleChange(ActionType type) {
            updateListStyleStates(type);
        }
    }

    private void updateFontFamilyStates(String font) {
        if (mEditorMenuFragment != null) {
            mEditorMenuFragment.updateFontFamilyStates(font);
        }
    }

    private void updateFontSizeStates(double fontSize) {
        if (mEditorMenuFragment != null) {
            mEditorMenuFragment.updateFontStates(ActionType.SIZE, fontSize);
        }
    }

    private void updateLineHeightStates(double lineHeightSize) {
        if (mEditorMenuFragment != null) {
            mEditorMenuFragment.updateFontStates(ActionType.LINE_HEIGHT, lineHeightSize);
        }
    }

    private void updateJustifyStates(ActionType type) {
        if (mEditorMenuFragment != null) {
            mEditorMenuFragment.updateActionStates(ActionType.JUSTIFY_LEFT,
                type == ActionType.JUSTIFY_LEFT);
            mEditorMenuFragment.updateActionStates(ActionType.JUSTIFY_CENTER,
                type == ActionType.JUSTIFY_CENTER);
            mEditorMenuFragment.updateActionStates(ActionType.JUSTIFY_RIGHT,
                type == ActionType.JUSTIFY_RIGHT);
            mEditorMenuFragment.updateActionStates(ActionType.JUSTIFY_FULL,
                type == ActionType.JUSTIFY_FULL);
        }
        updateButtonStates(ivJustifyLeft, type == ActionType.JUSTIFY_LEFT);
        updateButtonStates(ivJustifyCenter, type == ActionType.JUSTIFY_CENTER);
        updateButtonStates(ivJustifyRight, type == ActionType.JUSTIFY_RIGHT);
        updateButtonStates(ivJustifyFull, type == ActionType.JUSTIFY_FULL);
    }

    private void updateListStyleStates(ActionType type) {
        if (mEditorMenuFragment != null) {
            mEditorMenuFragment.updateActionStates(ActionType.UNORDERED,
                type == ActionType.UNORDERED);
            mEditorMenuFragment.updateActionStates(ActionType.ORDERED, type == ActionType.ORDERED);
        }
        updateButtonStates(ivUnOrdered, type == ActionType.UNORDERED);
        updateButtonStates(ivOrdered, type == ActionType.ORDERED);
    }

    public void updateButtonStates(ActionType type, boolean isActive) {
        if (mEditorMenuFragment != null) {
            mEditorMenuFragment.updateActionStates(type, isActive);
        }
        switch (type) {
            case BOLD:
                updateButtonStates(ivBold, isActive);
                break;
            case ITALIC:
                updateButtonStates(ivItalic, isActive);
                break;
            case UNDERLINE:
                updateButtonStates(ivUnderline, isActive);
                break;
            case SUBSCRIPT:
                updateButtonStates(ivSubScript, isActive);
                break;
            case SUPERSCRIPT:
                updateButtonStates(ivSuperScript, isActive);
                break;
            case STRIKETHROUGH:
                updateButtonStates(ivStrikethrough, isActive);
                break;
            case CODEVIEW:
                updateButtonStates(ivCodeView, isActive);
                break;
            default:
                break;
        }
    }
}
