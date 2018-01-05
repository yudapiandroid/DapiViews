package com.suse.dapi.customview.loading;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

import com.suse.dapi.customview.utils.DensityUtil;

import java.util.List;

/**
 *
 * Created by Administrator on 2018/1/5.
 *
 *  绘制菊花
 *
 */
public class JuHuaDrawable extends BaseLoadingDrawable {

    private int startColor = Color.BLACK;
    private int endColor = Color.WHITE;

    private int lineWidth = 10;// 绘制的时候的宽度
    private int lineCount = 30; // 绘制的总条数
    private int innerRadius = 100; // 内圆的半径

    private int[] colors; // 每条的颜色
    private Point[] pointStart;
    private Point[] pointEnd;
    private int speed = 70;
    private long lastRefreshTime = 0;
    private Paint paint;

    public JuHuaDrawable(View attachView,int lineWidth,int lineCount,int innerRadius,int startColor,int endColor) {
        super(attachView);
        init(lineWidth,lineCount,innerRadius,startColor,endColor);
    }


    /**
     *
     * 默认的构造方法
     *
     * @param attachView
     */
    public JuHuaDrawable(View attachView){
        super(attachView);
        init(
                DensityUtil.dip2px(attachView.getContext(),2),
                14,
                DensityUtil.dip2px(attachView.getContext(),6),
                Color.BLACK,
                Color.WHITE
        );
    }

    /**
     *
     * 计算坐标点  这个点是相对于原点的~~
     *
     */
    private void initPoint(Canvas canvas) {
        float step = (float) (Math.PI * 2 / lineCount);
        pointStart = new Point[lineCount];
        pointEnd = new Point[lineCount];

        int outerRadius = canvas.getWidth() / 2;
        for(int i=0;i < lineCount;i++){
            float g = step * i;
            float cosG = (float) Math.cos(g);
            float sinG = (float) Math.sin(g);
            pointStart[i] = new Point((int)(cosG * innerRadius),(int)(sinG * innerRadius));
            pointEnd[i] = new Point((int)(cosG * outerRadius),(int) (sinG * outerRadius));
        }
    } // end m

    private void initColor() {
        colors = new int[lineCount];
        for(int i = 0;i < colors.length; i++){
            colors[i] = getLineColorByPosition(i);
        }
    }

    /**
     *
     * 根据位置设置line的颜色
     *
     * @param i
     * @return
     *
     */
    private int getLineColorByPosition(int i) {
        int sR = Color.red(startColor);
        int sG = Color.green(startColor);
        int sB = Color.blue(startColor);

        int eR = Color.red(endColor);
        int eG = Color.green(endColor);
        int eB = Color.blue(endColor);

        float percent = ((lineCount - i) * 1.0f/ lineCount);
        int rR = getColorByPercent(sR,eR,percent);
        int rG = getColorByPercent(sG,eG,percent);
        int rB = getColorByPercent(sB,eB,percent);
        return Color.rgb(rR, rG, rB);
    }

    private int getColorByPercent(int start,int end,float percent){
        if(percent < 0){
            return 0;
        }
        if(start < end){
            return (int) ((end - start) * percent + start);
        }else{
            return (int) (start - ((start - end) * percent));
        }
    }

    private void init(int lineWidth,int lineCount,int innerRadius,int startColor,int endColor){
        this.lineWidth = lineWidth;
        this.lineCount = lineCount;
        this.innerRadius = innerRadius;
        this.startColor = startColor;
        this.endColor = endColor;
    }


    private void initBeforeDraw(Canvas canvas){
        initPaint();
        initColor();
        initPoint(canvas);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(lineWidth);
        paint.setStyle(Paint.Style.STROKE);
    }

    // 当前的帧数
    private long currentFrame = Integer.MAX_VALUE / 2;

    @Override
    public void drawSelf(Canvas canvas, int current, int total) {

        if(paint == null){
            initBeforeDraw(canvas);
        }

        if(currentFrame == 0){
            currentFrame = Integer.MAX_VALUE / 2;
        }

        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;

        for(int i=0;i < pointStart.length; i++){
            paint.setColor(colors[(int) ((currentFrame + i) % colors.length)]);
            // 绘制
            canvas.drawLine(centerX + pointStart[i].x,centerY + pointStart[i].y,centerX + pointEnd[i].x,centerY + pointEnd[i].y,paint);
        }
        if(System.currentTimeMillis() - lastRefreshTime > speed){
            lastRefreshTime = System.currentTimeMillis();
            currentFrame--;
        }
    }// end m

}
