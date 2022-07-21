package com.zyb.jmm;

/**
 * @author :Z1084
 * @description :happenBefore的原则就是
 * 只靠synchronized和volatile关键字来保证原子性、可见性以及有序性，那么编写并发程序可能会显得十分麻烦，幸运的是，从JDK 5开始，Java使用新的JSR-133内存模型，提供了happens-before 原则来辅助保证程序执行的原子性、可见性以及有序性的问题，它是判断数据是否存在竞争、线程是否安全的依据，happens-before 原则内容如下
 * 程序顺序原则，即在一个线程内必须保证语义串行性，也就是说按照代码顺序执行。
 * 锁规则 解锁(unlock)操作必然发生在后续的同一个锁的加锁(lock)之前，也就是说，如果对于一个锁解锁后，再加锁，那么加锁的动作必须在解锁动作之后(同一个锁)。
 * volatile规则 volatile变量的写，先发生于读，这保证了volatile变量的可见性，简单的理解就是，volatile变量在每次被线程访问时，都强迫从主内存中读该变量的值，而当该变量发生变化时，又会强迫将最新的值刷新到主内存，任何时刻，不同的线程总是能够看到该变量的最新值。
 * 线程启动规则 线程的start()方法先于它的每一个动作，即如果线程A在执行线程B的start方法之前修改了共享变量的值，那么当线程B执行start方法时，线程A对共享变量的修改对线程B可见
 * 传递性 A先于B ，B先于C 那么A必然先于C
 * 线程终止规则 线程的所有操作先于线程的终结，Thread.join()方法的作用是等待当前执行的线程终止。假设在线程B终止之前，修改了共享变量，线程A从线程B的join方法成功返回后，线程B对共享变量的修改将对线程A可见。
 * 线程中断规则 对线程 interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生，可以通过Thread.interrupted()方法检测线程是否中断。
 * 对象终结规则对象的构造函数执行，结束先于finalize()方法
 * @create :2021-09-29 16:59:00
 */
public class HappenBeforeDemo {
    public static volatile int r = 3;
    public static int g = 6;
    public static volatile double pai = 3.14;
    public static volatile double area;

    public static void calculate() {
        int a = r;
        int b = g;
        area = a * b * pai;
    }

    public static void main(String[] args) {
        calculate();
        System.out.println(area);
    }
}
