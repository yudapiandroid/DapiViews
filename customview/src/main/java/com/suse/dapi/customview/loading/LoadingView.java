package com.suse.dapi.customview.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2018/1/5.
 */

public class LoadingView extends View {

    private BaseLoadingDrawable drawable;
    private int current; // 当前进度
    private int total; // 总进度

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLoadingDrawable(BaseLoadingDrawable drawable){
        this.drawable = drawable;
        drawable.start();
    }

    public void setProgress(int current,int total){
        this.current = current;
        this.total = total;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(drawable != null){
            drawable.setCurrentPercent(current,total);
            drawable.draw(canvas);
        }
    }// end m

}
