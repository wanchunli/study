package com.anso.study;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import retrofit2.Retrofit;

public class MyClass {

    public static void main(String[] args) {
        //静态代理
//        Message message = new Lucy();
//        Agent agent = new Agent(message);
//        agent.message();

        //动态代理
        final Alin alin = new Alin();
        Object o = Proxy.newProxyInstance(MyClass.class.getClassLoader(),
                new Class[]{Message.class, Wash.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(args.length);
                for (Object arg : args) {
                    System.out.println(arg);
                }
                return method.invoke(alin,args);
            }
        });
        Message message1 = (Message)o;
        message1.message("嘿嘿","哈哈");

    }
}
