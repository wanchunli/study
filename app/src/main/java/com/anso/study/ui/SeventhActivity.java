package com.anso.study.ui;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.anso.study.R;
import com.anso.study.fragment.RecyclerViewFragment;
import com.anso.study.view.ColorChangeTextView;

import java.util.ArrayList;
import java.util.List;

public class SeventhActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh);
    }

}
