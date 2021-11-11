package com.zyb.unsafe;

import com.zyb.util.UnsafeInstance;
import sun.misc.Unsafe;

import java.util.concurrent.TimeUnit;

/**
 * @author :Z1084
 * @description : 使用unsafe下的park和unpark
 * unsafe.park(boolean var1, long var2),传两个参数
 * 第一个参数:如果为true，那么就是ms毫秒的底精准度等待时间。如果为false，那么就是ns纳秒精准度
 * 第二个参数：如果大于0，则等待相应时间后，自动唤醒线程。如果小于0则直接唤醒。如果等于0那么不管第一个参数是true还是false都会将线程直接挂起，直接unpark或者中断。
 * @create :2021-11-01 16:33:03
 */
public class ThreadParkerRunner {
    static Unsafe unsafe = UnsafeInstance.getUnSafeByReflex();

    public static void main(String[] args) throws InterruptedException {
        Thread t0 = new Thread(() -> {
            System.out.println("被锁了");
            //true则会实现ms定时,false则会实现ns定时。
            //unsafe.park(false, 0L);
            unsafe.park(false, -1L);
            System.out.println("释放锁了");
        }, "t0");
        t0.start();

        TimeUnit.SECONDS.sleep(5);
        unsafe.unpark(t0);
    }
}
