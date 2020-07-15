package com.anso.study.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anso.study.MainActivity;
import com.anso.study.R;
import com.anso.study.view.ArcMenuActivity;

public class FourthActivity extends AppCompatActivity {


    private ArcMenuActivity mArc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        mArc = findViewById(R.id.view_arc);
        mArc.setOnMenuItemClickListener(new ArcMenuActivity.onMenuItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(FourthActivity.this,position+" : "+view.getTag(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
