package com.zyb;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author：Z1084
 * @description：这个nio的模型有一个问题，就是每次遍历，都会将我们所有的连接进行遍历。 可能遍历的时候，大部分连接都没有数据，就会造成cpu的浪费
 * @create：2022-08-26 14:05
 */
public class NioServer {
    private static List<SocketChannel> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //创建nioServer端
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //绑定端口
        socketChannel.socket().bind(new InetSocketAddress(8000));
        //设置socketChannel为非阻塞
        socketChannel.configureBlocking(false);
        System.out.println("服务端启动成功");
        while (true) {
            SocketChannel accept = socketChannel.accept();
            if (accept != null) {
                System.out.println("连接成功");
                //设置非阻塞
                accept.configureBlocking(false);
                //加入到集合中
                list.add(accept);
            }
            Iterator<SocketChannel> iterator = list.iterator();
            while (iterator.hasNext()) {
                SocketChannel channel = iterator.next();
                ByteBuffer allocate = ByteBuffer.allocate(128);
                int read = channel.read(allocate);
                if (read > 0) {
                    System.out.println("读取到的消息:" + new String(allocate.array()));
                } else if (read == -1) {
                    channel.close();
                    iterator.remove();
                    System.out.println("客户端断开连接");
                }
            }


        }

    }
}
