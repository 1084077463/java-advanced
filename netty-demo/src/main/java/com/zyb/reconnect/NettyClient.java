package com.zyb.reconnect;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @author：Z1084
 * @description：
 * @create：2022-08-31 17:46
 */
public class NettyClient {
    private String host;
    private int port;
    private Bootstrap bootstrap;
    private NioEventLoopGroup group;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        init();
    }

    public static void main(String[] args) throws InterruptedException {
        NettyClient nettyClient = new NettyClient("127.0.0.1", 9000);
        nettyClient.connect();
    }

    private void init() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyClientHandler(NettyClient.this));
                    }
                });
    }

    public void connect() throws InterruptedException {
        System.out.println("netty client start");
        ChannelFuture channelFuture = bootstrap.connect(host, port);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    future.channel().eventLoop().schedule(() -> {
                        System.out.println("reconnect server");
                        try {
                            connect();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }, 2, TimeUnit.SECONDS);
                } else {
                    System.out.println("connect success");
                }
            }
        });
        channelFuture.channel().closeFuture().sync();

    }
}
