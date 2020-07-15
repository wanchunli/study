package com.anso.study;

import android.util.Log;

/**
 * 真实对象
 */
public class Lucy implements Message {

    @Override
    public void message(String m,String n) {
        System.out.println("提供服务2");
    }
}
