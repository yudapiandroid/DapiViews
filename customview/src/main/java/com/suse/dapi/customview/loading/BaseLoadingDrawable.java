package com.suse.dapi.customview.loading;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Administrator on 2018/1/5.
 */

public abstract class BaseLoadingDrawable extends Drawable implements Animatable{

    private boolean isRun = false;
    private View attachView; // 该drawable依附的view

    private int speed;
    private long lastRefreshTime;


    /**
     *
     * speed 多久刷新一次
     *
     * @param attachView
     * @param speed
     */
    public BaseLoadingDrawable(View attachView,int speed) {
        this.attachView = attachView;
        this.speed = speed;
    }

    @Override
    public void start() {
        isRun = true;
        invalidateSelf();
    }

    @Override
    public void stop() {
        isRun = false;
    }

    @Override
    public boolean isRunning() {
        return isRun;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if(!isRunning()){
            return;
        }
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastRefreshTime > speed){
            drawSelf(canvas,current,total);
            lastRefreshTime = currentTime;
            attachView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    attachView.invalidate();
                }
            },speed);
        }else{
            attachView.postInvalidate();
        }
    } // end draw


    /**
     *
     * 绘制自己
     *
     * @param canvas
     *
     */
    public abstract void drawSelf(Canvas canvas,int current,int total);

    protected int current = 100;
    protected int total = 100;
    public synchronized void setCurrentPercent(int current,int total){
        this.current = current;
        this.total = total;
    }

    /**
     *
     * 先不处理这些函数
     *
     * @param alpha
     *
     */
    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {}
    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {}
    @Override
    public int getOpacity() {return PixelFormat.OPAQUE;}

}
