package com.zhaowb.netty.ch7_1;

import com.zhaowb.netty.ch5_2.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SubReqServer {

    public void bind(int port) throws Exception {

        // 配置 服务端的 NIO 线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            // 创建一个 ObjectDecoder ，负责对实现 Serializable 的 POJO 对象进行解码。
                            // 有多个构造函数，支持不同的ClassResolver，使用weakCachingConcurrentResolver创建线程安全的
                            // WeakReferenceMap 对类加载器进行缓存，支持多线程并发访问，当虚拟机内存不足时，会释放缓存中
                            // 的内存，防止内存泄漏，为了防止异常码流和解码错误导致的内存溢出，将单个对象最大序列化后的
                            // 字节数组长度设置为 1M
                            ch.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                            // 创建 ObjectEncoder ，可以在消息发送的时候自动将实现 Serializable 的POJO 对象进行编码，
                            // 无需对对象手动序列化，只需要关注自己的业务逻辑处理即可，对象序列化和发序列化都由netty的对象编码解码器完成。
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new SubReqServerHandler());
                        }
                    });

            // 绑定端口，同步等待成功。
            ChannelFuture f = b.bind(port).sync();
            // 等待服务端监听端口关闭。
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            // 退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;

        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
            }
        }
        new SubReqServer().bind(port);
    }
}
