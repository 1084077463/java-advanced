package com.zyb.jvm;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @author :Z1084
 * @description :
 * @create :2021-04-11 22:00:42
 */
public class MyClassLoaderTest1 {
    public static void main(String[] args) throws Exception {
        MyClassLoader1 myClassLoader = new MyClassLoader1("F:/test1");
        Class<?> clazz = myClassLoader.findClass("com.zyb.jvm.User1");
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("sout", null);
        method.invoke(obj, null);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }

    static class MyClassLoader1 extends ClassLoader {
        private String path;

        public MyClassLoader1(String path) {
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

        protected Class<?> loadClass(String name, boolean resolve)
                throws ClassNotFoundException {
            synchronized (getClassLoadingLock(name)) {
                // First, check if the class has already been loaded
                Class<?> c = findLoadedClass(name);
                if (c == null) {
                    long t0 = System.nanoTime();
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    if (name.startsWith("com.zyb.jvm")) {
                        c = findClass(name);
                    } else {
                        c = this.getParent().loadClass(name);
                    }

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();

                }
                if (resolve) {
                    resolveClass(c);
                }
                return c;
            }
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
