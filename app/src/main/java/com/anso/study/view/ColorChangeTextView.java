package com.anso.study.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.text.MeasuredText;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.anso.study.R;

public class ColorChangeTextView extends AppCompatTextView {

    private static final String TAG = "Wan";

    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_TOP = 2;
    public static final int DIRECTION_BOTTOM = 3;

    private Rect mTextBound = new Rect();
    private Paint mTextPaint;
    private int startX;
    private int startY;
    private int mTextWidth;
    private int mTextHeight;

    private String mText = "享学课堂";//成员变量
    private int mTextSize = sp2px(30);
    private int mTextColor = Color.BLACK;
    private int mTextColorChange = Color.RED;
    private float mProgress;
    private int mDirection = DIRECTION_LEFT;

    private Paint mLinePaint;

    public ColorChangeTextView(Context context) {
        this(context, null);
    }

    public ColorChangeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorChangeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttr(context,attrs);
    }

    private void initAttr(final Context context, @Nullable final AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColorChangeTextView1);
        mText = ta.getString(R.styleable.ColorChangeTextView1_text);
        mTextSize = ta.getDimensionPixelSize(
                R.styleable.ColorChangeTextView1_text_size, mTextSize);
        mTextColor = ta.getColor(
                R.styleable.ColorChangeTextView1_text_color, mTextColor);
        mTextColorChange = ta.getColor(
                R.styleable.ColorChangeTextView1_text_color_change, mTextColorChange);
        mProgress = ta.getFloat(R.styleable.ColorChangeTextView1_progress, 0);

        mDirection = ta
                .getInt(R.styleable.ColorChangeTextView1_direction, mDirection);
        ta.recycle();

    }

    private void init() {
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeWidth(3);

        //绘制边框
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(dp2px(3));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.GREEN);
    }

    public int getmDirection() {
        return mDirection;
    }

    public void setmDirection(int mDirection) {
        this.mDirection = mDirection;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //1、先测量文字
        measuredText();
        //2、测量自身
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        //3、保持测量尺寸
        setMeasuredDimension(width,height);
        startX = getMeasuredWidth() / 2 - mTextWidth / 2;
        startY = getMeasuredHeight() / 2 + (int) ((mTextPaint.descent() - mTextPaint.ascent()) / 2 - mTextPaint.descent());
    }

    //获取文字的宽和高
    private void measuredText() {
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextWidth = (int) (mTextPaint.measureText(mText)+.5f);
        mTextHeight = (int) (fontMetrics.bottom - fontMetrics.top);
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = (int) (mTextWidth + .5f) + getPaddingLeft() + getPaddingRight();
                break;
        }
        //如果是AT_MOST,不能超过父布局的尺寸
        result = (mode == MeasureSpec.AT_MOST) ? Math.min(result, size) : result;
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = (int) (mTextBound.height() + .5f) + getPaddingTop() + getPaddingBottom();
                break;
        }
        //如果是AT_MOST,不能超过父布局的尺寸
        result = (mode == MeasureSpec.AT_MOST) ? Math.min(result, size) : result;
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mDirection){
            case DIRECTION_LEFT:
                canvas.save();
                //剪裁
                canvas.clipRect(startX + (int) (mTextWidth * mProgress)
                        ,getMeasuredHeight() / 2 - mTextHeight / 2
                        ,startX + mTextWidth
                        ,getMeasuredHeight() / 2 + mTextHeight / 2);
                mTextPaint.setColor(mTextColor);
                canvas.drawText(mText, startX, startY, mTextPaint);
                canvas.restore();

                canvas.save();
                canvas.clipRect(startX
                        ,getMeasuredHeight() / 2 - mTextHeight / 2
                        ,startX + (int) (mTextWidth * mProgress)
                        ,getMeasuredHeight() / 2 + mTextHeight / 2);
                mTextPaint.setColor(mTextColorChange);
                canvas.drawText(mText, startX, startY, mTextPaint);
                canvas.restore();


                break;
            case DIRECTION_RIGHT:
                //绘制原始的文字
                canvas.save();
                //剪裁
                canvas.clipRect(startX + (int) (mTextWidth * (1-mProgress)),
                        getMeasuredHeight() / 2 - mTextHeight / 2,
                        startX + mTextWidth,
                        getMeasuredHeight() / 2 + mTextHeight / 2);
                mTextPaint.setColor(mTextColorChange);
                canvas.drawText(mText, startX, startY, mTextPaint);
                canvas.restore();
                //绘制变化的文字
                canvas.save();
                //剪裁
                canvas.clipRect(startX ,
                        getMeasuredHeight() / 2 - mTextHeight / 2,
                        startX + + (int) (mTextWidth * (1-mProgress)),
                        getMeasuredHeight() / 2 + mTextHeight / 2);
                mTextPaint.setColor(mTextColor);
                canvas.drawText(mText, startX, startY, mTextPaint);
                canvas.restore();
                break;
            case DIRECTION_TOP:
                Log.i(TAG, "DIRECTION_TOP = " + DIRECTION_TOP);
                //先绘制改变的颜色
                drawTextVertical(canvas, mTextColorChange, startY,
                        (int) (startY + mProgress * mTextHeight));
                //后绘制没改变的

                drawTextVertical(canvas, mTextColor,
                        (int) (startY + mProgress * mTextHeight), startY + mTextHeight);
                break;
            case DIRECTION_BOTTOM:
                Log.i(TAG, "DIRECTION_BOTTOM = " + DIRECTION_BOTTOM);
                //先绘制改变的颜色
                drawTextVertical(canvas, mTextColorChange,
                        (int) (startY + (1 - mProgress) * mTextHeight), startY + mTextHeight);
                //后绘制没改变的
                drawTextVertical(canvas, mTextColor, startY,
                        (int) (startY + (1 - mProgress) * mTextHeight));
                break;
            default:
                break;
        }
//        drawOriText(canvas);
//        drawNewText(canvas);
    }

    private void drawOriText(Canvas canvas) {
        canvas.save();
        int left = startX + (int) (mTextWidth * mProgress);
        int top = getMeasuredHeight() / 2 - mTextHeight / 2;
        int right = startX + mTextWidth;
        int bottom = getMeasuredHeight() / 2 + mTextHeight / 2;
        //剪裁
        canvas.clipRect(left,top,right,bottom);
        mTextPaint.setColor(mTextColor);
        canvas.drawText(mText, startX, startY, mTextPaint);
        canvas.restore();
    }

    private void drawNewText(Canvas canvas) {
        canvas.save();

        int left = startX;
        int top = getMeasuredHeight() / 2 - mTextHeight / 2;
        int right = startX + (int) (mTextWidth * mProgress);
        int bottom = getMeasuredHeight() / 2 + mTextHeight / 2;
//        mTextPaint.setColor(Color.BLUE);
//        mTextPaint.setStyle(Paint.Style.STROKE);//不填充
//        mTextPaint.setStrokeWidth(2);  //线的宽度
//        canvas.drawRect(left,top,right,bottom,mTextPaint);
        canvas.clipRect(left,top,right,bottom);
        mTextPaint.setColor(mTextColorChange);
        canvas.drawText(mText, startX, startY, mTextPaint);
        canvas.restore();
    }

    public float getPercent() {
        return mProgress;
    }

    public void setPercent(float percent) {
        this.mProgress = percent;
        invalidate();
    }

    private void drawTextHorizontal(Canvas canvas, int color, int startX, int endX) {
        mTextPaint.setColor(color);

        canvas.save();
        canvas.clipRect(startX, 0, endX, getMeasuredHeight());
        canvas.drawText(mText, startX,
                getMeasuredHeight() / 2
                        - ((mTextPaint.descent() + mTextPaint.ascent()) / 2), mTextPaint);
        canvas.restore();
    }

    private void drawTextVertical(Canvas canvas, int color, int startY, int endY) {
        mTextPaint.setColor(color);

        canvas.save();
        canvas.clipRect(0, startY, getMeasuredWidth(), endY);
        canvas.drawText(mText, startX,
                getMeasuredHeight() / 2
                        - ((mTextPaint.descent() + mTextPaint.ascent()) / 2), mTextPaint);
        canvas.restore();
    }

    static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    static int sp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, Resources.getSystem().getDisplayMetrics());
    }


}
