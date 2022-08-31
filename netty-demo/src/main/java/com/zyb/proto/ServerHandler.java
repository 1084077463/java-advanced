package com.zyb.proto;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author：Z1084
 * @description：
 * @create：2022-08-30 17:49
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        int count = byteBuf.readableBytes();
        byte[] bytes = new byte[count];
        byteBuf.readBytes(bytes);
        User user = ProtostuffUtil.deserializer(bytes, User.class);
        System.out.println("客户端发来的消息:" + user);
        //回复消息
        user.setId(2);
        user.setName("lisi");
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(ProtostuffUtil.serializer(user)));
    }

}
