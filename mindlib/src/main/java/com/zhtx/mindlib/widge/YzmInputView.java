package com.zhtx.mindlib.widge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;

import java.lang.reflect.Field;

/**
 * 无边框的验证码输入view
 * Created by hygo05 on 2017/5/5 14:59
 */
public class YzmInputView extends EditText {
    private Paint mPaint;
    private Paint mPaintContent;
    private Paint mPaintArc;
    private int mPadding = 1;
    private int radiusBg;
    private int PaintLastArcAnimDuration = 200;
    private int mTextSize;
    private PaintLastArcAnim mPaintLastArcAnim;
    private int textLength;
    private int maxLineSize;

    public YzmInputView(Context context) {
        this(context, null);
    }

    public YzmInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YzmInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
        // 背景
        RectF rectIn = new RectF(mPadding, mPadding, getMeasuredWidth() - mPadding, getMeasuredHeight() - mPadding);
        canvas.drawRoundRect(rectIn, radiusBg, radiusBg, mPaintContent);
        // 边框
//        RectF rect = new RectF(mPadding, mPadding, getMeasuredWidth() - mPadding, getMeasuredHeight() - mPadding);
//        mPaint.setStrokeWidth(0f);//由0.8改为1.8
//        canvas.drawRoundRect(rect, radiusBg, radiusBg, mPaint);

        float cx, cy = getMeasuredHeight() / 2;
        float half = getMeasuredWidth() / maxLineSize / 2;

        mPaint.setStrokeWidth(2.0f);
        mPaintArc.setTextSize(mTextSize);

        for (int i = 0; i < textLength; i++) {
            cx = getMeasuredWidth() * i / maxLineSize + half;
            String text = String.valueOf(getText().toString().charAt(i));
            canvas.drawText(text, cx - getFontlength(mPaintArc, text) / 2.0f, cy + getFontHeight(mPaintArc, text) / 2.0f, mPaintArc);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.toString().length();
        if (textLength <= getMaxLength()) {
            if (mPaintLastArcAnim != null) {
                clearAnimation();
                startAnimation(mPaintLastArcAnim);
            } else {
                invalidate();
            }
        }
    }

    private void initPaint() {
        maxLineSize = getMaxLength();
        mPaintLastArcAnim = new PaintLastArcAnim();
        mPaintLastArcAnim.setDuration(PaintLastArcAnimDuration);
        radiusBg = dip2px(4);
        mTextSize = dip2px(18);
        textLength = 0;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#ff5655"));

        mPaintContent = new Paint();
        mPaintContent.setAntiAlias(true);
        mPaintContent.setStyle(Paint.Style.FILL);
        mPaintContent.setColor(Color.WHITE);

        mPaintArc = new Paint();
        mPaintArc.setAntiAlias(true);
        mPaintArc.setStyle(Paint.Style.FILL);
        mPaintArc.setColor(Color.rgb(51, 51, 51));

    }

    private class PaintLastArcAnim extends Animation {
        @Override
        protected void applyTransformation(float time, Transformation t) {
            super.applyTransformation(time, t);
            postInvalidate();
        }
    }


    public int getMaxLength() {
        int length = 10;
        try {
            InputFilter[] inputFilters = getFilters();
            for (InputFilter filter : inputFilters) {
                Class<?> c = filter.getClass();
                if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
                    Field[] f = c.getDeclaredFields();
                    for (Field field : f) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            length = (Integer) field.get(filter);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;

    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public float getFontlength(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    public float getFontHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }
}
