package com.zyb.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

/**
 * @author :Z1084
 * @description :
 * @create :2021-10-15 16:05:46
 */
@Slf4j
public class NumbersConsumer implements Runnable {
    private final int positionPill;
    private BlockingQueue<Integer> queue;

    public NumbersConsumer(BlockingQueue<Integer> queue, int positionPill) {
        this.queue = queue;
        this.positionPill = positionPill;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Integer number = queue.take();
                if (number.equals(positionPill)) {
                    return;
                }
                log.info("武大郎-{}号,喝药-编号:{}", Thread.currentThread().getId(), number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
