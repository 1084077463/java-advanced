package com.zyb.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author :Z1084
 * @description :
 * @create :2021-10-15 16:51:44
 */
public class NumbersMain {
    public static void main(String[] args) {
        int BOUND = 10;
        //获取核心数----消费者
        int CONSUMER = Runtime.getRuntime().availableProcessors();
        int PRODUCER = 16;
        int POSITION_PILL = Integer.MAX_VALUE;
        int POSITION_PILL_PRODUCER = CONSUMER / PRODUCER;
        int MOD = CONSUMER % PRODUCER;

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(BOUND);

        for (int i = 0; i < PRODUCER; i++) {
            new Thread(new NumbersProducer(queue, POSITION_PILL, POSITION_PILL_PRODUCER), i + "").start();
        }

        for (int i = 0; i < CONSUMER; i++) {
            new Thread(new NumbersConsumer(queue, POSITION_PILL), i + "").start();
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new NumbersProducer(queue, POSITION_PILL, POSITION_PILL_PRODUCER + MOD), "toudu").start();
    }
}
