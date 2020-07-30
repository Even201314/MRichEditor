package com.even.sample.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.even.sample.R;

/**
 * Round View
 * Created by even.wu on 8/8/17.
 */

public class RoundView extends View {
    private Paint mPaint;
    private int backgroundColor;
    private boolean isSelected;

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.roundView);
        backgroundColor = ta.getColor(R.styleable.roundView_backgroundColor, Color.WHITE);
        ta.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(4);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(backgroundColor);
        canvas.drawCircle(width / 2, height / 2, width / 2, mPaint);

        if (isSelected) {
            mPaint.setColor(Color.WHITE);
            canvas.drawLine(5 * width / 16, height / 2, 7 * width / 16, 5 * height / 8, mPaint);
            canvas.drawLine(7 * width / 16, 5 * height / 8, 11 * width / 16, 3 * height / 8,
                mPaint);
        }
    }

    public String getBackgroundColor() {
        return String.format("#%06X", (0xFFFFFF & backgroundColor));
    }

    @Override public boolean isSelected() {
        return isSelected;
    }

    @Override public void setSelected(boolean selected) {
        isSelected = selected;

        invalidate();
    }

    @Override public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        mPaint.setColor(backgroundColor);
        invalidate();
    }
}
