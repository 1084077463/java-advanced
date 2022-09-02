package com.zyb.im;

import com.zyb.im.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author：Z1084
 * @description: 聊天室的服务端
 * @create：2022-08-30 9:51
 */
public class NettyTcpServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            //为了防止粘包的问题，我们可以使用LineBasedFrameDecoder这个是回车换行符。不过真实业务比较少用这种
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            //还有一种定义消息的长度，固定长度，空的用空格或者其他方式补全。
                            //ch.pipeline().addLast(new FixedLengthFrameDecoder(100));
                            //这个是固定结尾的方式进行切割，类似交通部的协议就是这样的
                            //ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("1".getBytes())));
                            ch.pipeline().addLast(new StringDecoder());

                            ch.pipeline().addLast(new ServerHandler());
                            ch.pipeline().addLast(new StringEncoder());

                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8000).sync();
            System.out.println("启动成功");
            //监听关闭的消息通知
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
