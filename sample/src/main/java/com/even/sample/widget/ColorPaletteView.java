package com.even.sample.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.even.sample.R;

/**
 * Color PaletteView
 * Created by even.wu on 10/8/17.
 */

public class ColorPaletteView extends LinearLayout {
    @BindView(R.id.rv_color_black) RoundView rvColorBlack;
    @BindView(R.id.rv_color_blue) RoundView rvColorBlue;
    @BindView(R.id.rv_color_green) RoundView rvColorGreen;
    @BindView(R.id.rv_color_yellow) RoundView rvColorYellow;
    @BindView(R.id.rv_color_red) RoundView rvColorRed;

    private String selectedColor;
    private OnColorChangeListener mOnColorChangeListener;

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
        View rootView =
            LayoutInflater.from(context).inflate(R.layout.view_color_palette, this, true);
        ButterKnife.bind(this, rootView);
    }

    @OnClick({
        R.id.rv_color_black, R.id.rv_color_blue, R.id.rv_color_green, R.id.rv_color_yellow,
        R.id.rv_color_red
    }) void onClickTextColor(RoundView view) {
        rvColorBlack.setSelected(view.getId() == R.id.rv_color_black);
        rvColorBlue.setSelected(view.getId() == R.id.rv_color_blue);
        rvColorGreen.setSelected(view.getId() == R.id.rv_color_green);
        rvColorYellow.setSelected(view.getId() == R.id.rv_color_yellow);
        rvColorRed.setSelected(view.getId() == R.id.rv_color_red);

        switch (view.getId()) {
            case R.id.rv_color_black:
                selectedColor = "#000000";
                break;
            case R.id.rv_color_blue:
                selectedColor = "#2196F3";
                break;
            case R.id.rv_color_green:
                selectedColor = "#4CAF50";
                break;
            case R.id.rv_color_yellow:
                selectedColor = "#FFEB3B";
                break;
            case R.id.rv_color_red:
                selectedColor = "#F44336";
                break;
        }

        if (mOnColorChangeListener != null) {
            mOnColorChangeListener.onColorChange(selectedColor);
        }
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void setOnColorChangeListener(OnColorChangeListener mOnColorChangeListener) {
        this.mOnColorChangeListener = mOnColorChangeListener;
    }

    public interface OnColorChangeListener {
        void onColorChange(String color);
    }
}
