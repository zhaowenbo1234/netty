package com.zhaowb.netty.ch13_1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;


public class FileServerHandler extends SimpleChannelInboundHandler<String> {

    private static final String CR = System.getProperty("line.separator");

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        File file = new File(msg);
        // 校验文件的合法性，不存在构造异常消息返回。如果存在使用RandomAccessFile 以只读的方式打开文件，然后使用netty的
        //  DefaultFileRegion 进行文件传输，
        //          FileChannel file, 文件通道，用于对文件进行读写操作
        //          long position,文件操作的指针位置，读取或写入的起始点
        //          long count，操作的总字符数
        if (file.exists()) {
            if (!file.isFile()) {
                ctx.writeAndFlush("Not a file " + msg);
                return;
            }
            ctx.write(file + " " + file.length() + CR);
            RandomAccessFile randomAccessFile = new RandomAccessFile(msg, "r");
            FileRegion region = new DefaultFileRegion(randomAccessFile.getChannel(), 0, randomAccessFile.length());
            ctx.write(region);
            ctx.writeAndFlush(CR); // 写入换行符，告诉CMD控制台，文件传输结束。
            randomAccessFile.close();
        } else {
            ctx.writeAndFlush("File not found " + file + CR);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接成功！");
    }


}
