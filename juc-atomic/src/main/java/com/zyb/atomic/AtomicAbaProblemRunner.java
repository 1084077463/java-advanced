package com.zyb.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author :Z1084
 * @description :aba的案例问题：A有200块，B拿走了去做投资，然后B将200变成400，然后将200给A。这时A发现他的钱还是200没有变过
 * @create :2021-10-22 14:17:23
 */
@Slf4j
public class AtomicAbaProblemRunner {
    static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {
        Thread t0 = new Thread(() -> {
            int a = atomicInteger.get();
            log.info("操作线程" + Thread.currentThread().getName() + "--修改前操作数值:" + a);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //去修改值
            if (atomicInteger.compareAndSet(a, 2)) {
                log.info("将1修改成2修改成功");

            } else {
                log.info("将1修改成2修改失败");
            }
        }, "主线程");

        Thread t1 = new Thread(() -> {
            atomicInteger.incrementAndGet();
            log.info("操作线程" + Thread.currentThread().getName() + "--increase后值:" + atomicInteger.get());
            atomicInteger.decrementAndGet();
            log.info("操作线程" + Thread.currentThread().getName() + "--decrement后值:" + atomicInteger.get());
        }, "干扰线程");

        t0.start();
        Thread.sleep(500);
        t1.start();
    }
}
