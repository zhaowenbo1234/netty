package com.zhaowb.netty.extendslearn;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/7 16:00
 */
public class Test {
    public static void main(String[] args) {

        Animal animal = new Animal("animal",2);
        animal.eat();
        Mouse mouse = new Mouse("mouse", 1);
        System.out.println(mouse);
        mouse.introduction();
        mouse.eatTest();

        Penguin penguin = new Penguin("penguin", 3);
        System.out.println(penguin);
        penguin.introduction();
    }
}
