package com.zyb.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author :Z1084
 * @description :修改属性里面的int值
 * @create :2021-10-20 16:55:43
 */
public class AtomicIntegerFieldUpdaterRunner {
    static AtomicIntegerFieldUpdater<Student> updater = AtomicIntegerFieldUpdater.newUpdater(Student.class, "age");

    public static void main(String[] args) {
        Student student = new Student("张三", 10);
        //这里返回的是修改前的值
        System.out.println(updater.getAndIncrement(student));
        System.out.println(updater.getAndIncrement(student));
        updater.decrementAndGet(student);
        System.out.println(updater.get(student));

    }

    static class Student {
        //这里需要修改的值，必须要用public 加上volatile来修饰
        public volatile int age;
        private String name;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
