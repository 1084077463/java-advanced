package com.zyb.unsafe;

import com.zyb.util.UnsafeInstance;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

/**
 * @author :Z1084
 * @description :使用unsafe来解决加锁和解锁（synchronized）的操作
 * @create :2021-11-01 17:05:09
 */

@Slf4j
public class ObjectMonitorRunner {
    static Object object = new Object();
    static Unsafe unsafe = UnsafeInstance.getUnSafeByReflex();

    private static void methodEnter() {
        unsafe.monitorEnter(object);
    }

    private static void methodExit() {
        unsafe.monitorExit(object);
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                log.info("准备枪锁");
                //1.获取锁
                methodEnter();
                //执行业务逻辑
                log.info("执行业务逻辑");
                //2.释放锁
                methodExit();
                log.info("释放锁");
            }, i + "").start();

        }
    }
}
