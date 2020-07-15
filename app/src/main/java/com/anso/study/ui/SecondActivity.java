package com.anso.study.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.os.Bundle;

import com.anso.study.R;
import com.anso.study.databinding.ActivitySecondBinding;
import com.anso.study.fragment.RecyclerViewFragment;
import com.anso.study.viewpager.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding secondBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_second);

        secondBinding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this, getPageFragments());
        secondBinding.viewpagerView.setAdapter(pagerAdapter);
        final String[] labels = new String[]{"linear", "scroll", "recycler"};
        new TabLayoutMediator(secondBinding.tablayout, secondBinding.viewpagerView, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(labels[position]);
            }
        }).attach();
        secondBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                secondBinding.getRoot().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        secondBinding.swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
    }

    private List<Fragment> getPageFragments() {
        List<Fragment> data = new ArrayList<>();
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        return data;
    }
}
