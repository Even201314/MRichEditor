package com.even.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.even.mricheditor.ActionType;
import com.even.sample.R;
import com.even.sample.widget.ColorPaletteView;

/**
 * Editor Menu Fragment
 * Created by even.wu on 8/8/17.
 */

public class EditorMenuFragment extends Fragment {
    private View rootView;
    @BindView(R.id.tv_font_size) TextView tvFontSize;
    @BindView(R.id.tv_font_name) TextView tvFontName;
    @BindView(R.id.tv_font_spacing) TextView tvFontSpacing;

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

    @BindView(R.id.ll_normal) LinearLayout llNormal;
    @BindView(R.id.ll_h1) LinearLayout llH1;
    @BindView(R.id.ll_h2) LinearLayout llH2;
    @BindView(R.id.ll_h3) LinearLayout llH3;

    @BindView(R.id.cpv_font_text_color) ColorPaletteView cpvFontTextColor;
    @BindView(R.id.cpv_highlight_color) ColorPaletteView cpvHighlightColor;

    private OnActionClickListener mActionClickListener;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_editor_menu, null);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        rootView.findViewById(R.id.ll_font_size).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                openFontSettingFragment(FontSettingFragment.TYPE_SIZE);
            }
        });

        cpvFontTextColor.setOnColorChangeListener(new ColorPaletteView.OnColorChangeListener() {
            @Override public void onColorChange(String color) {
                if (mActionClickListener != null) {
                    mActionClickListener.onFontColorChange(ActionType.TEXT_COLOR, color);
                }
            }
        });

        cpvHighlightColor.setOnColorChangeListener(new ColorPaletteView.OnColorChangeListener() {
            @Override public void onColorChange(String color) {
                if (mActionClickListener != null) {
                    mActionClickListener.onFontColorChange(ActionType.HIGHLIGHT, color);
                }
            }
        });

        tvFontName.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                openFontSettingFragment(FontSettingFragment.TYPE_FONT_FAMILY);
            }
        });

        rootView.findViewById(R.id.ll_line_height).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                openFontSettingFragment(FontSettingFragment.TYPE_LINE_HGEIGHT);
            }
        });

        ivBold.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.BOLD);
                }
            }
        });

        ivItalic.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.ITALIC);
                }
            }
        });

        ivUnderline.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.UNDERLINE);
                }
            }
        });

        ivStrikethrough.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.STRIKETHROUGH);
                }
            }
        });

        ivJustifyLeft.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.JUSTIFY_LEFT);
                }
            }
        });

        ivJustifyCenter.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.JUSTIFY_CENTER);
                }
            }
        });

        ivJustifyRight.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.JUSTIFY_RIGHT);
                }
            }
        });

        ivJustifyFull.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.JUSTIFY_FULL);
                }
            }
        });

        ivSubScript.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.SUBSCRIPT);
                }
            }
        });

        ivSuperScript.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.SUPERSCRIPT);
                }
            }
        });

        ivCodeView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.CODEVIEW);
                }
            }
        });

        ivUnOrdered.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.UNORDERED);
                }
            }
        });

        ivOrdered.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.ORDERED);
                }
            }
        });

        ivIndent.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.INDENT);
                }
            }
        });

        ivOutdent.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.OUTDENT);
                }
            }
        });

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.IMAGE);
                }
            }
        });

        ivLink.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.LINK);
                }
            }
        });

        ivTable.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.TABLE);
                }
            }
        });

        ivLine.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.LINE);
                }
            }
        });

        ivBlockQuote.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.BLOCKQUOTE);
                }
            }
        });

        ivCodeBlock.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.CODE_BLOCK);
                }
            }
        });

        llNormal.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.NORMAL);
                }
            }
        });

        llH1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.H1);
                }
            }
        });

        llH2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.H2);
                }
            }
        });

        llH3.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionClick(ActionType.H3);
                }
            }
        });
    }

    private void openFontSettingFragment(final int type) {
        FontSettingFragment fontSettingFragment = new FontSettingFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(FontSettingFragment.TYPE, type);
        fontSettingFragment.setArguments(bundle);

        fontSettingFragment.setOnResultListener(new FontSettingFragment.OnResultListener() {
            @Override public void onResult(String result) {
                if (mActionClickListener != null) {
                    switch (type) {
                        case FontSettingFragment.TYPE_SIZE:
                            tvFontSize.setText(result);
                            mActionClickListener.onFontSizeChange(Double.valueOf(result));
                            break;
                        case FontSettingFragment.TYPE_LINE_HGEIGHT:
                            tvFontSpacing.setText(result);
                            mActionClickListener.onFontLineHeightChange(Double.valueOf(result));
                            break;
                        case FontSettingFragment.TYPE_FONT_FAMILY:
                            tvFontName.setText(result);
                            mActionClickListener.onFontFamilyChange(result);
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
            .add(R.id.fl_action, fontSettingFragment, FontSettingFragment.class.getName())
            .hide(EditorMenuFragment.this)
            .commit();
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
            case JUSTIFY_LEFT:
                updateButtonStates(ivJustifyLeft, isActive);
                break;
            case JUSTIFY_CENTER:
                updateButtonStates(ivJustifyCenter, isActive);
                break;
            case JUSTIFY_RIGHT:
                updateButtonStates(ivJustifyRight, isActive);
                break;
            case JUSTIFY_FULL:
                updateButtonStates(ivJustifyFull, isActive);
                break;
            case ORDERED:
                updateButtonStates(ivOrdered, isActive);
                break;
            case UNORDERED:
                updateButtonStates(ivUnOrdered, isActive);
                break;
            case CODEVIEW:
                updateButtonStates(ivCodeView, isActive);
                break;
            default:
                break;
        }
    }

    public void updateFontFamilyStates(final String font) {
        rootView.post(new Runnable() {
            @Override public void run() {
                tvFontName.setText(font);
            }
        });
    }

    public void updateFontStates(final ActionType type, final double value) {
        rootView.post(new Runnable() {
            @Override public void run() {
                switch (type) {
                    case SIZE:
                        tvFontSize.setText(String.valueOf((int) value));
                        break;
                    case LINE_HEIGHT:
                        tvFontSpacing.setText(String.valueOf(value));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void updateStyleStates(ActionType type) {
        changeStyleBackground(llNormal, type == ActionType.NORMAL);
        changeStyleBackground(llH1, type == ActionType.H1);
        changeStyleBackground(llH2, type == ActionType.H2);
        changeStyleBackground(llH3, type == ActionType.H3);
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

    private void changeStyleBackground(final View view, final boolean isSelected) {
        rootView.post(new Runnable() {
            @Override public void run() {
                if (isSelected) {
                    view.setBackgroundResource(R.drawable.round_rectangle_blue);
                } else {
                    view.setBackgroundResource(R.drawable.round_rectangle_white);
                }
            }
        });
    }

    public interface OnActionClickListener {
        void onActionClick(ActionType type);

        void onFontSizeChange(double size);

        void onFontLineHeightChange(double size);

        void onFontColorChange(ActionType type, String color);

        void onFontFamilyChange(String font);
    }

    public void setActionClickListener(OnActionClickListener mActionClickListener) {
        this.mActionClickListener = mActionClickListener;
    }
}