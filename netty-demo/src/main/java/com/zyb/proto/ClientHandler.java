package com.zyb.proto;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author：Z1084
 * @description：
 * @create：2022-08-30 17:21
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        int count = byteBuf.readableBytes();
        byte[] bytes = new byte[count];
        byteBuf.readBytes(bytes);
        User user = ProtostuffUtil.deserializer(bytes, User.class);
        System.out.println("收到回复的消息:" + user);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User user = new User(1, "张三");
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(ProtostuffUtil.serializer(user)));
        System.out.println("发送数据");
    }
}
