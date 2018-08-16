package com.zhaowb.netty.ch2.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        attachment.asynchronousServerSocketChannel.accept(attachment,this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        /**
         *  ByteBuffer dst, 接受缓冲区，用于从异步 Channel 中读取数据包
         *  A attachment, 异步 Channel 携带的附件，通知回调的时候作为入参使用
         *  CompletionHandler<Integer,? super A> handler ： 接受通知回调的业务 handle
         */
        result.read(buffer,buffer,new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
