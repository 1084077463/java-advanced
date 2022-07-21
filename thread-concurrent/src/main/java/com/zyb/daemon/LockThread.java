package com.zyb.daemon;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author：Z1084
 * @description：使用lock的方式来进行非守护线程的阻塞和等待 在使用唤醒和等待都需要使用lock获取锁然后才可以执行后续相关的操作
 * @create：2022-07-21 11:20
 */
public class LockThread {
    public Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        LockThread threadDaemon = new LockThread();
        threadDaemon.daemonThread();
    }

    /**
     * 非守护线程
     */
    public void daemonThread() throws InterruptedException {
        System.out.println("主线程开始执行");
        Thread thread = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程执行");
                System.out.println("线程等待");
                condition.await();
                System.out.println("结束等待");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }


        });
        thread.setDaemon(false);
        thread.start();
        System.out.println("主线程执行完毕");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("唤醒线程");
        lock.lock();
        condition.signal();
        lock.unlock();

    }
}
