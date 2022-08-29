package com.zyb;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author：Z1084
 * @description：aio的server端,通过测试得知无论是接收客户端连接还是读取数据都是不同的线程在进行操作。
 * @create：2022-08-29 9:49
 */
public class AioTcpServer {
    public static void main(String[] args) throws Exception {
        //构建一个aioServer端
        AsynchronousServerSocketChannel socketChannel = AsynchronousServerSocketChannel
                .open()
                .bind(new InetSocketAddress(8000));
        //设置接收到连接请求的处理
        socketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel socket, Object attachment) {
                System.out.println("2--" + Thread.currentThread().getName());
                //需要加上这一句，否则可能客户端无法连接
                socketChannel.accept(attachment, this);
                try {
                    System.out.println(socket.getRemoteAddress());
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    socket.read(allocate, allocate, new CompletionHandler<Integer, Object>() {
                        @Override
                        public void completed(Integer result, Object attachment) {
                            System.out.println("3--" + Thread.currentThread().getName());
                            allocate.flip();
                            System.out.println(new String(allocate.array(), 0, result));
                            socket.write(ByteBuffer.wrap("hello aio server".getBytes()));
                        }

                        @Override
                        public void failed(Throwable exc, Object attachment) {
                            exc.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
        System.out.println("1--" + Thread.currentThread().getName());
        //这里需要进行阻塞，否则程序直接退出了
        Thread.sleep(Integer.MAX_VALUE);
    }
}
