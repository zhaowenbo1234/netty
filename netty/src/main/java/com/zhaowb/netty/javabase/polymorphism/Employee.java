package com.zhaowb.netty.javabase.polymorphism;

import java.io.Serializable;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/10 16:14
 */
public class Employee implements Serializable {
    private String name;
    private String address;
    private int number;
    private transient int SSN;

    public Employee() {
    }

    public Employee(String name, String address, int number ) {
        System.out.println("Employee 构造函数");
        this.name = name;
        this.address = address;
        this.number = number;
    }
    public Employee(String name, String address, int number , int SSN) {
        System.out.println("Employee 构造函数");
        this.name = name;
        this.address = address;
        this.number = number;
        this.SSN = SSN;
    }

    public void mailCheck() {
        System.out.println("邮寄支票给： " + this.name
                + " " + this.address);
    }

    @Override
    public String toString() {
        return name + " " + address + " " + number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSSN() {
        return SSN;
    }

    public void setSSN(int SSN) {
        this.SSN = SSN;
    }
}
