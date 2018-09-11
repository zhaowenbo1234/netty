package com.zhaowb.netty.weight;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author zwb
 */
public class WeightServer {

    private final static int PORT = 16666;

    private void run(int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new WeightServerHandler());
            b.bind(port).sync().channel().closeFuture().await();
            System.out.println("监听端口 " + port);
        } catch (Exception e) {
            System.out.println("weightServer run catch");
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new WeightServer().run(PORT);
    }
}
