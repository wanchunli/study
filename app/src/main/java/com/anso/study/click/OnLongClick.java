package com.anso.study.click;

import android.view.View;

import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@EventType(listenerType = View.OnLongClickListener.class,listenerSetter = "setOnLongClickListener")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnLongClick {
    @IdRes int[] value();
}
