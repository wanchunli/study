package com.anso.study.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 点比阶数多一
 */
public class FishDrawable extends Drawable {

    private static final int OTHER_ALPHA = 110;

    private Path mPath;
    private Paint mPaint;
    //鱼的重心
    private PointF middlePoint;
    //鱼的主要朝向角度
    private float fishMainAngle = 0;

    /*
    鱼的长度值
     */
    //绘制鱼头的半径
    private float HEAD_RADIUS = 100;
    //鱼身长度
    private float BODY_LENGTH = 0;
    //寻找鱼鳍起始点坐标的线长
    private float FIND_FINS_LENGTH = 0.9f * HEAD_RADIUS;
    //鱼鳍的长度
    private float FINS_LENGTH = 1.3f * HEAD_RADIUS;
    //大圆的半径
    private float BIG_RADIUS = 0.7f * HEAD_RADIUS;
    //中圆的半径
    private float MIDDLE_CIRCLE_RADIUS = 0.6f * HEAD_RADIUS;
    //小圆的半径
    private float SMALL__CIRCLE_RADIUS = 0.4f * HEAD_RADIUS;
    //寻找中圆的线长
    private float FIND_MIDDLE_CIRCLE_LENGTH = 0.4f * HEAD_RADIUS;
    //寻找小圆的线长
    private float FIND_SMALL_CIRCLE_LENGTH = 0.4f * HEAD_RADIUS;


    public FishDrawable() {
        init();
    }

    private void init() {
        mPath = new Path();
        //画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        //抗锯齿
        mPaint.setAntiAlias(true);
        //抖动
        mPaint.setDither(true);
        mPaint.setColor(Color.argb(OTHER_ALPHA, 244, 92, 71));

        //确定重心
        middlePoint = new PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS);

    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        float fishAngle = fishMainAngle;
        //鱼头的圆形坐标
        PointF headPoint = calculatePoint(middlePoint, BODY_LENGTH / 2, fishAngle);
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint);
        //右鱼鳍
        PointF rightFishPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110);
        makeFins(canvas, rightFishPoint, fishAngle, true);
        //左鱼鳍
        PointF leftFishPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle + 110);
        makeFins(canvas, leftFishPoint, fishAngle, false);

        PointF bodyBottomCenterPoint = calculatePoint(headPoint, BODY_LENGTH, fishAngle - 180);
        //画节肢1
        makeSegment(canvas, bodyBottomCenterPoint, fishAngle);
    }

    private void makeSegment(Canvas canvas, PointF bottomCenterPoint, float fishAngle) {
        //梯形上底圆的圆心
        PointF upperCenterPoint = calculatePoint(bottomCenterPoint, FIND_MIDDLE_CIRCLE_LENGTH, fishAngle - 180);

        //梯形四个点
        PointF bottomLeftPoint = calculatePoint(bottomCenterPoint, BIG_RADIUS, fishAngle + 90);
    }

    private void makeFins(Canvas canvas, PointF startPoint, float fishAngle, boolean isRight) {
        float controlAngle = 115;
        //鱼鳍的终点---二阶贝塞尔曲线的终点
        PointF endPoint = calculatePoint(startPoint, FINS_LENGTH, fishAngle - 180);
        //控制点
        PointF controlPoint = calculatePoint(startPoint, FINS_LENGTH * 1.8f,
                isRight ? fishAngle - controlAngle : fishAngle + controlAngle);
        //绘制
        mPath.reset();
        //将画笔移动到起始点
        mPath.moveTo(startPoint.x, startPoint.y);
        //二阶贝塞尔曲线
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * @param startPoint 起始点坐标
     * @param length     要求的点到起始点的直线距离--线长
     * @param angle      鱼当前的朝向角度
     * @return
     */
    public PointF calculatePoint(PointF startPoint, float length, float angle) {
        //x坐标
        float deltaX = (float) Math.cos(Math.toRadians(angle)) * length;
        //y坐标
        float deltaY = (float) Math.sin(Math.toRadians(angle - 180)) * length;
        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (8.38f * HEAD_RADIUS);
    }
}
