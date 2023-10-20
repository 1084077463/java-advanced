package com.zyb.interrupt;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author：Z1084
 * @description：
 * @create：2023-09-17 18:03
 */
public class ThreadDemo01 {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50,
                100,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(()->{
                System.out.println("初始化线程池");
            });
        }
        Thread.sleep(1000);
        Date nowDate = DateUtil.beginOfHour(new Date());
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(()->{
                Date date = DateUtil.offsetHour(nowDate, -1).toJdkDate();
                //DateTime offset = DateUtil.date(nowDate).offset(DateField.HOUR, -1);
                DateTime dateTime = DateUtil.offsetHour(nowDate, -1);
                System.out.println(nowDate+"----"+date+"---"+1+"---"+dateTime);
            });

        }
        Date saveDate = DateUtil.beginOfMinute(new Date()).toJdkDate();
        System.out.println(saveDate);
        Thread.sleep(100000L);
    }
}
