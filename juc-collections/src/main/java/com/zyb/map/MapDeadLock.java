package com.zyb.map;

/**
 * @author :Z1084
 * @description :
 * @create :2021-11-10 14:15:28
 */
public class MapDeadLock {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new MultiThread(), "t0").start();
        }
    }
}
