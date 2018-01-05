package com.suse.dapi.customview.loading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.suse.dapi.customview.R;

/**
 * Created by Administrator on 2018/1/5.
 *
 *
 * 菊花的加载中
 *
 */
public class JuHuaLoadingView extends LoadingView {

    private int lineCount;
    private int lineWidth;
    private int startColor;
    private int endColor;
    private int innerRadius;


    private static final int DEFAULT_LINE_COUNT = 15;
    private static final int DEFAULT_LINE_WIDTH = 4;
    private static final int DEFAULT_START_COLOR = Color.BLACK;
    private static final int DEFAULT_END_COLOR = Color.WHITE;
    private static final int DEFAULT_INNER_RADIUS = 20;

    public JuHuaLoadingView(Context context) {
        super(context);
        lineCount = DEFAULT_LINE_COUNT;
        lineWidth = DEFAULT_LINE_WIDTH;
        startColor = DEFAULT_START_COLOR;
        endColor = DEFAULT_END_COLOR;
        innerRadius = DEFAULT_INNER_RADIUS;
    }

    public JuHuaLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
    }

    public JuHuaLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context,attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.JuHuaLoadingView);
        lineCount = arr.getInt(R.styleable.JuHuaLoadingView_JuHuaLineCount,DEFAULT_LINE_COUNT);
        lineWidth = arr.getDimensionPixelSize(R.styleable.JuHuaLoadingView_JuHuaLineWidth,DEFAULT_LINE_WIDTH);
        startColor = arr.getColor(R.styleable.JuHuaLoadingView_JuHuaLineStartColor,DEFAULT_START_COLOR);
        endColor = arr.getColor(R.styleable.JuHuaLoadingView_JuHuaLineEndColor,DEFAULT_END_COLOR);
        innerRadius = arr.getDimensionPixelSize(R.styleable.JuHuaLoadingView_JuHuaInnerRadius,DEFAULT_INNER_RADIUS);
        arr.recycle();
        Log.i("TEST",lineCount +" "+lineWidth+"  "+startColor+"  "+endColor+"  "+innerRadius);
    }

    private JuHuaDrawable loading = null;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(loading == null){
            // lineWidth, lineCount, innerRadius, startColor, endColor
            Log.i("TEST","width "+canvas.getWidth() +"  "+innerRadius);
            loading = new JuHuaDrawable(this,lineWidth,lineCount,innerRadius,startColor,endColor);
            setLoadingDrawable(loading);
        }
    }// end m
}
