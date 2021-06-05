package net.jackbauer.annotation;

import java.lang.reflect.Method;

public class MyContextContatiner {
    public MyContextContatiner() {}

    private <T> T invokeAnnotation(T instance) throws IllegalAccessException {
        Method[] methods = instance.getClass().getDeclaredMethods();

        for (Method method : methods) {
            NesoyAnnotation annotation = method.getAnnotation(NesoyAnnotation.class);
            if (annotation != null) {
                System.out.println(annotation.value());
            }
        }

        return instance;
    }

    public <T> T get(Class clazz) throws IllegalAccessException, InstantiationException {
        T instance = (T) clazz.newInstance();
        instance = invokeAnnotation(instance);
        return instance;
    }
}
