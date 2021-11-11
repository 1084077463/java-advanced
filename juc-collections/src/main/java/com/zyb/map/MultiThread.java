package com.zyb.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author :Z1084
 * @description :
 * @create :2021-11-10 14:15:37
 */
public class MultiThread implements Runnable {
    private static Map<Integer, Integer> map = new HashMap<>(11);
    private static AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void run() {
        while (atomicInteger.get() < 1000000) {
            map.put(atomicInteger.get(), atomicInteger.get());
            atomicInteger.incrementAndGet();
        }
    }
}
