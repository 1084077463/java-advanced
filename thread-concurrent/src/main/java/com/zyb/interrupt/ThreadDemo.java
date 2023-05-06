package com.zyb.interrupt;

/**
 * @author：Z1084
 * @description：
 * @create：2023-05-06 10:33
 */
public class ThreadDemo {
    public static void main(String[] args) {
        ThreadTest testThread = new ThreadTest();
        testThread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("开始中断");
        testThread.interrupt();
    }

    public static class ThreadTest extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("testThread");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
