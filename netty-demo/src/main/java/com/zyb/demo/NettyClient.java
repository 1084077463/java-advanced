package com.zyb.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author：Z1084
 * @description：netty的tcp客户端
 * @create：2022-08-29 14:21
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //客户端需要一个线程池来处理事件
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            System.out.println("客户端启动成功");
            //同步获取到连接
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 9000)).sync();
            //对关闭的事件进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
