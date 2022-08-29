package com.zyb;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author：Z1084
 * @description：将之前的nio模型进行优化，为了避免重复多次扫描没有消息的连接，这里引入了selector多路复用器 但是自己去写一个nio的框架，会遇到很多的问题，包括空轮询的bug导致cpu100%，以及在读写的时候，需要去切换ByteBuffer的类型，也是很麻烦的一件事。
 * 包括性能，netty帮我们实现了零拷贝等功能，我们只需要去使用就可以了。
 * @create：2022-08-26 15:43
 */
public class NioServerSelector {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.socket().bind(new InetSocketAddress(8000));
        socketChannel.configureBlocking(false);
        //创建一个多路复用器
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端启动成功");
        while (true) {
            //先阻塞，等待事件进入，第一次默认就是等待连接事件
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socket = channel.accept();
                    System.out.println("有新的连接:" + socket.getRemoteAddress().toString());
                    socket.configureBlocking(false);
                    socket.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel socket = (SocketChannel) key.channel();
                    ByteBuffer allocate = ByteBuffer.allocate(128);
                    int read = socket.read(allocate);
                    if (read > 0) {
                        System.out.println("读取到消息:" + new String(allocate.array()));
                    } else if (read == -1) {
                        System.out.println("客户端断开连接");
                        //关闭连接
                        socket.close();
                    }
                }
                iterator.remove();
            }
        }
    }
}

