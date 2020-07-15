package com.anso.study;

/**
 * 代理对象（经纪人）
 */
public class Agent implements Message {

    private final Message message;

    public Agent(Message message) {
        this.message = message;
    }

    private void before(){
        System.out.println("前置准备工作");
    }

    private void after(){
        System.out.println("服务评价");
    }

    @Override
    public void message(String m,String n) {
        before();
        message.message(m,n);
        after();
    }
}
