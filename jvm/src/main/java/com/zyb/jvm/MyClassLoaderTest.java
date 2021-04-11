package com.zyb.jvm;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @author :Z1084
 * @description :
 * @create :2021-04-11 22:00:42
 */
public class MyClassLoaderTest {
    public static void main(String[] args) throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader("F:/test1");
        Class<?> clazz = myClassLoader.findClass("com.zyb.jvm.User");
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("sout", null);
        method.invoke(obj, null);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }

    static class MyClassLoader extends ClassLoader {
        private String path;

        public MyClassLoader(String path) {
            this.path = path;
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/").concat(".class");
            FileInputStream is = new FileInputStream(path + "/" + name);
            int length = is.available();//长度
            byte[] data = new byte[length];
            is.read(data);
            is.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }
    }
}
