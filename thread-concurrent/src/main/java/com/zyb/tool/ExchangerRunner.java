package com.zyb.tool;

import java.util.concurrent.Exchanger;

/**
 * @author :Z1084
 * @description :当一个线程遇到exchange()时候，会进行阻塞，当下一个线程执行到exchange时候，交换数据。
 * @create :2021-10-19 15:37:28
 */
public class ExchangerRunner {
    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();
        for (int i = 0; i < 10; i++) {
            Integer number = i;
            new Thread(() -> {
                try {
                    Integer exchange = exchanger.exchange(number);
                    System.out.println("线程" + Thread.currentThread().getName() + ",以前的数据:" + number + ",新的数据:" + exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, number + "").start();
        }
    }
}
