package com.zyb.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author :Z1084
 * @description :由数组支持的有界队列
 * @create :2021-10-14 17:15:01
 */
public class ArrayBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        //ArrayBlockingQueue(int capacity, boolean fair),第一个参数设置队列的容量，第二个参数设置底层使用的ReentrantLock时公平锁还是非公平锁
        BlockingQueue<String> queue = new ArrayBlockingQueue(1);
//        addAndRemove(queue);
//        putAndTake(queue);
        offerAndPoll(queue);
    }

    public static void offerAndPoll(BlockingQueue<String> queue) throws InterruptedException {
        //offer方法，如果可以插入返回true,否则返回false
        System.out.println(queue.offer("a"));
        System.out.println(queue.offer("a"));
        //poll方法，如果有值返回数值，否则返回Null
        System.out.println(queue.poll());
        System.out.println(queue.poll());
//        offer传入时间参数，如果在规定时间内，没有插入成功，返回失败。这种在无法插入时，会等待一会。
        System.out.println(queue.offer("b", 1L, TimeUnit.SECONDS));
        //poll传入时间参数，如果在规定的时间内，没有获取到元素，返回null。这种在获取元素时，如果没有获取到，会在设置的一段时间再去获取，超过时间返回null
        System.out.println(queue.poll(2L, TimeUnit.SECONDS));
    }

    public static void putAndTake(BlockingQueue<String> queue) throws InterruptedException {
        //put方法如果队列满了，会阻塞线程，直到队列有空间去放，这里会用到ReentrantLock和condition
        new Thread(() -> {
            try {
                queue.put("a");
                queue.put("a");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "a").start();

        //take方法如果队列空了，会阻塞线程，直到队列有元素去取
        new Thread(() -> {
            try {
                System.out.println(queue.take());
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "b").start();

    }

    public static void addAndRemove(BlockingQueue<String> queue) {
        //add方法如果队列满了，在添加元素会抛出异常throw new IllegalStateException("Queue full");
        boolean a = queue.add("a");
        boolean a1 = queue.add("a");
        System.out.println(a);
        System.out.println(a1);
        //remove方法如果队列空了，在获取元素会抛出异常throw new NoSuchElementException();
        String remove = queue.remove();
        String remove1 = queue.remove();
        System.out.println(remove);
        System.out.println(remove1);
    }

}
