package com.anso.study;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.anso.study.click.ClickUtils;
import com.anso.study.click.OnClick;
import com.anso.study.click.OnLongClick;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.schedulers.SchedulerWhen;
import io.reactivex.rxjava3.schedulers.Schedulers;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClickUtils.onClickInject(this);
        imageview = findViewById(R.id.imageview);
    }

    private void rxShowView() {
        Observable.just("")
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Throwable {
                        Thread.sleep(2000);
                        return BitmapFactory.decodeFile("");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Bitmap bitmap) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick({R.id.button1, R.id.button2})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Toast.makeText(MainActivity.this, "点击了button1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Toast.makeText(MainActivity.this, "点击了button2", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @OnLongClick({R.id.button1, R.id.button2})
    private boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Toast.makeText(MainActivity.this, "长按了button1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Toast.makeText(MainActivity.this, "长按了button2", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
