package com.zhaowb.netty.ch5_1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoServerHandler extends ChannelHandlerAdapter {

    int counter = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链接成功！");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 当读取信息时，先经过 DelimiterBasedFrameDecoder 去掉结束分隔符，然后再使用 StringDecoder 将 ByteBuf 解码成字符串/
        // 然后 EchoServerHandler 收到的数据就是解码后的字符串对象

        String body = (String) msg;
        System.out.println("This is " + ++counter + " times receive client : [" + body + "]");
        body += "$_"; // 由于 设置 DelimiterBasedFrameDecoder 过滤掉了分隔符，返回给客户端时需要在消息的尾部拼接 "$_"

        ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(echo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();// 发生异常，关闭链路
    }
}