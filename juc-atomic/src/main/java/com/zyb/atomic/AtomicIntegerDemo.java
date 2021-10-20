package com.zyb.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author :Z1084
 * @description :atomicInteger是基于cas无锁算法来实现，比传统的加锁synchronized或者Lock的效率更高
 * @create :2021-10-20 14:32:38
 */
public class AtomicIntegerDemo {
    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    atomicInteger.incrementAndGet();
                }
                countDownLatch.countDown();
            }, i + "").start();
        }
        countDownLatch.await();
        System.out.println(atomicInteger.get());

    }
}
