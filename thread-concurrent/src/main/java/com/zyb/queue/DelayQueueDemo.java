package com.zyb.queue;

import java.util.concurrent.DelayQueue;

/**
 * @author :Z1084
 * @description :由优先级堆支持的、基于时间的调度队列，内部基于无界队列PriorityQueue实现，而无界队列基于数组的扩容实现
 * @create :2021-10-15 11:15:07
 */
public class DelayQueueDemo {
    public static void main(String[] args) {
        DelayQueue<MovieTicket> queue = new DelayQueue();
        queue.put(new MovieTicket(3000, "1号"));
        queue.put(new MovieTicket(2000, "2号"));
        queue.put(new MovieTicket(4000, "3号"));
        while (queue.size() > 0) {
            try {
                MovieTicket take = queue.take();
                System.out.println(take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
