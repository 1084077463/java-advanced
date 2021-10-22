package com.zyb.atomic;

import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * @author :Z1084
 * @description :使用cas修改实体类数组里面的某一个实体类。这里也是，只修改atomicReferenceArray里面的，不修改真实的。
 * this.array = Arrays.copyOf(array, array.length, Object[].class);
 * @create :2021-10-21 16:23:28
 */
public class AtomicReferenceArrayRunner {
    static Student[] students = new Student[]{new Student("zhangsan", 12), new Student("wangwu", 13)};
    private static AtomicReferenceArray<Student> atomicReferenceArray = new AtomicReferenceArray(students);

    public static void main(String[] args) {
        //修改第一个里面的student
        System.out.println(atomicReferenceArray.get(0).toString());
        atomicReferenceArray.set(0, new Student("lisi", 22));
        System.out.println(atomicReferenceArray.get(0).toString());
        System.out.println(students[0].toString());

    }
}
