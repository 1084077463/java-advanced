package com.zyb.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author :Z1084
 * @description :
 * @create :2021-10-15 16:06:51
 */
@Slf4j
public class NumbersProducer implements Runnable {
    private final int positionPill;
    private final int positionPreProducer;
    private BlockingQueue<Integer> queue;

    public NumbersProducer(BlockingQueue<Integer> queue, int positionPill, int positionPreProducer) {
        this.queue = queue;
        this.positionPill = positionPill;
        this.positionPreProducer = positionPreProducer;
    }

    @Override
    public void run() {
        try {
            generateNumbers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void generateNumbers() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            queue.put(ThreadLocalRandom.current().nextInt(100));
            log.info("潘金莲-{}号,给武大郎的泡药！", Thread.currentThread().getId());
        }

        for (int i = 0; i < positionPreProducer; i++) {
            queue.put(positionPill);
            log.info("潘金莲-{}号,往武大郎的药里放入第{}颗毒丸！", Thread.currentThread().getId(), i + 1);
        }

    }
}
