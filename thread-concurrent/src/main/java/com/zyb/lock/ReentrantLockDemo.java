package com.zyb.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author :Z1084
 * @description :ReentrantLock锁，同一时间只有一个线程可以进行读或者写操作
 * 同样的reentrantLock锁也为可重入锁，排他锁，独占锁。
 * @create :2021-10-20 10:26:43
 */
@Slf4j
public class ReentrantLockDemo {
    public static ReentrantLock lock = new ReentrantLock();

    public static void getLock() throws InterruptedException {
        Thread thread = Thread.currentThread();
        lock.lock();
        log.info("第一次获取锁:{}", thread.getName());
        lock.lock();
        log.info("第二次获取锁:{}", thread.getName());
        lock.unlock();
        log.info("第一次释放锁:{}", thread.getName());
        /*boolean flag = true;
        while (true) {
            if (!flag) {
                break;
            }
        }*/
        Thread.sleep(3000);
        lock.unlock();
        log.info("第二次释放锁:{}", thread.getName());

    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                getLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t0").start();

        Thread.sleep(3000);

        new Thread(() -> {
            try {
                getLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
    }
}
