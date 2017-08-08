package com.even.sample;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.even.mricheditor.ActionType;
import com.even.mricheditor.RichEditorAction;
import com.even.mricheditor.RichEditorCallback;
import com.even.sample.fragment.EditorMenuFragment;
import com.even.sample.keyboard.KeyboardHeightObserver;
import com.even.sample.keyboard.KeyboardHeightProvider;
import com.even.sample.keyboard.KeyboardUtils;

@SuppressLint("SetJavaScriptEnabled") public class RichEditorActivity extends AppCompatActivity
    implements KeyboardHeightObserver, EditorMenuFragment.OnActionClickListener {
    private WebView mWebView;
    private RelativeLayout rlAction;

    /** The keyboard height provider */
    private KeyboardHeightProvider keyboardHeightProvider;
    private boolean isKeyboardShowing;

    private RichEditorAction mRichEditorAction;
    private RichEditorCallback mRichEditorCallback;

    private EditorMenuFragment mEditorMenuFragment;

    private ImageView ivBold;
    private ImageView ivItalic;
    private ImageView ivUnderline;
    private ImageView ivStrikethrough;
    private ImageView ivSubScript;
    private ImageView ivSuperScript;
    private ImageView ivCodeView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCallBack();
        initWebView();
        initView();

        mEditorMenuFragment = new EditorMenuFragment();
        mEditorMenuFragment.setActionClickListener(this);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.rl_action, mEditorMenuFragment).commit();

        mWebView.post(new Runnable() {
            @Override public void run() {
                mRichEditorAction.insertTable(6, 6);
            }
        });
    }

    private void initCallBack() {
        mRichEditorCallback = new RichEditorCallback() {
            @Override public void updateActionStates(ActionType type, boolean isActive) {
                updateButtonStates(type, isActive);
            }
        };
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
            case CODEVIEW:
                ivCodeView.performClick();
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

        findViewById(R.id.ll_container).post(new Runnable() {
            @Override public void run() {
                keyboardHeightProvider.start();
            }
        });

        rlAction = (RelativeLayout) findViewById(R.id.rl_action);
        findViewById(R.id.iv_action).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (rlAction.getVisibility() == View.VISIBLE) {
                    rlAction.setVisibility(View.GONE);
                } else {
                    if (isKeyboardShowing) {
                        KeyboardUtils.hideSoftInput(RichEditorActivity.this);
                    }
                    rlAction.setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.iv_keyboard).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

            }
        });

        findViewById(R.id.iv_action_undo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.undo();
            }
        });

        findViewById(R.id.iv_action_redo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.redo();
            }
        });

        ivBold = (ImageView) findViewById(R.id.iv_action_bold);
        ivBold.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.bold();
            }
        });

        ivItalic = (ImageView) findViewById(R.id.iv_action_italic);
        ivItalic.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.italic();
            }
        });

        ivSubScript = (ImageView) findViewById(R.id.iv_action_subscript);
        ivSubScript.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.subscript();
            }
        });

        ivSuperScript = (ImageView) findViewById(R.id.iv_action_superscript);
        ivSuperScript.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.superscript();
            }
        });

        ivStrikethrough = (ImageView) findViewById(R.id.iv_action_strikethrough);
        ivStrikethrough.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.strikethrough();
            }
        });

        ivUnderline = (ImageView) findViewById(R.id.iv_action_underline);
        ivUnderline.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.underline();
            }
        });

        findViewById(R.id.iv_action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.formatH1();
            }
        });

        findViewById(R.id.iv_action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.formatH2();
            }
        });

        findViewById(R.id.iv_action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.formatH3();
            }
        });

        findViewById(R.id.iv_action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.formatH4();
            }
        });

        findViewById(R.id.iv_action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.formatH5();
            }
        });

        findViewById(R.id.iv_action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.formatH6();
            }
        });

        findViewById(R.id.iv_action_txt_color).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.foreColor("blue");
            }
        });

        findViewById(R.id.iv_action_txt_bg_color).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.backColor("red");
            }
        });

        findViewById(R.id.iv_action_indent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.indent();
            }
        });

        findViewById(R.id.iv_action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.outdent();
            }
        });

        findViewById(R.id.iv_action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.justifyLeft();
            }
        });

        findViewById(R.id.iv_action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.justifyRight();
            }
        });

        findViewById(R.id.iv_action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.justifyCenter();
            }
        });

        findViewById(R.id.iv_action_align_all).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.justifyFull();
            }
        });

        findViewById(R.id.iv_action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.formatBlockquote();
            }
        });

        findViewById(R.id.iv_action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.insertUnorderedList();
            }
        });

        findViewById(R.id.iv_action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.insertOrderedList();
            }
        });

        findViewById(R.id.iv_action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.insertImage(
                    "https://avatars0.githubusercontent.com/u/5581118?v=4&u=b7ea903e397678b3675e2a15b0b6d0944f6f129e&s=400");
            }
        });

        findViewById(R.id.iv_action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.createLink("Even",
                    "https://avatars0.githubusercontent.com/u/5581118?v=4&u=b7ea903e397678b3675e2a15b0b6d0944f6f129e&s=400");
            }
        });

        ivCodeView = (ImageView) findViewById(R.id.iv_action_code_view);
        ivCodeView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.codeReview();
            }
        });

        findViewById(R.id.iv_action_table).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.insertTable(3, 7);
            }
        });

        findViewById(R.id.iv_action_line_height).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.lineHeight(20);
            }
        });

        findViewById(R.id.iv_action_line).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.insertHorizontalRule();
            }
        });

        findViewById(R.id.iv_action_code_block).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mRichEditorAction.formatBlockCode();
            }
        });
    }

    @Override public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
        if (rlAction.getVisibility() == View.INVISIBLE) {
            rlAction.setVisibility(View.GONE);
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
            rlAction.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = rlAction.getLayoutParams();
            params.height = height;
            rlAction.setLayoutParams(params);
        } else if (rlAction.getVisibility() != View.VISIBLE) {
            rlAction.setVisibility(View.GONE);
        }
    }
}
