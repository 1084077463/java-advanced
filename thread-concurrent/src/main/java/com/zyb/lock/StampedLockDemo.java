package com.zyb.lock;

import java.util.concurrent.locks.StampedLock;

/**
 * @author :Z1084
 * @description :jdk1.8新引进的读写锁。
 * 在Java 8中引入了一种锁的新机制——StampedLock，它可以看成是读写锁的一个改进版本。
 * StampedLock提供了一种乐观读锁的实现，这种乐观读锁类似于无锁的操作，完全不会阻塞写线程获取写锁，
 * 从而缓解读多写少时写线程“饥饿”现象。由于StampedLock提供的乐观读锁不阻塞写线程获取读锁，
 * 当线程共享变量从主内存load到线程工作内存时，会存在数据不一致问题，所以当使用StampedLock的乐观读锁时，
 * 需要遵从如下图用例中使用的模式来确保数据的一致性。
 * @create :2021-10-29 17:38:13
 */
public class StampedLockDemo {
    private static StampedLock stampedLock = new StampedLock();
    private double x, y;

    public static void main(String[] args) {
        StampedLockDemo stampedLockDemo = new StampedLockDemo();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                stampedLockDemo.move(12.5 + finalI, 11 + finalI);
                System.out.println(stampedLockDemo.distanceFromOrigin());
            }, "").start();
        }

    }

    void move(double newX, double newY) {
        System.out.println("newX:" + newX + "-----newY:" + newY);
        long stamp = stampedLock.writeLock();
        try {
            x = newX;
            y = newY;

        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    double distanceFromOrigin() {
        long l = stampedLock.tryOptimisticRead();
        double currentX = x, currentY = y;
        if (!stampedLock.validate(l)) {
            l = stampedLock.readLock();
            try {
                currentX = x;
                currentY = y;

            } finally {
                stampedLock.unlockRead(l);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
