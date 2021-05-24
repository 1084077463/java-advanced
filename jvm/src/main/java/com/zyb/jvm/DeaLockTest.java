package com.zyb.jvm;

/**
 * @author :Z1084
 * @description :死锁案例的模板代码
 * @create :2021-05-24 17:34:05
 */
public class DeaLockTest {
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock1) {
                try {
                    System.out.println("thread1 begin");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("thread1 end");
                }
            }
        }, "A").start();

        new Thread(() -> {
            synchronized (lock2) {
                try {
                    System.out.println("thread2 begin");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("thread2 end");
                }
            }
        }, "B").start();
        System.out.println("main thread end");
    }
}
