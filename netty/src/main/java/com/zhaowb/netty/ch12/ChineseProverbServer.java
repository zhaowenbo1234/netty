package com.zhaowb.netty.ch12;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class ChineseProverbServer {

    private void run(int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)// 使用UDP通信，在创建Channel 的时候使用NioDatagramChannel来创建
                    .option(ChannelOption.SO_BROADCAST, true)// 设置Socket 支持广播，
                    .handler(new ChineseProverbServerHandler());// 设置业务处理handler
            // UDP 不存在客户端和服务端实际链接，因此不需要ChannelPipeline设置handler，对于服务端，只需要启动辅助类handler就可以了
            b.bind(port).sync().channel().closeFuture().await();

        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new ChineseProverbServer().run(port);
    }
}
