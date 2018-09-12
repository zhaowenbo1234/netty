package com.zhaowb.netty.ch2.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel servChannel;

    private volatile boolean stop;

    /**
     * 初始化多路复用器，绑定监听端口
     *
     * @param port
     */
    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            servChannel = ServerSocketChannel.open();
            servChannel.configureBlocking(false); // 设置为一部非阻塞模式
            servChannel.socket().bind(new InetSocketAddress(port), 1024);
            servChannel.register(selector, SelectionKey.OP_ACCEPT); // 系统资源初始化成功后，将 ServerSocketChannel 注册到 Selector ,监听 SelectionKey.OP_ACCEPT 操作位，如果资源初始化失败，则退出
            System.out.println("The time server is start in : " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    /**
     * selector 休眠时间为 1s 无论是否有读写等事件发生， selector 每隔1s都别唤醒一次，selector 也提供了一个无参的 select方法。当有处于就绪状态的 Channel时，
     * <p>
     * selector 将返回就绪状态的 Channel 的 SelectionKey 集合，通过对就绪状态的 Channel 集合进行迭代，可以进行网络的异步读写操作
     *
     * @see Runnable#run()
     */
    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        // 多路复用器关闭后，所有注册在上面的 Channel 和 Pipe 等资源都会被自动去注册并关闭。所以不需要重复释放资源
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {

        if (key.isValid()) {
            // 处理新接入的请求消息
            /**
             * 处理新接入的客户端请求消息，根据 SelectionKey 的操作位进行判断即可获知网络事件的类型，
             * 通过 ServerSocketChannel 的 accept 接受客户端的连接请求并创建 SocketChannel 实例 。
             */
            if (key.isAcceptable()) {
                // Accept the new connection
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                // Add the new Connection to the selector
                sc.register(selector, SelectionKey.OP_READ);
            }
            /**
             *  用于读取客户端的请求消息，首先创建一个 ByteBuffer ,暂定为1 K的缓冲区。够我玩的了
             *  然后调用 SocketChannel 的 read 方法读取请求码流。
             *  注意 ： 已经将 SocketChannel 设置为异步非阻塞模式，因此它的 read 是非阻塞的，使用返回值进行判断，
             *  看读取到的字节数，返回值有三种可能的结果。
             *      1）、返回值大于0:读取到字节，对字节进行编解码；
             *      2）、返回值等于0：没有读取到字节，属于正常场景，忽略；
             *      3）、返回值等于-1：链路已经关闭，需要关闭 SocketChannel ，释放资源
             *   当读取到资源以后，进行解码，首先对 readBuffer 进行 flip 操作， 作用是将缓冲区当前的 limit 设置为 position
             *   position 设置为 0，用于后续对缓冲区的读取操作。然后根据缓冲区的可读的字节个数创建字节数组，调用 ByteBuffer
             *   的 get 操作将缓冲区可读的 字节数复制到新创建的字节数组中，最后调用字符串中的构造函数创建请求消息体并打印。
             *
             */
            if (key.isReadable()) {
                // Read the data
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server received order : "
                            + body);
                    String currentTime = "QUERY TIME ORDER"
                            .equalsIgnoreCase(body) ? new Date(
                            System.currentTimeMillis()).toString()
                            : "BAD ORDER";
                    doWrite(sc, currentTime);
                } else if (readBytes < 0) {
                    // 对端链路关闭
                    key.cancel();
                    sc.close();
                } else {
                    ; // 读到 0 字节 ，忽略
                }
            }
        }
    }

    /**
     * 将应答数据异步发送给给客户端。
     * 1）、将字符串编码成字节数组，根据字节数据的容量创建 ByteBuffer ，调用 ByteBuffer 的 put 操作将字节数组复制到
     * 缓冲区中；
     * 2）、对缓冲区进行 flip 操作，；
     * 3）、调用 SocketChannel 的 write 方法，将缓冲区的字节数组发送出去。
     * 注意：
     * 由于 SocketChannel 是异步非阻塞的，它并不保证一次能把需要发送的字节数组发送完，此时会出现 “写半包”问题
     * 需要注册写操作，不断轮训 Selector 将没有发送完的 ByteBuffer 发送完毕，可以通过 ByteBuffer 的 hasRemaining
     * 方法判断消息是否发送完成，暂不考虑。
     *
     * @param channel
     * @param response
     * @throws IOException
     */
    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }
}