package com.zhaowb.netty.ch2.aio;

public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;

        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        // 创建异步的时间服务器处理类，然后启动线程将 AsyncTimeServerHandle 拉起
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
    }
}
