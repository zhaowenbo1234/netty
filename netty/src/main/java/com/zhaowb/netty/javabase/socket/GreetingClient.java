package com.zhaowb.netty.javabase.socket;

import java.io.*;
import java.net.Socket;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/10 17:48
 */
public class GreetingClient {
    public static void main(String[] args) {
        String serverName = "127.0.0.1";
        int port = 7777;
        try {
            System.out.println("连接到主机：" + serverName + " ，端口号：" + port);
            Socket client = new Socket(serverName, port);
            System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF("Hello from " + client.getLocalSocketAddress());
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println("服务器响应： " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
