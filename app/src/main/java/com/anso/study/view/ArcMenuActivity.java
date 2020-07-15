package com.anso.study.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.anso.study.R;

public class ArcMenuActivity extends ViewGroup {
    private static final int POS_LEFT_TOP = 0;
    private static final int POS_RIGHT_TOP = 1;
    private static final int POS_LEFT_BOTTOM = 2;
    private static final int POS_RIGHT_BOTTOM = 3;

    private Position mPosition = Position.RIGHT_BOTTOM;
    private Status mCurrentStatus = Status.CLOSE;
    private onMenuItemClickListener onMenuItemClick;
    private View mCButton;       //主菜单按钮
    private int mRadius;         //卫星半径

    //卫星菜单位置枚举类
    public enum Position {
        LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM
    }

    //主菜单的状态
    public enum Status {
        OPEN, CLOSE
    }

    //定义一个点击点击子菜单项的回调接口
    public interface onMenuItemClickListener {
        void onItemClick(View view, int position);
    }

    //自定义的点击方法
    public void setOnMenuItemClickListener(onMenuItemClickListener onMenuItemClick) {
        this.onMenuItemClick = onMenuItemClick;
    }

    public ArcMenuActivity(Context context) {
//        super(context);
        this(context, null);
    }

    public ArcMenuActivity(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public ArcMenuActivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性的值
        TypedArray ta = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.ArcMenu, defStyleAttr, 0);

        //getInt()方法
        //参数1：所需要赋予给pos的值
        // 参数2：如果参数1无值，则取该值，就是custom:position="right_bottom"没有定义时
        int pos = ta.getInt(R.styleable.ArcMenu_position, POS_RIGHT_BOTTOM);
        switch (pos) {
            case POS_LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
        }

        //半径的默认值
        mRadius = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        //getDimension()方法
        //参数1：所需要赋予给radius的值
        // 参数2：如果参数1无值，则取该值，就是custom:radius="100"没有定义时
        mRadius = (int) ta.getDimension(R.styleable.ArcMenu_radius, TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));

        //radius输出的值为px
        Log.v("TGA", "position = " + mPosition + ",radius = " + mRadius);

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取activity_main.xml里面的View控件的个数
        // <my.com.example.x550v.view.ArcMenuActivity/>里面的
        int count = getChildCount();
        //测量child
        for (int i = 0; i < count; i++) {
            //xml文件里面控件的位置，宽，高
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            //主菜单按钮
            layoutCButton();
            //子菜单按钮
            subItemButton();
        }
    }

    //定义主菜单按钮
    private void layoutCButton() {
        //或者使用findViewById()的方法
        mCButton = getChildAt(0); //获取第一个xml文件里面的第一个View控件
        mCButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //主菜单动画
                //这里得使用getContext()来获取context
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim);
                //如果使用setAnimation()只会转一次
//                mCButton.startAnimation(animation);

                //子菜单动画
                toggleMenu(300);
            }
        });

        //l为主菜单距离父布局的左边距离，t为主菜单距离父布局的顶边距离
        int l = 0, t = 0;
        //获取主按钮的宽和高
        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();

        switch (mPosition) {
            case LEFT_TOP:
                //0,0表示坐上角的位置
                l = 0;
                t = 0;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;   //getMeasuredWidth()取得容器的宽度
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height; //getMeasuredHeight()取得容器的高度
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }
        mCButton.layout(l, t, l + width, t + height);
    }

    //定义子菜单按钮
    public void subItemButton() {
        //获取activity_main.xml里面的View控件的个数
        // <my.com.example.x550v.view.ArcMenuActivity/>里面的
        int count = getChildCount();

        for (int i = 0; i < count - 1; i++) { //去掉主菜单按钮的一个
            View child = getChildAt(i + 1);   //从第一个子菜单开始获取，而不是主菜单

            //开始时设置子菜单为隐藏
            child.setVisibility(GONE);

            //当子菜单为左上角时，cl为子菜单距离父布局的左边距离，ct为子菜单距离父布局的顶边距离
            //当子菜单为右上角时，cl为子菜单距离父布局的右边距离，ct为子菜单距离父布局的顶边距离
            //当子菜单为左下角时，cl为子菜单距离父布局的左边距离，ct为子菜单距离父布局的底边距离
            //当子菜单为右下角时，cl为子菜单距离父布局的右边距离，ct为子菜单距离父布局的底边距离
            //Math.PI的值为圆周率pai，角度为180度
            //Math.PI / 2 / (count - 2)是取出平均角，
            //*i是看子菜单拥有几个平均角
            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
//            Log.v("TAG","cl = "+cl);
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            //获取子菜单View的宽和高
            int cWidth = child.getMeasuredWidth();
            int cHeight = child.getMeasuredHeight();

            //如果子菜单在左下，右下
            if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
                ct = getMeasuredHeight() - cHeight - ct;
                Log.v("TAG","getMeasuredHeight()"+getMeasuredHeight());
            }
            //如果子菜单在右上，右下
            if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {
                cl = getMeasuredWidth() - cWidth - cl;
            }
            child.layout(cl, ct, cl + cWidth, ct + cHeight);
        }
    }

    //定义点击主菜单后子菜单出现动画
    public void toggleMenu(int duration) {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View childView = getChildAt(i + 1);
            childView.setVisibility(View.VISIBLE);

            //end 0,0
            //start
            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            int xFlag = 1;
            int yFlag = 1;

            if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
                xFlag = -1;
            }
            if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) {
                yFlag = -1;
            }

            final AnimationSet animationSet = new AnimationSet(true);

            //位移动画
            Animation tranAnim = null;
            //如果为关闭状态,点击后会散开
            if (mCurrentStatus == Status.CLOSE) {
                //子菜单一开始就是扇形排布，其坐标为(0,0)
                tranAnim = new TranslateAnimation(xFlag * cl, 0, yFlag * ct, 0);
                childView.setClickable(true);
                childView.setFocusable(true);
            }
            //否则为打开状态，点击后会收缩
            else {
                tranAnim = new TranslateAnimation(0, xFlag * cl, 0, yFlag * ct);
                childView.setClickable(false);
                childView.setFocusable(false);
            }
            tranAnim.setFillAfter(true);
            tranAnim.setDuration(duration);
            tranAnim.setStartOffset((i * 100) / count); //越是后面的子菜单，延迟越多
            tranAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mCurrentStatus == Status.CLOSE) {
                        childView.setVisibility(GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            //旋转动画
            RotateAnimation rotateAnim = new RotateAnimation(
                    0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnim.setFillAfter(true);
            rotateAnim.setDuration(duration);

            //先旋转，再位移
            //先位移，再旋转更炫
//            animationSet.addAnimation(tranAnim);
            animationSet.addAnimation(rotateAnim);
            animationSet.addAnimation(tranAnim);
            childView.startAnimation(animationSet);

            final int pos = i + 1;
            //子菜单的点击监听
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMenuItemClick != null) {
                        onMenuItemClick.onItemClick(childView, pos);
                    }
//                    menuItemAnim(pos - 1);  //子菜单点击动画
//                    changeStatus();
                }
            });
        }
        //切换菜单状态,在for()循环之外
        changeStatus();
    }

    //切换菜单状态
    private void changeStatus() {
        mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }

    //添加子菜单点击动画
    private void menuItemAnim(int pos) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            View childView = getChildAt(i + 1);
            if (i == pos) {
                childView.startAnimation(scaleBigAnimation(300));
            } else {
                childView.startAnimation(scaleSmallAnimation(300));
            }
            childView.setClickable(false);
            childView.setFocusable(false);
        }
    }

    //子菜单变大，变小动画
    private Animation scaleBigAnimation(int duration) {
        AnimationSet set = new AnimationSet(true);
        Animation scaleAnim = new ScaleAnimation(1, 2, 1, 2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        Animation alphaAnim = new AlphaAnimation(1, 0);
        set.setDuration(duration);
        set.addAnimation(scaleAnim);
        set.addAnimation(alphaAnim);
        set.setFillAfter(true);
        return set;
    }

    private Animation scaleSmallAnimation(int duration) {
        AnimationSet set = new AnimationSet(true);
        Animation scaleAnim = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        Animation alphaAnim = new AlphaAnimation(1, 0);
        set.setDuration(duration);
        set.addAnimation(scaleAnim);
        set.addAnimation(alphaAnim);
        set.setFillAfter(true);
        return set;
    }

    public boolean isOpen(){
        return mCurrentStatus == Status.OPEN;
    }

}
