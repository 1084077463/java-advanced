package com.zyb.daemon;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author：Z1084
 * @description：使用LockSupport来解决非守护线程一直等待的功能，不让线程退出
 * @create：2022-07-21 11:17
 */
public class LockSupportThread {
    public static void main(String[] args) throws InterruptedException {
        LockSupportThread threadDaemon = new LockSupportThread();
        threadDaemon.daemonThread();
    }


    /**
     * 非守护线程
     */
    public void daemonThread() throws InterruptedException {
        System.out.println("主线程开始执行");
        Thread thread = new Thread(() -> {
            System.out.println("开始等待");
            try {
                LockSupport.park();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("结束等待");

        });
        thread.setDaemon(false);
        thread.start();

        System.out.println("主线程执行完毕");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("唤醒线程");
        LockSupport.unpark(thread);

    }
}
