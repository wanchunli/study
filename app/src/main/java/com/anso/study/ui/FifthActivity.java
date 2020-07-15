package com.anso.study.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;

import com.anso.study.R;
import com.anso.study.view.ColorChangeTextView;

public class FifthActivity extends AppCompatActivity {

    ColorChangeTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        textView = findViewById(R.id.textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChange(textView);
            }
        });
    }

    private void startChange(ColorChangeTextView view) {
        ObjectAnimator.ofFloat(view, "percent", 0, 1).setDuration(10000).start();
    }
}
