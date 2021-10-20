package com.zyb.lock;

import com.zyb.util.UnsafeInstance;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.util.concurrent.CyclicBarrier;

/**
 * @author :Z1084
 * @description :自定义使用cas来进行数据赋值
 * @create :2021-10-20 11:16:48
 */
@Slf4j
public class ThreadCasDemo {

    private static final Unsafe unsafe = UnsafeInstance.getUnSafeByReflex();
    private static final long stateOffset;
    private static ThreadCasDemo threadCasDemo = new ThreadCasDemo();

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(ThreadCasDemo.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            throw new Error();
        }
    }

    private int state = 0;

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                log.info("等待其他线程也启动:{}", Thread.currentThread().getName());
                try {
                    cyclicBarrier.await();
                    log.info("开始一起抢锁:{}", Thread.currentThread().getName());
                    if (threadCasDemo.compareAndSwapState(0, 1)) {
                        log.info("抢到锁:{}", Thread.currentThread().getName());
                    } else {
                        log.info("没抢到锁:{}", Thread.currentThread().getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }, i + "").start();
        }

    }

    public final boolean compareAndSwapState(int oldValue, int newValue) {
        return unsafe.compareAndSwapInt(this, stateOffset, oldValue, newValue);
    }
}
