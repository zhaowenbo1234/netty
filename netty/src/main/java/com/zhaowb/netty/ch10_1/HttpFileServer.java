package com.zhaowb.netty.ch10_1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {

    // 在网上看到/src/com/ 但是在实际操作中，这个一直提示404，调试的时候发现，路径错误，换成了"/netty/src/main/java/com/";
    // 我的是idea  在 eclipse 中 /src/main/java/com/就可以。
    private static final String DEFAULT_URL = "/netty/src/main/java/com/";

    public void run(final int port, final String url) throws Exception {

        // 配置 服务端的 NIO 线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());

                            // Chunked Handler 主要作用是支持异步发送大的码流（例如大的文件传输），但不占用过多的内存，
                            // 防止发生java内存溢出错误。
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());

                            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });

            // 绑定端口，同步等待成功。
            ChannelFuture f = b.bind("192.168.1.156", port).sync();
            System.out.println("HTTP 文件目录服务器启动，网址是 ：" + "http://192.168.1.156:" + port + url);
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

                e.printStackTrace();
            }
        }
        String url = DEFAULT_URL;
        if (args.length > 1) {
            url = args[1];
        }
        new HttpFileServer().run(port, url);
    }
}
