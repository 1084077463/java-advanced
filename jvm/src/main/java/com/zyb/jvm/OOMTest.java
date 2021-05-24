package com.zyb.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author :Z1084
 * @description :内存溢出的案例代码
 * @create :2021-05-24 17:25:04
 */
public class OOMTest {
    private static List<Object> list = new ArrayList<>();

    //-Xmx10M -Xms10M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=F:\jvm.dump
    //-Xms10M -Xmx10M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=F:\jvm.dump
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (true) {
            list.add(new User(i++, UUID.randomUUID().toString()));
            new User(j--, UUID.randomUUID().toString());
        }
    }
}
