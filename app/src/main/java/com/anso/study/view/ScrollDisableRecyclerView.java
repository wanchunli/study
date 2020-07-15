package com.anso.study.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ScrollDisableRecyclerView extends RecyclerView {

    Context context;

    public ScrollDisableRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public ScrollDisableRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollDisableRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.context = context;
        setLayoutManager(new LinearLayoutManager(context, VERTICAL, false));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(setData());
        setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }

    private List<String> setData(){
        List<String> stringList = new ArrayList<>();
        stringList.add("Banner1");
        stringList.add("Banner2");
        stringList.add("Banner3");
        stringList.add("Banner4");
        stringList.add("Banner5");
        return stringList;
    }
}
