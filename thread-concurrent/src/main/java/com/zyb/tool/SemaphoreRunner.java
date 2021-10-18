package com.zyb.tool;

import java.util.concurrent.Semaphore;

/**
 * @author :Z1084
 * @description :信号量工具类
 * @create :2021-10-18 09:58:48
 */
public class SemaphoreRunner {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("线程" + Thread.currentThread().getName() + "抢到了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("线程" + Thread.currentThread().getName() + "离开了车位");
                    semaphore.release();
                }
            }, i + "").start();
        }
    }
}
