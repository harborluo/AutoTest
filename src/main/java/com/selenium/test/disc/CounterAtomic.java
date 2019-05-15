package com.selenium.test.disc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;

/**
 * Created by harbor on 2019/5/15.
 */
public class CounterAtomic {
    private AtomicLong count = new AtomicLong(0);

//    Lock

    public void inc(){
        try{
            Thread.sleep(300);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        count.incrementAndGet();

    }

    @Override
    public String toString() {
        return "Counter{" +
                "count=" + count.get() +
                '}';
    }

    public static void main(String[] args) {
        final CounterAtomic couner = new CounterAtomic();
        for(int i=0; i<1000; i++){
            new Thread(()->{
                couner.inc();
            }).start();
        }

        try{
            Thread.sleep(2000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println(couner.toString());
    }
}
