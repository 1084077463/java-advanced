package com.zyb.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author：Z1084
 * @description：注意，当ChannelGroup前没有加static时，发现每个连接都有一个新的ChannelGroup，说明这个类，是每次都进行创建了，不是单例的
 * @create：2022-08-30 10:07
 */
//@ChannelHandler.Sharable 该注解只是创建管道时，用了相同的对象，是单例的还是每次都创建一个新的
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    //GlobalEventExecutor.INSTANCE是一个全局的事件执行器，是一个单例的
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //private  ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //给其他的客户端发送消息
        channelGroup.forEach(channel -> {
            if (!ctx.channel().equals(channel)) {
                channel.writeAndFlush("[客户端]" + ctx.channel().remoteAddress() + "发来了消息:" + msg);
            } else {
                channel.writeAndFlush("[自己]发送了消息:" + msg);
            }
        });
    }

    /**
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //1.先通知其他节点，在将节点加入到集合中，否则会给自己也发送一遍
        //通知其他的节点上线了
        channelGroup.writeAndFlush("[客户端]" + ctx.channel().remoteAddress() + "上线了" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //将新建立的连接放到ChannelGroup中
        channelGroup.add(ctx.channel());
        System.out.println("客户端" + ctx.channel().remoteAddress() + "上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //下线通知,把该对象进行移除
        channelGroup.remove(ctx.channel());
        channelGroup.writeAndFlush("客户端" + ctx.channel().remoteAddress() + "下线了");
        System.out.println("客户端" + ctx.channel().remoteAddress() + "下线了");

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.channel().close();
    }
}
