package com.anso.study.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.anso.study.R;
import com.anso.study.fragment.RecyclerViewFragment;
import com.anso.study.view.ColorChangeTextView;
import com.anso.study.view.ColorChangeTextView1;

import java.util.ArrayList;
import java.util.List;

public class FifthActivity extends AppCompatActivity {

    private static final String TAG = "Wan";
    private String[] mTitles = new String[]{"关注", "热点", "推荐", "长沙"};
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private RecyclerViewFragment[] mFragments = new RecyclerViewFragment[mTitles.length];
    private List<ColorChangeTextView1> mTabs = new ArrayList<ColorChangeTextView1>();


    ColorChangeTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        initViews();
        initDatas();
        initEvents();
    }

    private void startChange(ColorChangeTextView view) {
        ObjectAnimator.ofFloat(view, "percent", 0, 1).setDuration(10000).start();
    }

    private void initEvents() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                if (positionOffset > 0) {
                    ColorChangeTextView1 left = mTabs.get(position);
                    ColorChangeTextView1 right = mTabs.get(position + 1);

//                    left.setmDirection(ColorChangeTextView.DIRECTION_RIGHT);
//                    right.setmDirection(ColorChangeTextView.DIRECTION_LEFT);
//                    Log.v(TAG, positionOffset + "");
//                    left.setPercent(1 - positionOffset);
//                    right.setPercent(positionOffset);

                    left.setDirection(ColorChangeTextView1.DIRECTION_RIGHT);
                    right.setDirection(ColorChangeTextView1.DIRECTION_LEFT);
                    Log.v(TAG, positionOffset + "");
                    left.setProgress(1 - positionOffset);
                    right.setProgress(positionOffset);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initDatas() {

        for (int i = 0; i < mTitles.length; i++) {
            mFragments[i] = new RecyclerViewFragment();
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }

    private void initViews() {
        mViewPager = findViewById(R.id.id_viewpager);

        mTabs.add((ColorChangeTextView1) findViewById(R.id.id_tab_01));
        mTabs.add((ColorChangeTextView1) findViewById(R.id.id_tab_02));
        mTabs.add((ColorChangeTextView1) findViewById(R.id.id_tab_03));
        mTabs.add((ColorChangeTextView1) findViewById(R.id.id_tab_04));
    }
}
