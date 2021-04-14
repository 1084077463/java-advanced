package com.zyb.jvm;

/**
 * @author :Z1084
 * @description :测试jvm的栈内存溢出
 * 在jar启动时，设置 -Xss128k来指定栈的大小，推荐使用512k
 * @create :2021-04-14 10:52:04
 */
public class StackOverFlowTest {
    public static int count = 0;

    public static void redo() {
        count++;
        redo();
    }

    public static void main(String[] args) {
        try {
            redo();
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println(count);
        }

    }
}
