package com.zyb.atomic;

import com.zyb.util.UnsafeInstance;
import org.openjdk.jol.info.ClassLayout;
import sun.misc.Unsafe;

/**
 * @author :Z1084
 * @description :使用cas修改实体类里面的属性
 * @create :2021-10-20 17:28:20
 */
public class AtomicStudentAgeUpdater {
    private static final Unsafe unsafe = UnsafeInstance.getUnSafeByReflex();
    private static final long valueOffset;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset(AtomicStudentAgeUpdater.class.getDeclaredField("age"));
            System.out.println(valueOffset);
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    private String name;
    private int age;

    public AtomicStudentAgeUpdater(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        AtomicStudentAgeUpdater atomicStudentAgeUpdater = new AtomicStudentAgeUpdater("zhansang", 10);
        System.out.println(ClassLayout.parseInstance(atomicStudentAgeUpdater).toPrintable());
        System.out.println(atomicStudentAgeUpdater.compareAndSwapAge(10, 20));
        System.out.println(atomicStudentAgeUpdater.getAge());
    }

    public int getAge() {
        return this.age;
    }

    public boolean compareAndSwapAge(int oldValue, int newValue) {
        return unsafe.compareAndSwapInt(this, valueOffset, oldValue, newValue);
    }
}
