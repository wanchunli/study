package com.anso.study.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.os.Bundle;

import com.anso.study.R;
import com.anso.study.fragment.RecyclerViewFragment;
import com.anso.study.viewpager.MyViewPager;

public class ThirdActivity extends AppCompatActivity {

    MyViewPager myViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        myViewPager = findViewById(R.id.myviewpager);
        myViewPager.setAdapter(new CustomViewPagerAdapter(getSupportFragmentManager()));
        myViewPager.setCurrentItem(0, false);
        myViewPager.setOffscreenPageLimit(1);
    }

    static class CustomViewPagerAdapter extends FragmentStatePagerAdapter {

        public CustomViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new RecyclerViewFragment();
                case 1:
                    return new RecyclerViewFragment();
                case 2:
                    return new RecyclerViewFragment();
                case 3:
                    return new RecyclerViewFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
