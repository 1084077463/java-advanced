package com.zyb.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author :Z1084
 * @description :使用lockSupport让线程处于阻塞状态
 * 当线程被park住时，有两种方式来进行唤醒
 * 1.使用lockSupport.unpark来进行释放
 * 2.使用线程中断来释放
 * @create :2021-10-20 09:43:26
 */
@Slf4j
public class LockSupportDemo {
    public static void main(String[] args) throws InterruptedException {

        Thread t0 = new Thread(() -> {
            Thread thread = Thread.currentThread();
            log.info("{},开始执行", thread.getName());
            for (; ; ) {
                //自旋
                log.info("准备Park住当前线程:{}", thread.getName());
                LockSupport.park();
                System.out.println(Thread.interrupted());
                log.info("当前线程已经被唤醒了:{}", thread.getName());
            }
        }, "t0");

        t0.start();
        //主线程睡眠2秒，然后进行唤醒
        TimeUnit.SECONDS.sleep(2);
        log.info("开始唤醒线程了:{}", t0.getName());
        LockSupport.unpark(t0);
        TimeUnit.SECONDS.sleep(3);
        t0.interrupt();

    }
}
