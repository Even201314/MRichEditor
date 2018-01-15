package com.even.sample;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.even.mricheditor.ActionType;
import com.even.mricheditor.RichEditorAction;
import com.even.mricheditor.RichEditorCallback;
import com.even.mricheditor.ui.ActionImageView;
import com.even.sample.fragment.EditHyperlinkFragment;
import com.even.sample.fragment.EditTableFragment;
import com.even.sample.fragment.EditorMenuFragment;
import com.even.sample.interfaces.OnActionPerformListener;
import com.even.sample.keyboard.KeyboardHeightObserver;
import com.even.sample.keyboard.KeyboardHeightProvider;
import com.even.sample.keyboard.KeyboardUtils;
import com.even.sample.util.FileIOUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("SetJavaScriptEnabled") public class RichEditorActivity extends AppCompatActivity
    implements KeyboardHeightObserver {
    @BindView(R.id.wv_container) WebView mWebView;
    @BindView(R.id.fl_action) FrameLayout flAction;
    @BindView(R.id.ll_action_bar_container) LinearLayout llActionBarContainer;

    /** The keyboard height provider */
    private KeyboardHeightProvider keyboardHeightProvider;
    private boolean isKeyboardShowing;
    private String htmlContent = "<p>Hello World</p>";

    private RichEditorAction mRichEditorAction;
    private RichEditorCallback mRichEditorCallback;

    private EditorMenuFragment mEditorMenuFragment;

    private List<ActionType> mActionTypeList =
        Arrays.asList(ActionType.BOLD, ActionType.ITALIC, ActionType.UNDERLINE,
            ActionType.STRIKETHROUGH, ActionType.SUBSCRIPT, ActionType.SUPERSCRIPT,
            ActionType.NORMAL, ActionType.H1, ActionType.H2, ActionType.H3, ActionType.H4,
            ActionType.H5, ActionType.H6, ActionType.INDENT, ActionType.OUTDENT,
            ActionType.JUSTIFY_LEFT, ActionType.JUSTIFY_CENTER, ActionType.JUSTIFY_RIGHT,
            ActionType.JUSTIFY_FULL, ActionType.ORDERED, ActionType.UNORDERED, ActionType.LINE,
            ActionType.BLOCK_CODE, ActionType.BLOCK_QUOTE, ActionType.CODE_VIEW);

    private List<Integer> mActionTypeIconList =
        Arrays.asList(R.drawable.ic_format_bold, R.drawable.ic_format_italic,
            R.drawable.ic_format_underlined, R.drawable.ic_format_strikethrough,
            R.drawable.ic_format_subscript, R.drawable.ic_format_superscript,
            R.drawable.ic_format_para, R.drawable.ic_format_h1, R.drawable.ic_format_h2,
            R.drawable.ic_format_h3, R.drawable.ic_format_h4, R.drawable.ic_format_h5,
            R.drawable.ic_format_h6, R.drawable.ic_format_indent_decrease,
            R.drawable.ic_format_indent_increase, R.drawable.ic_format_align_left,
            R.drawable.ic_format_align_center, R.drawable.ic_format_align_right,
            R.drawable.ic_format_align_justify, R.drawable.ic_format_list_numbered,
            R.drawable.ic_format_list_bulleted, R.drawable.ic_line, R.drawable.ic_code_block,
            R.drawable.ic_format_quote, R.drawable.ic_code_review);

    private static final int REQUEST_CODE_CHOOSE = 0;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initImageLoader();
        initView();

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40,
            getResources().getDisplayMetrics());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9,
            getResources().getDisplayMetrics());
        for (int i = 0, size = mActionTypeList.size(); i < size; i++) {
            final ActionImageView actionImageView = new ActionImageView(this);
            actionImageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            actionImageView.setPadding(padding, padding, padding, padding);
            actionImageView.setActionType(mActionTypeList.get(i));
            actionImageView.setTag(mActionTypeList.get(i));
            actionImageView.setActivatedColor(R.color.colorAccent);
            actionImageView.setDeactivatedColor(R.color.tintColor);
            actionImageView.setRichEditorAction(mRichEditorAction);
            actionImageView.setBackgroundResource(R.drawable.btn_colored_material);
            actionImageView.setImageResource(mActionTypeIconList.get(i));
            actionImageView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    actionImageView.command();
                }
            });
            llActionBarContainer.addView(actionImageView);
        }

        mEditorMenuFragment = new EditorMenuFragment();
        mEditorMenuFragment.setActionClickListener(new MOnActionPerformListener(mRichEditorAction));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
            .add(R.id.fl_action, mEditorMenuFragment, EditorMenuFragment.class.getName())
            .commit();
    }

    /**
     * ImageLoader for insert Image
     */
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

    private void initView() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new CustomWebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mRichEditorCallback = new MRichEditorCallback();
        mWebView.addJavascriptInterface(mRichEditorCallback, "MRichEditor");
        mWebView.loadUrl("file:///android_asset/richEditor.html");
        mRichEditorAction = new RichEditorAction(mWebView);

        keyboardHeightProvider = new KeyboardHeightProvider(this);
        findViewById(R.id.fl_container).post(new Runnable() {
            @Override public void run() {
                keyboardHeightProvider.start();
            }
        });
    }

    private class CustomWebChromeClient extends WebChromeClient {
        @Override public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                if (!TextUtils.isEmpty(htmlContent)) {
                    mRichEditorAction.insertHtml(htmlContent);
                }
                KeyboardUtils.showSoftInput(RichEditorActivity.this);
            }
        }

        @Override public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
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

    private RichEditorCallback.OnGetHtmlListener onGetHtmlListener =
        new RichEditorCallback.OnGetHtmlListener() {
            @Override public void getHtml(String html) {
                if (TextUtils.isEmpty(html)) {
                    Toast.makeText(RichEditorActivity.this, "Empty Html String", Toast.LENGTH_SHORT)
                        .show();
                    return;
                }
                Toast.makeText(RichEditorActivity.this, html, Toast.LENGTH_SHORT).show();
            }
        };

    @OnClick(R.id.iv_get_html) void onClickGetHtml() {
        mRichEditorAction.refreshHtml(mRichEditorCallback, onGetHtmlListener);
    }

    @OnClick(R.id.iv_action_undo) void onClickUndo() {
        mRichEditorAction.undo();
    }

    @OnClick(R.id.iv_action_redo) void onClickRedo() {
        mRichEditorAction.redo();
    }

    @OnClick(R.id.iv_action_txt_color) void onClickTextColor() {
        mRichEditorAction.foreColor("blue");
    }

    @OnClick(R.id.iv_action_txt_bg_color) void onClickHighlight() {
        mRichEditorAction.backColor("red");
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

                //1.Insert the Base64 String (Base64.NO_WRAP)
                ImageItem imageItem = images.get(0);
                mRichEditorAction.insertImageData(imageItem.name,
                    encodeFileToBase64Binary(imageItem.path));

                //2.Insert the ImageUrl
                //mRichEditorAction.insertImageUrl(
                //    "https://avatars0.githubusercontent.com/u/5581118?v=4&u=b7ea903e397678b3675e2a15b0b6d0944f6f129e&s=400");
            }
        }
    }

    private static String encodeFileToBase64Binary(String filePath) {
        byte[] bytes = FileIOUtil.readFile2BytesByStream(filePath);
        byte[] encoded = Base64.encode(bytes, Base64.NO_WRAP);
        return new String(encoded);
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

    @Override public void onResume() {
        super.onResume();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
        if (flAction.getVisibility() == View.INVISIBLE) {
            flAction.setVisibility(View.GONE);
        }
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
            performInputSpaceAndDel();
        } else if (flAction.getVisibility() != View.VISIBLE) {
            flAction.setVisibility(View.GONE);
        }
    }

    //TODO not a good solution
    private void performInputSpaceAndDel() {
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    Thread.sleep(100);
                    Instrumentation instrumentation = new Instrumentation();
                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_SPACE);
                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DEL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    class MRichEditorCallback extends RichEditorCallback {

        @Override public void notifyFontStyleChange(ActionType type, final String value) {
            ActionImageView actionImageView =
                (ActionImageView) llActionBarContainer.findViewWithTag(type);
            if (actionImageView != null) {
                actionImageView.notifyFontStyleChange(type, value);
            }

            if (mEditorMenuFragment != null) {
                mEditorMenuFragment.updateActionStates(type, value);
            }
        }
    }

    public class MOnActionPerformListener implements OnActionPerformListener {
        private RichEditorAction mRichEditorAction;

        public MOnActionPerformListener(RichEditorAction mRichEditorAction) {
            this.mRichEditorAction = mRichEditorAction;
        }

        @Override public void onActionPerform(ActionType type, Object... values) {
            if (mRichEditorAction == null) {
                return;
            }

            String value = null;
            if (values != null && values.length > 0) {
                value = (String) values[0];
            }

            switch (type) {
                case SIZE:
                    mRichEditorAction.fontSize(Double.valueOf(value));
                    break;
                case LINE_HEIGHT:
                    mRichEditorAction.lineHeight(Double.valueOf(value));
                    break;
                case FORE_COLOR:
                    mRichEditorAction.foreColor(value);
                    break;
                case BACK_COLOR:
                    mRichEditorAction.backColor(value);
                    break;
                case FAMILY:
                    mRichEditorAction.fontName(value);
                    break;
                case IMAGE:
                    onClickInsertImage();
                    break;
                case LINK:
                    onClickInsertLink();
                    break;
                case TABLE:
                    onClickInsertTable();
                    break;
                case BOLD:
                case ITALIC:
                case UNDERLINE:
                case SUBSCRIPT:
                case SUPERSCRIPT:
                case STRIKETHROUGH:
                case JUSTIFY_LEFT:
                case JUSTIFY_CENTER:
                case JUSTIFY_RIGHT:
                case JUSTIFY_FULL:
                case CODE_VIEW:
                case ORDERED:
                case UNORDERED:
                case INDENT:
                case OUTDENT:
                case BLOCK_QUOTE:
                case BLOCK_CODE:
                case NORMAL:
                case H1:
                case H2:
                case H3:
                case H4:
                case H5:
                case H6:
                case LINE:
                    ActionImageView actionImageView =
                        (ActionImageView) llActionBarContainer.findViewWithTag(type);
                    if (actionImageView != null) {
                        actionImageView.performClick();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
