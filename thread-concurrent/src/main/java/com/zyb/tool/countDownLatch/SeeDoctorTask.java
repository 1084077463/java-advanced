package com.zyb.tool.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author :Z1084
 * @description :
 * @create :2021-10-18 11:08:08
 */
public class SeeDoctorTask implements Runnable {
    private CountDownLatch countDownLatch;

    public SeeDoctorTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println("排队看医生");
            Thread.sleep(1000);
            System.out.println("看完医生了");
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
