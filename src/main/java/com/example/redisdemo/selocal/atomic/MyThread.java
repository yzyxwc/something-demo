package com.example.redisdemo.selocal.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wc
 */
public class MyThread extends Thread {
    public static AtomicInteger atomicInteger = new AtomicInteger(10);
    @Override
    public void run() {
        try {
            Thread.sleep(50);
            atomicInteger.incrementAndGet();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
