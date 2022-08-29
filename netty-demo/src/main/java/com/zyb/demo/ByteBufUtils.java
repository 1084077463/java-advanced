package com.zyb.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author：Z1084
 * @description：byteBuf测试
 * @create：2022-08-29 17:00
 */
public class ByteBufUtils {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);
        System.out.println("初始化后的ByteBuf:" + buffer);

        for (int i = 0; i < 8; i++) {
            buffer.writeByte(i);
        }
        System.out.println("写完数据后的ByteBuf:" + buffer);

        for (int i = 0; i < 5; i++) {
            //使用getByte方法是不会移动读的哪个下标，不会向后移动，
            //所以当想获取数据，但是并不像移动下标时，可以使用getByte方法
            System.out.println(buffer.getByte(i));
        }
        System.out.println("使用getByte()方法后的ByteBuf:" + buffer);

        for (int i = 0; i < 5; i++) {
            //使用readByte方法是会移动读的下标，会向后移动
            //读取后，就不能在从头开始读了
            System.out.println(buffer.readByte());
        }
        System.out.println("使用readByte()方法后的ByteBuf:" + buffer);

        System.out.println("------------------------------");

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,netty!".getBytes());
        //判断是否有数据
        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();
            //转换成字符串
            System.out.println("读取到的内容:" + new String(array));
            System.out.println("读取完数据的ByteBuf:" + byteBuf);
            System.out.println("读取ByteBuf的第一位:" + byteBuf.getByte(0));//h对应的ascii是104
            int len = byteBuf.readableBytes();
            System.out.println("可读取的长度:" + len);
            for (int i = 0; i < len; i++) {
                System.out.println((char) byteBuf.getByte(i));
            }
            System.out.println(byteBuf.getCharSequence(0, 6, CharsetUtil.UTF_8));
            System.out.println(byteBuf.getCharSequence(0, 6, CharsetUtil.UTF_8));
        }
    }
}
