package com.zyb.tool.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author :Z1084
 * @description :
 * @create :2021-10-18 14:00:22
 */
public class QueueTask implements Runnable {
    private CountDownLatch countDownLatch;

    public QueueTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println("排队买药");
            Thread.sleep(3000);
            System.out.println("开始买药");
            countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
