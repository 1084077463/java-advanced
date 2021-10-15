package com.zyb.jmm;

/**
 * @author :Z1084
 * @description :cpu缓存案例，体现空间局部性，时间局部性
 * @create :2021-09-30 11:31:38
 */
public class CpuCacheDemo {
    private final static int RUNS = 10;
    private final static int DIMENSION_1 = 1024 * 1024;
    private final static int DIMENSION_2 = 6;
    private static long[][] longs;

    public static void main(String[] args) {
        //初始化数组
        longs = new long[DIMENSION_1][];
        for (int i = 0; i < DIMENSION_1; i++) {
            longs[i] = new long[DIMENSION_2];
            for (int j = 0; j < DIMENSION_2; j++) {
                longs[i][j] = 1L;
            }
        }
        long sum = 0L;
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUNS; i++) {
            for (int j = 0; j < DIMENSION_1; j++) {
                for (int k = 0; k < DIMENSION_2; k++) {
                    sum += longs[j][k];
                }
            }
        }
        System.out.println("spend time1 :" + (System.currentTimeMillis() - start));
        System.out.println("result:" + sum);
        sum = 0L;
        start = System.currentTimeMillis();
        for (int i = 0; i < RUNS; i++) {
            for (int j = 0; j < DIMENSION_2; j++) {
                for (int k = 0; k < DIMENSION_1; k++) {
                    sum += longs[k][j];
                }
            }
        }
        System.out.println("spend time2 :" + (System.currentTimeMillis() - start));
        System.out.println("result:" + sum);
    }

}
