package com.zhaowb.netty;

public class MyThread extends Thread {

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 50000; i++) {
            System.out.println("i = " + i);
        }
    }

    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
        try {
            Thread.sleep(100);
            System.out.println("0? " + thread.isInterrupted());
            thread.interrupt();
            System.out.println("1? " + thread.isInterrupted());
            System.out.println("2? " + thread.isInterrupted());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");

    }
}
