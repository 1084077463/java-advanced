package com.zyb.atomic;

import com.zyb.util.UnsafeInstance;
import sun.misc.Unsafe;

/**
 * @author :Z1084
 * @description :
 * @create :2021-10-20 17:28:20
 */
public class AtomicStudentAgeUpdater {
    private static final Unsafe unsafe = UnsafeInstance.getUnSafeByReflex();
    private static final long valueOffset;
    private static AtomicStudentAgeUpdater atomicStudentAgeUpdater = new AtomicStudentAgeUpdater();

    static {
        try {
            valueOffset = unsafe.objectFieldOffset(AtomicStudentAgeUpdater.class.getDeclaredField("age"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    private String name;
    private int age;

    public static void main(String[] args) {

    }

    public static boolean compareAndSwapAge(int oldValue, int newValue) {
        return unsafe.compareAndSwapInt(atomicStudentAgeUpdater, valueOffset, oldValue, newValue);
    }
}
