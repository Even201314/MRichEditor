package com.even.sample.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
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
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(backgroundColor);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth() / 2,
            mPaint);
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        mPaint.setColor(backgroundColor);
        invalidate();
    }
}
