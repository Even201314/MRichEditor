package com.even.sample.widget;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.even.sample.R;
import java.util.Arrays;
import java.util.List;

/**
 * Color PaletteView
 *
 * @author even.wu
 * @date 10/8/17
 */

public class ColorPaletteView extends LinearLayout {
    @BindView(R.id.ll_color_container) LinearLayout llColorContainer;

    private String selectedColor;
    private OnColorChangeListener mOnColorChangeListener;

    private List<String> mColorList =
        Arrays.asList("#000000", "#424242", "#636363", "#9C9C94", "#CEC6CE", "#EFEFEF", "#F7F7F7",
            "#FFFFFF", "#FF0000", "#FF9C00", "#FFFF00", "#00FF00", "#00FFFF", "#0000FF", "#9C00FF",
            "#FF00FF", "#F7C6CE", "#FFE7CE", "#FFEFC6", "#D6EFD6", "#CEDEE7", "#CEE7F7", "#D6D6E7",
            "#E7D6DE", "#E79C9C", "#FFC69C", "#FFE79C", "#B5D6A5", "#A5C6CE", "#9CC6EF", "#B5A5D6",
            "#D6A5BD", "#E76363", "#F7AD6B", "#FFD663", "#94BD7B", "#73A5AD", "#6BADDE", "#8C7BC6",
            "#C67BA5", "#CE0000", "#E79439", "#EFC631", "#6BA54A", "#4A7B8C", "#3984C6", "#634AA5",
            "#A54A7B", "#9C0000", "#B56308", "#BD9400", "#397B21", "#104A5A", "#085294", "#311873",
            "#731842", "#630000", "#7B3900", "#846300", "#295218", "#083139", "#003163", "#21104A",
            "#4A1031");

    public ColorPaletteView(Context context) {
        this(context, null);
    }

    public ColorPaletteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPaletteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        final View rootView =
            LayoutInflater.from(context).inflate(R.layout.view_color_palette, this, true);
        ButterKnife.bind(this, rootView);

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
            getResources().getDisplayMetrics());
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
            getResources().getDisplayMetrics());
        for (int i = 0, size = mColorList.size(); i < size; i++) {
            final RoundView roundView = new RoundView(context);
            LayoutParams params = new LinearLayout.LayoutParams(width, width);
            params.setMargins(margin, 0, margin, 0);
            roundView.setLayoutParams(params);
            final String color = mColorList.get(i);
            roundView.setTag(color);
            roundView.setBackgroundColor(Color.parseColor(color));
            roundView.setOnClickListener(v -> {
                setSelectedColor(color);
                if (mOnColorChangeListener != null) {
                    mOnColorChangeListener.onColorChange(roundView.getBackgroundColor());
                }
            });
            llColorContainer.addView(roundView);
        }
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        if (TextUtils.isEmpty(selectedColor)) {
            return;
        }

        selectedColor = selectedColor.toUpperCase();
        if (!TextUtils.isEmpty(this.selectedColor)) {
            RoundView currentSelectedView = llColorContainer.findViewWithTag(this.selectedColor);
            if (currentSelectedView != null) {
                currentSelectedView.setSelected(this.selectedColor.equalsIgnoreCase(selectedColor));
            }
        }

        this.selectedColor = selectedColor;
        if (llColorContainer.findViewWithTag(selectedColor) != null) {
            llColorContainer.findViewWithTag(selectedColor).setSelected(true);
        }
    }

    public void setOnColorChangeListener(OnColorChangeListener mOnColorChangeListener) {
        this.mOnColorChangeListener = mOnColorChangeListener;
    }

    public interface OnColorChangeListener {
        void onColorChange(String color);
    }
}
