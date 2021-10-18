package com.zyb.tool.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author :Z1084
 * @description :一般被成为计数器，通过使用countDownLatch来解决多线程之间的同步执行。Zookeeper分布式锁,Jmeter模拟高并发等
 * @create :2021-10-18 10:19:14
 */
public class CountDownLatchRunner {
    public static void main(String[] args) throws InterruptedException {
        long now = System.currentTimeMillis();
        //multiThread();
        //awaitThread();
        //测试多线程按顺序执行
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(new SeeDoctorTask(countDownLatch)).start();
        new Thread(new QueueTask(countDownLatch)).start();
        countDownLatch.await();
        System.out.println("over，回家 cost:" + (System.currentTimeMillis() - now));
    }

    public static void awaitThread() throws InterruptedException {
        //等待线程创建完成
        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println("线程创建完毕");
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, i + "").start();
        }
        Thread.sleep(3000);
        countDownLatch.countDown();
        System.out.println("主线程退出");
    }

    public static void multiThread() throws InterruptedException {
        //1.可以模拟多线程模式，等其他线程执行完了，主线程在退出
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("执行完了");
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }, i + "").start();
        }
        System.out.println("等待中");
        countDownLatch.await();
        System.out.println("主进程结束");

    }
}
