package com.zhaowb.netty.javabase.serialization;

import com.zhaowb.netty.javabase.polymorphism.Employee;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created with IDEA
 * 序列化 Employee 到 文件 employee.ser
 *
 * @author zwb
 * @create 2018/12/10 16:56
 */
public class SerializeDemo {
    public static void main(String[] args) {
        Employee e = new Employee();
        e.setAddress("Phokka Kuan, Ambehta Peer");
        e.setName("Reyan Ali");
        e.setSSN(112233);
        e.setNumber(1234566);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/employee.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(e);
            out.close();
            fileOutputStream.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException el) {
            el.printStackTrace();
        }
    }
}
