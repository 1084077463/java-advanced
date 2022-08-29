package com.zyb;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * @author：Z1084
 * @description：aio的tcpClinet模型
 * @create：2022-08-29 10:09
 */
public class AioTcpClient {
    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel
                .open();
        //使用get来使其同步执行
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8000)).get();

        //使用同步发送
        socketChannel.write(ByteBuffer.wrap("hello aio client".getBytes())).get();
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        Integer count = socketChannel.read(allocate).get();
        if (count != -1) {
            System.out.println(new String(allocate.array(), 0, count));
        }
    }
}
