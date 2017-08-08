package com.even.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.even.mricheditor.ActionType;
import com.even.sample.R;

/**
 * Editor Menu Fragment
 * Created by even.wu on 8/8/17.
 */

public class EditorMenuFragment extends Fragment {
    private View rootView;
    private TextView tvFontSize;
    private TextView tvFontName;

    private ImageView ivBold;
    private ImageView ivItalic;
    private ImageView ivUnderline;
    private ImageView ivStrikethrough;
    private ImageView ivSubScript;
    private ImageView ivSuperScript;
    private ImageView ivCodeView;

    private OnActionClickListener mActionClickListener;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_editor_menu, null);
        initView();
        return rootView;
    }

    private void initView() {
        tvFontSize = rootView.findViewById(R.id.tv_font_size);
        tvFontSize.setText("12");
        rootView.findViewById(R.id.ll_font_size).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

            }
        });

        tvFontName = rootView.findViewById(R.id.tv_font_name);
        tvFontName.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

            }
        });

        ivBold = rootView.findViewById(R.id.iv_action_bold);
        ivBold.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.BOLD);
                }
            }
        });

        ivItalic = rootView.findViewById(R.id.iv_action_italic);
        ivItalic.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.ITALIC);
                }
            }
        });

        ivUnderline = rootView.findViewById(R.id.iv_action_underline);
        ivUnderline.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.UNDERLINE);
                }
            }
        });

        ivStrikethrough = rootView.findViewById(R.id.iv_action_strikethrough);
        ivStrikethrough.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.STRIKETHROUGH);
                }
            }
        });

        ivSubScript = rootView.findViewById(R.id.iv_action_subscript);
        ivSubScript.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.SUBSCRIPT);
                }
            }
        });

        ivSuperScript = rootView.findViewById(R.id.iv_action_superscript);
        ivSuperScript.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.SUPERSCRIPT);
                }
            }
        });

        ivCodeView = rootView.findViewById(R.id.iv_action_code_view);
        ivCodeView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.CODEVIEW);
                }
            }
        });
    }

    public void updateActionStates(ActionType type, boolean isActive) {
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

    private void updateButtonStates(final ImageView iv, final boolean isActive) {
        rootView.post(new Runnable() {
            @Override public void run() {
                if (isActive) {
                    iv.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
                } else {
                    iv.setColorFilter(ContextCompat.getColor(getContext(), R.color.tintColor));
                }
            }
        });
    }

    public interface OnActionClickListener {
        void onActionClick(ActionType type);
    }

    public OnActionClickListener getActionClickListener() {
        return mActionClickListener;
    }

    public void setActionClickListener(OnActionClickListener mActionClickListener) {
        this.mActionClickListener = mActionClickListener;
    }
}
