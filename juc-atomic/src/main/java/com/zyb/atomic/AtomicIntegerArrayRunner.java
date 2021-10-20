package com.zyb.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author :Z1084
 * @description :修改数组int类型
 * 当修改时，修改的是原先数组的克隆，并不是改的原数组。
 * public AtomicIntegerArray(int[] array) {
 * // Visibility guaranteed by final field guarantees
 * this.array = array.clone();
 * }
 * @create :2021-10-20 16:33:47
 */
public class AtomicIntegerArrayRunner {
    public static int[] value = new int[]{1, 2};
    private static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        atomicIntegerArray.getAndAdd(0, 10);
        System.out.println(atomicIntegerArray.get(0));
        System.out.println(value[0]);
    }
}
