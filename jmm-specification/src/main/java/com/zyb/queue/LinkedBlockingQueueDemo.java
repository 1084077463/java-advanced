package com.zyb.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author :Z1084
 * @description :基于链表式的无界队列
 * @create :2021-10-15 10:24:57
 */
public class LinkedBlockingQueueDemo {
    public static void main(String[] args) {
        //无参默认容量大小为Integer.MAX_VALUE
        BlockingQueue<String> queue = new LinkedBlockingQueue(10);
        //可以通过有参类型来设置容量
    }
}
