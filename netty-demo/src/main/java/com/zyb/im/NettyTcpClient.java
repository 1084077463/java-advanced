package com.zyb.im;

import com.zyb.im.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author：Z1084
 * @description：
 * @create：2022-08-30 14:32
 */
public class NettyTcpClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());

                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000)).sync();
            Channel channel = channelFuture.channel();
            //创建一个扫描器,用来读取控制台写入的数据
            /*Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg);
            }*/
            //当突然频繁的发送数据，就会造成粘包的问题
            for (int i = 0; i < 100; i++) {
                channel.writeAndFlush("hello i'm client!" + i + "\n");
                // TimeUnit.SECONDS.sleep(1);
            }
            //监听关闭的通知消息
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
