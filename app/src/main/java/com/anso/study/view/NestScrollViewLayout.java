package com.anso.study.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.anso.study.fragment.NestedLogRecyclerView;
import com.xiangxue.common.utils.FlingHelper;

public class NestScrollViewLayout extends NestedScrollView {
    private View topView;
    private ViewGroup contentView;

    //惯性滑动
    private FlingHelper mFlingHelper;

    int totalDy = 0;
    /**
     * 用于判断RecyclerView是否在fling
     */
    boolean isStartFling = false;
    /**
     * 记录当前滑动的y轴加速度
     */
    private int velocityY = 0;

    public NestScrollViewLayout(Context context) {
        this(context, null);
        init();
    }

    public NestScrollViewLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public NestScrollViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
        init();
    }

    public NestScrollViewLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mFlingHelper = new FlingHelper(getContext());
        setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (isStartFling) {
                    totalDy = 0;
                    isStartFling = false;
                }
                if (scrollY == 0) {
                    // refreshLayout.setEnabled(true);
                }
                if (scrollY == (getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    dispatchChildFling();
                }
                //在RecyclerView fling情况下，记录当前RecyclerView在y轴的偏移
                totalDy += scrollY - oldScrollY;
            }
        });
    }

    private void dispatchChildFling() {
        if (velocityY != 0) {
            Double splineFlingDistance = mFlingHelper.getSplineFlingDistance(velocityY);
            if (splineFlingDistance > totalDy) {
                childFling(mFlingHelper.getVelocityByDistance(splineFlingDistance - Double.valueOf(totalDy)));
            }
        }
        totalDy = 0;
        velocityY = 0;
    }

    private void childFling(int velY) {
        RecyclerView childRecyclerView = getChildRecyclerView(contentView);
        if (childRecyclerView != null) {
            childRecyclerView.fling(0, velY);
        }
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        if (velocityY <= 0) {
            this.velocityY = 0;
        } else {
            isStartFling = true;
            this.velocityY = velocityY;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewGroup childAt0 = (ViewGroup) getChildAt(0);
        int childCount = childAt0.getChildCount();
        topView =  childAt0.getChildAt(0);
        contentView = (ViewGroup) childAt0.getChildAt(childCount - 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.height = getMeasuredHeight();
        contentView.setLayoutParams(layoutParams);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.i("RecyclerViewNestedLog--", getScrollY()+"::onNestedPreScroll::"+topView.getMeasuredHeight());
        // 向上滑动。若当前topview可见，需要将topview滑动至不可见
        int scrollY = getScrollY();
        int topHeight = topView.getMeasuredHeight();
        boolean headerScrollUp = dy > 0 && scrollY < topHeight;
        boolean headerScrollDown = dy < 0 && scrollY > 0 && !target.canScrollVertically(-1);
        if (headerScrollUp||headerScrollDown) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    private RecyclerView getChildRecyclerView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof RecyclerView && view.getClass() == NestedLogRecyclerView.class) {
                return (RecyclerView) viewGroup.getChildAt(i);
            } else if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                ViewGroup childRecyclerView = getChildRecyclerView((ViewGroup) viewGroup.getChildAt(i));
                if (childRecyclerView instanceof RecyclerView) {
                    return (RecyclerView) childRecyclerView;
                }
            }
            continue;
        }
        return null;
    }
}
