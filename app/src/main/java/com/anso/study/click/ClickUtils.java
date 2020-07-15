package com.anso.study.click;

import android.app.Activity;
import android.view.View;

import com.anso.study.R;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ClickUtils {

    public static void onClickInject(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        //获取类中的声明方法
        Method[] declaredMethods = aClass.getDeclaredMethods();
        //遍历所有的声明方法
        for (Method declaredMethod : declaredMethods) {
            //获取单个方法的所有注解
            Annotation[] annotations = declaredMethod.getAnnotations();
            //遍历所有的注解
            for (Annotation annotation : annotations) {
                //获取当前注解的类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //限定注解EventType.class
                if (annotationType.isAnnotationPresent(EventType.class)) {
                    //获取EventType.class的各项参数
                    EventType eventType = annotationType.getAnnotation(EventType.class);
                    Class listenerType = eventType.listenerType();
                    String listenerSetter = eventType.listenerSetter();
                    try {
                        //获取注解下的方法
                        Method method = annotationType.getDeclaredMethod("value");
                        int[] ids = (int[])method.invoke(annotation);
                        declaredMethod.setAccessible(true);
                        //declaredMethod(真实对象)需要动态代理listenerType
                        ListenerInvocationHandler invocationHandler = new ListenerInvocationHandler(declaredMethod,activity);
                        Object o = Proxy.newProxyInstance(aClass.getClassLoader(), new Class[]{listenerType}, invocationHandler);
                        for (int id : ids) {
                            View view = activity.findViewById(id);
                            //setOnClicklistener
                            Class<? extends View> aClass1 = view.getClass();
                            Method setter = aClass1.getMethod(listenerSetter, listenerType);
                            setter.invoke(view, o);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class ListenerInvocationHandler<T> implements InvocationHandler {

        private Method method;

        private T target;

        public ListenerInvocationHandler(Method method, T target) {
            this.method = method;
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return this.method.invoke(target, args);
        }
    }
}
