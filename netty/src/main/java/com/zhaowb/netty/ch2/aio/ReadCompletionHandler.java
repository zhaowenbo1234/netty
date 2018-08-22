package com.zhaowb.netty.ch2.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    /**
     * 将 AsynchronousSocketChannel 通过参数传递到 ReadCompletionHandler 中当做变量使用，主要用于读取半包消息和发送应答。
     *
     * @param channel
     */
    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        if (this.channel == null) {
            this.channel = channel;
        }
    }

    /**
     * 读取到消息后的处理，对 attachment 进行 flip 操作，为后续从缓冲区读取数据做准备。根据缓冲区的可读字节数创建 byte
     * 数组，然后通过 new String 方法创建请求消息，对请求消息进行判断，如果是 QUERY TIME ORDER，获取当前系统服务器的时间，
     * 调用 doWrite 方法发送给客户端。
     *
     * @param result
     * @param attachment
     */
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body, "UTF-8");
            System.out.println("The time server received order : " + req);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ?
                    new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
            doWrite(currentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对当前时间进行合法性校验，如果合法，调用字符串的解码方法将应答消息编码成字节数组，然后将它复制到发送缓冲区
     * writeBuffer 中，最后调用 AsynchronousSocketChannel 的异步 write 方法。
     *
     * @param currentTime
     */
    private void doWrite(String currentTime) {

        if (currentTime != null && currentTime.trim().length() > 0) {
            byte[] bytes = currentTime.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    // 如果没有发送完成，继续发送
                    if (buffer.hasRemaining()) {
                        channel.write(buffer, buffer, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {

                    try {
                        channel.close();
                    } catch (IOException e) {

                    }
                }
            });
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {

        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
