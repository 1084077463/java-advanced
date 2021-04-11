package com.zyb.jvm;

import com.sun.crypto.provider.DESKeyFactory;
import sun.misc.Launcher;

import java.net.URL;

/**
 * @author :Z1084
 * @description :
 * @create :2021-04-11 21:24:50
 */
public class TestJDKClassLoader {

    public static void main(String[] args) {
        System.out.println(String.class.getClassLoader());//这个由引导类加载器加载
        System.out.println(DESKeyFactory.class.getClassLoader());//扩展类加载器加载
        System.out.println(TestJDKClassLoader.class.getClassLoader());//应用程序类加载器加载

        System.out.println();

        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();//根据源码查看，此ClassLoader为应用程序类加载器
        ClassLoader extClassLoader = appClassLoader.getParent();
        ClassLoader bootStrapClassLoader = extClassLoader.getParent();

        System.out.println("the bootstrapLoader:" + bootStrapClassLoader);//结果为null，因为bootstrapLoader为c++编写
        System.out.println("the extClassLoader:" + extClassLoader);//the extClassLoader:sun.misc.Launcher$ExtClassLoader@4b67cf4d
        System.out.println("the appClassLoader:" + appClassLoader);//the appClassLoader:sun.misc.Launcher$AppClassLoader@18b4aac2

        System.out.println();

        System.out.println("bootstrapLoader加载以下类：");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL);
        }

        System.out.println();
        System.out.println("extClassLoader加载以下类");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println();
        System.out.println("appClassLoader加载以下类：");
        System.out.println(System.getProperty("java.class.path"));
    }
}
