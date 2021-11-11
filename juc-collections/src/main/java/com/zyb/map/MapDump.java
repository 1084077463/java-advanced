package com.zyb.map;

import java.util.HashMap;

/**
 * @author :Z1084
 * @description :
 * @create :2021-11-02 09:52:11
 */
public class MapDump {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        HashMap hashMap = new HashMap<Integer, Integer>();
        int j = 0;
        for (int i = 0; i < 9; i++) {
            hashMap.put(j, i);
            j = j + 16;
        }
    }
}
