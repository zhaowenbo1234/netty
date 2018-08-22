package com.zhaowb.netty.ch2.bio.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

/**
 * <b>类名称：</b>CopyFileToLan<br/>
 * <b>类描述：</b> 本地文件写入局域网共享文件夹   <br/>
 * <b>版本：</b>V1.0<br/>
 * <b>创建时间：</b>2016年6月8日 下午3:12:36<br/>
 */
public class CopyFileToLan {

    public static void main(String[] args) {

        InputStream in = null;
        OutputStream out = null;
        try {
            //测试文件
            File localFile = new File("D:\\文档\\test.txt");

            String host = "192.168.1.151";//远程服务器的地址
            String username = "luyun";//用户名
            String password = "luyunshu";//密码
            String path = "/share/";//远程服务器共享文件夹名称

            String remoteUrl = "smb://" + username + ":" + password + "@" + host + path + (path.endsWith("/") ? "" : "/");
            SmbFile remoteFile = new SmbFile(remoteUrl + "/" + localFile.getName());

            remoteFile.connect();

            in = new BufferedInputStream(new FileInputStream(localFile));
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));

//            in = new BufferedInputStream(new FileInputStream(localFile));
//            out = new BufferedOutputStream(new SmbFileOutputStream(localFile));

            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            String msg = "发生错误：" + e.getLocalizedMessage();
            System.out.println(msg);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }
    }
}
