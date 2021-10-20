package com.zyb.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author :Z1084
 * @description :获取魔法类unsafe,可以使用魔法类unsafe来获取读写屏障，来保证指令不会被重排
 * @create :2021-10-19 17:47:58
 */
public class UnsafeInstance {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //Unsafe unsafe = getUnSafeByMethod();
        Unsafe unsafe = getUnSafeByReflex();
        System.out.println(unsafe);
        int a = 1;
        //在这里加上读屏障来保证指令不会重排，loadFence为读屏障，storeFence为写屏障
        unsafe.loadFence();
        unsafe.fullFence();
        int b = 0;
    }

    public static Unsafe getUnSafeByMethod() {
        //这样获取Unsafe的实体类，需要在java命令中添加java -Xbootclasspath/a:${path}   // 其中path为调用Unsafe相关方法的类所在jar包路径
        //TODO 没有尝试这种方法，需要在命令行配置该Jar包的位置
        Unsafe unsafe = Unsafe.getUnsafe();
        return unsafe;
    }

    public static Unsafe getUnSafeByReflex() {
        Field theUnsafe = null;
        try {
            theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
