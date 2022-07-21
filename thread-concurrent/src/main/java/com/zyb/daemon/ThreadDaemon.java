package com.zyb.daemon;

import java.util.concurrent.TimeUnit;

/**
 * @author：Z1084
 * @description：守护线程和非守护线程的区别 当在main方法中，开启了非守护线程，程序是不会退出的。如果设置的是守护线程则会结束主函数
 * thread.setDaemon(false);表示非守护线程，设置为true则为守护线程
 * 只要非守护线程不退出，就不会结束主函数
 * @create：2022-07-21 10:45
 */
public class ThreadDaemon {
    public static void main(String[] args) throws InterruptedException {
        ThreadDaemon threadDaemon = new ThreadDaemon();
        threadDaemon.daemonThread();
    }

    /**
     * 非守护线程
     */
    public void daemonThread() throws InterruptedException {
        System.out.println("主线程开始执行");
        Thread thread = new Thread(() -> {

            System.out.println("线程执行");
            while (true) {
                System.out.println("执行中");
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        thread.setDaemon(false);
        thread.start();
        System.out.println("主线程执行完毕");
    }
}
