package com.zyb;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author :Z1084
 * @description :
 * @create :2021-10-12 16:05:48
 */
public class ReentrantLockDemo {
    public static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
            System.out.println("加锁成功");
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
            System.out.println("锁以释放");
        }

    }
}
