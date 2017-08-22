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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.even.mricheditor.ActionType;
import com.even.sample.R;
import com.even.sample.interfaces.OnActionPerformListener;
import com.even.sample.widget.ColorPaletteView;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Editor Menu Fragment
 * Created by even.wu on 8/8/17.
 */

public class EditorMenuFragment extends Fragment {
    private View rootView;
    @BindView(R.id.tv_font_size) TextView tvFontSize;
    @BindView(R.id.tv_font_name) TextView tvFontName;
    @BindView(R.id.tv_font_spacing) TextView tvFontSpacing;
    @BindView(R.id.cpv_font_text_color) ColorPaletteView cpvFontTextColor;
    @BindView(R.id.cpv_highlight_color) ColorPaletteView cpvHighlightColor;
    @BindView(R.id.tv_font_color_auto) TextView tvHighlightNone;

    private OnActionPerformListener mActionClickListener;

    private Map<Integer, ActionType> mViewTypeMap = new HashMap<Integer, ActionType>() {
        {
            put(R.id.iv_action_bold, ActionType.BOLD);
            put(R.id.iv_action_italic, ActionType.ITALIC);
            put(R.id.iv_action_underline, ActionType.UNDERLINE);
            put(R.id.iv_action_strikethrough, ActionType.STRIKETHROUGH);
            put(R.id.iv_action_justify_left, ActionType.JUSTIFY_LEFT);
            put(R.id.iv_action_justify_center, ActionType.JUSTIFY_CENTER);
            put(R.id.iv_action_justify_right, ActionType.JUSTIFY_RIGHT);
            put(R.id.iv_action_justify_full, ActionType.JUSTIFY_FULL);
            put(R.id.iv_action_subscript, ActionType.SUBSCRIPT);
            put(R.id.iv_action_superscript, ActionType.SUPERSCRIPT);
            put(R.id.iv_action_insert_numbers, ActionType.ORDERED);
            put(R.id.iv_action_insert_bullets, ActionType.UNORDERED);
            put(R.id.iv_action_indent, ActionType.INDENT);
            put(R.id.iv_action_outdent, ActionType.OUTDENT);
            put(R.id.iv_action_code_view, ActionType.CODE_VIEW);
            put(R.id.iv_action_blockquote, ActionType.BLOCK_QUOTE);
            put(R.id.iv_action_code_block, ActionType.BLOCK_CODE);
            put(R.id.ll_normal, ActionType.NORMAL);
            put(R.id.ll_h1, ActionType.H1);
            put(R.id.ll_h2, ActionType.H2);
            put(R.id.ll_h3, ActionType.H3);
            put(R.id.ll_h4, ActionType.H4);
            put(R.id.ll_h5, ActionType.H5);
            put(R.id.ll_h6, ActionType.H6);
            put(R.id.iv_action_insert_image, ActionType.IMAGE);
            put(R.id.iv_action_insert_link, ActionType.LINK);
            put(R.id.iv_action_table, ActionType.TABLE);
            put(R.id.iv_action_line, ActionType.LINE);
        }
    };

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
                    mActionClickListener.onActionPerform(ActionType.FORE_COLOR, color);
                }
            }
        });

        cpvHighlightColor.setOnColorChangeListener(new ColorPaletteView.OnColorChangeListener() {
            @Override public void onColorChange(String color) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionPerform(ActionType.BACK_COLOR, color);
                }
            }
        });

        tvHighlightNone.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionPerform(ActionType.BACK_COLOR, "#FFFFFF");
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
    }

    @OnClick({
        R.id.iv_action_bold, R.id.iv_action_italic, R.id.iv_action_underline,
        R.id.iv_action_strikethrough, R.id.iv_action_justify_left, R.id.iv_action_justify_center,
        R.id.iv_action_justify_right, R.id.iv_action_justify_full, R.id.iv_action_subscript,
        R.id.iv_action_superscript, R.id.iv_action_insert_numbers, R.id.iv_action_insert_bullets,
        R.id.iv_action_indent, R.id.iv_action_outdent, R.id.iv_action_code_view,
        R.id.iv_action_blockquote, R.id.iv_action_code_block, R.id.ll_normal, R.id.ll_h1,
        R.id.ll_h2, R.id.ll_h3, R.id.ll_h4, R.id.ll_h5, R.id.ll_h6, R.id.iv_action_insert_image,
        R.id.iv_action_insert_link, R.id.iv_action_table, R.id.iv_action_line
    }) void onClickAction(View view) {
        if (mActionClickListener == null) {
            return;
        }

        ActionType type = mViewTypeMap.get(view.getId());
        mActionClickListener.onActionPerform(type);
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
                            mActionClickListener.onActionPerform(ActionType.SIZE, result);
                            break;
                        case FontSettingFragment.TYPE_LINE_HGEIGHT:
                            tvFontSpacing.setText(result);
                            mActionClickListener.onActionPerform(ActionType.LINE_HEIGHT, result);
                            break;
                        case FontSettingFragment.TYPE_FONT_FAMILY:
                            tvFontName.setText(result);
                            mActionClickListener.onActionPerform(ActionType.FAMILY, result);
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
        View view = null;
        for (Map.Entry<Integer, ActionType> e : mViewTypeMap.entrySet()) {
            Integer key = e.getKey();
            if (e.getValue() == type) {
                view = rootView.findViewById(key);
                break;
            }
        }
        switch (type) {
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
            case ORDERED:
            case CODE_VIEW:
            case UNORDERED:
                updateButtonStates(view, isActive);
                break;
            case NORMAL:
            case H1:
            case H2:
            case H3:
            case H4:
            case H5:
            case H6:
                changeStyleBackground(view, isActive);
                break;
            default:
                break;
        }
    }

    private void updateFontFamilyStates(final String font) {
        rootView.post(new Runnable() {
            @Override public void run() {
                tvFontName.setText(font);
            }
        });
    }

    private void updateFontStates(final ActionType type, final double value) {
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

    private void updateFontColorStates(final ActionType type, final String color) {
        rootView.post(new Runnable() {
            @Override public void run() {
                String selectedColor = rgbToHex(color);
                if (selectedColor != null) {
                    if (type == ActionType.FORE_COLOR) {
                        cpvFontTextColor.setSelectedColor(selectedColor);
                    } else if (type == ActionType.BACK_COLOR) {
                        cpvHighlightColor.setSelectedColor(selectedColor);
                    }
                }
            }
        });
    }

    public static String rgbToHex(String rgb) {
        Pattern c = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");
        Matcher m = c.matcher(rgb);
        if (m.matches()) {
            return String.format("#%02x%02x%02x", Integer.valueOf(m.group(1)),
                Integer.valueOf(m.group(2)), Integer.valueOf(m.group(3)));
        }
        return null;
    }

    private void updateButtonStates(final View view, final boolean isActive) {
        rootView.post(new Runnable() {
            @Override public void run() {
                if (isActive) {
                    ((ImageView) view).setColorFilter(
                        ContextCompat.getColor(getContext(), R.color.colorAccent));
                } else {
                    ((ImageView) view).setColorFilter(
                        ContextCompat.getColor(getContext(), R.color.tintColor));
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

    public void setActionClickListener(OnActionPerformListener mActionClickListener) {
        this.mActionClickListener = mActionClickListener;
    }

    public void updateActionStates(ActionType type, final String value) {
        switch (type) {
            case FAMILY:
                updateFontFamilyStates(value);
                break;
            case SIZE:
                updateFontStates(ActionType.SIZE, Double.valueOf(value));
                break;
            case FORE_COLOR:
            case BACK_COLOR:
                updateFontColorStates(type, value);
                break;
            case LINE_HEIGHT:
                updateFontStates(ActionType.LINE_HEIGHT, Double.valueOf(value));
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
            case NORMAL:
            case H1:
            case H2:
            case H3:
            case H4:
            case H5:
            case H6:
            case ORDERED:
            case UNORDERED:
                updateActionStates(type, Boolean.valueOf(value));
                break;
            default:
                break;
        }
    }
}
