package com.zyb.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author :Z1084
 * @description :使用atomicStampedReference来解决aba问题,添加版本号的道理，来实现这个功能，当A再去修改的时候，发现版本号变了，那么就修改失败。
 * @create :2021-10-22 15:06:08
 */
@Slf4j
public class AtomicStampedReferenceRunner {
    public static void main(String[] args) throws InterruptedException {
        AtomicStampedReference<Integer> reference = new AtomicStampedReference(1, 0);
        Thread t0 = new Thread(() -> {
            int stamp = reference.getStamp();
            log.info("stamp:" + stamp + "");
            log.info(reference.getReference() + "");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(reference.compareAndSet(1, 2, stamp, stamp + 1) + "");
        }, "A");

        Thread t1 = new Thread(() -> {
            int stamp = reference.getStamp();
            log.info(reference.compareAndSet(1, 2, stamp, stamp + 1) + "");
            stamp = reference.getStamp();
            log.info(reference.compareAndSet(2, 1, stamp, stamp + 1) + "");
        }, "B");
        t0.start();
        Thread.sleep(300);
        t1.start();
    }
}
