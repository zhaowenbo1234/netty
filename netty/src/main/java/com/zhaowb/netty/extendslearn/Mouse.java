package com.zhaowb.netty.extendslearn;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/7 15:59
 */
public class Mouse extends Animal {

    public Mouse() {
    }

    public Mouse(String name, Integer id) {
        super(name, id);
    }

    @Override
    public void eat() {
        System.out.println(super.getName() + " : eat");
    }

    public void eatTest() {
        this.eat();
        super.eat();
    }


}
