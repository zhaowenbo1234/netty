package com.zhaowb.netty.ch7_1;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqClientHandler extends ChannelHandlerAdapter {

    public SubReqClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 10; i++) {
            ctx.write(subResp(i));
        }
        ctx.flush();
    }

    private SubscribeReq subResp(int i) {
        SubscribeReq req = new SubscribeReq();
        req.setAddress("国家地质公园");
        req.setPhoneNumber("5464654113");
        req.setProductName("netty 权威指南");
        req.setSubReqID(i);
        req.setUserName("lilinfeng");
        return req;

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
