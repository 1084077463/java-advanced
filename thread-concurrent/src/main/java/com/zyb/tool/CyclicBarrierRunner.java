package com.zyb.tool;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author :Z1084
 * @description : 栅栏屏障，当线程到达屏障时阻塞，直到最后一个线程也到达屏障，所有的线程才能继续运行。
 * @create :2021-10-18 14:38:30
 */
public class CyclicBarrierRunner {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("集齐七龙珠了");
        });
        for (int i = 0; i < 7; i++) {
            int number = i;
            new Thread(() -> {
                System.out.println("我拿到了" + (number + 1) + "龙珠");
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                }
            }, i + "").start();
        }

        //cyclicBarrier.await();
        System.out.println("已经全部拿到了");
    }
}
