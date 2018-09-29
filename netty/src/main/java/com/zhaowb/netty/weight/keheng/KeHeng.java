package com.zhaowb.netty.weight.keheng;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/9/21 15:30
 */
public class KeHeng {

    /**
     * 日志记录
     */
    private static final Logger logger = LoggerFactory.getLogger(KeHengServerHandler.class);
    /**
     * 监听端口
     */
    private final static int PORT = 16666;

    public void run(int port) {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true).handler(new StringDecoder()).handler(new KeHengServerHandler());
            b.bind(port).sync().channel().closeFuture().await();
            logger.info("监听端口 port = {}", "port");
        } catch (Exception e) {
            logger.info("KeHeng run  is  catch exception = {}", e.getMessage());
            e.printStackTrace();
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new KeHeng().run(PORT);

        logger.info("KeHeng is running...");
    }
}
