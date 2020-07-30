package com.even.sample;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author even
 */
public class StartActivity extends AppCompatActivity {
    @BindView(R.id.wv_start) WebView mWebView;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        mWebView.loadUrl("https://github.com");
    }

    @OnClick(R.id.btn_rich_editor) void onClickRichEditor() {
        Intent intent = new Intent(this, RichEditorActivity.class);
        startActivity(intent);
    }
}
