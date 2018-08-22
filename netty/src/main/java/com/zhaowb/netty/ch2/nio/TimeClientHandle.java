package com.zhaowb.netty.ch2.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class TimeClientHandle implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    /**
     * 初始化 NIO 的多路复用器和 SocketChannel 对象，并将其设置为异步非阻塞模式
     *
     * @param host
     * @param port
     */
    public TimeClientHandle(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * @see Runnable#run()
     */
    @Override
    public void run() {

        /**
         * 用于发送连接请求，理想连接成功，暂不考虑连接异常，需要重连的情况
         */
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * 在循环体中轮询多路复用器 Selector ,当有就绪的 Channel 时，执行handleInput(key)方法
         */
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
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
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

    /**
     * 首先判断 SelectionKey 处于什么状态，如果是处于连接状态，说明服务端已经返回 ACK 应答消息。此时对连接结果进行判断，
     * 调用 SocketChannel 的 finishConnect()方法，如果返回 true ，说明客户端连接成功；如果返回 false 或者直接抛出
     * IOException ,说明连接失败。
     * 连接成功： 将 SocketChannel 注册到多路复用器上，注册 SelectionKey.OP_READ 操作位，监听网络读操作，然后发送请求
     * 消息给服务端。
     *
     * @param key
     * @throws IOException
     */
    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            // 判断是否连接成功
            SocketChannel sc = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                if (sc.finishConnect()) {
                    sc.register(selector, SelectionKey.OP_READ);
                    doWrite(sc);
                } else {
                    System.exit(1); // 连接失败，进程结束
                }

            }
            /**
             * 如果客户端收到服务端的应答消息，则 SocketChannel 是可读的 ，分配 1k 的缓冲区用于读取应答消息，调用
             *      SocketChannel 的 read() 方法进行异步读取操作。由于是异步操作，需要对读取结果进行判断，
             *      看读取到的字节数，返回值有三种可能的结果。
             *                   1）、返回值大于0:读取到字节，对字节进行编解码；
             *                   2）、返回值等于0：没有读取到字节，属于正常场景，忽略；
             *                   3）、返回值等于-1：链路已经关闭，需要关闭 SocketChannel ，释放资源
             *   当读取到资源以后，进行解码，首先对 readBuffer 进行 flip 操作， 作用是将缓冲区当前的 limit 设置为 position
             *   position 设置为 0，用于后续对缓冲区的读取操作。然后根据缓冲区的可读的字节个数创建字节数组，调用 ByteBuffer
             *   的 get 操作将缓冲区可读的 字节数复制到新创建的字节数组中，最后调用字符串中的构造函数创建请求消息体并打印。
             *   执行完成后将 stop 置为 true ，线程退出循环
             */
            if (key.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println(" Now is : " + body);
                    this.stop = true;
                } else if (readBytes < 0) {
                    // 对端链路关闭
                    key.cancel();
                    sc.close();
                } else {
                    ;
                }
            }
        }
    }

    /**
     * 先判断 SocketChannel 的 connect() 操作，如果连接成功，则将 SocketChannel 注册到多路复用器 Selector 上，
     * 注册 SelectionKey.OP_READ ，如果没有直接连接成功，则说明服务端没有返回 TCP 握手应答消息，但这不代表连接失败，
     * 需要将 SocketChannel 注册到多路复用器 Selector 上，注册 SelectionKey.OP_CONNECT ，当服务端返回 TCP syn-ack 消息后，
     * Selector 就能轮询到这个 SocketChannel 处于连接就绪状态。
     *
     * @throws IOException
     */
    private void doConnect() throws IOException {
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    /**
     * 构造请求消息体，然后编码，写入到发送缓冲区中，最后调用 SocketChannel 的 write 方法进行发送。由于发送是异步的，
     * 所以会存在“半包写”问题，最后通过 hasRemaining() 方法对发送结果进行判断，如果缓冲区中的消息全部发送完成，打印
     * " Send order 2 server succeed.".
     *
     * @param sc
     * @throws IOException
     */
    private void doWrite(SocketChannel sc) throws IOException {

        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            System.out.println(" Send order 2 server succeed.");
        }
    }
}
