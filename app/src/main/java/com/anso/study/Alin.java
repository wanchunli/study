package com.anso.study;

import android.util.Log;

public class Alin implements Message,Wash {

    @Override
    public void message(String m,String n) {
        System.out.println("提供服务2");
    }

    @Override
    public void wash() {
        System.out.println("提供服务3");
    }
}
