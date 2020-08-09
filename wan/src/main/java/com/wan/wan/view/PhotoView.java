package com.wan.wan.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PhotoView extends View {

    private static final float IMAGE_WIDTH = 300;
    private Bitmap bitmap;
    private Paint paint;

    public PhotoView(Context context) {
        this(context,null);
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        
    }
}
