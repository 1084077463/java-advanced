package com.zyb.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author：Z1084
 * @description：
 * @create：2022-08-29 14:35
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("------------channelRegistered-----------------");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("------------channelUnregistered-----------------");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("------------channelActive-----------------");
        //建立好连接后，给服务端发送消息
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello server.im client".getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("------------channelInactive-----------------");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("------------channelRead-----------------");
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("从服务端收到的消息:" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务端的地址:" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("------------channelReadComplete-----------------");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("------------userEventTriggered-----------------");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("------------channelWritabilityChanged-----------------");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("------------exceptionCaught-----------------");
        super.exceptionCaught(ctx, cause);
    }
}
