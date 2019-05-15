package com.selenium.test.disc;

/**
 * Created by harbor on 2019/5/15.
 */
public class Counter {
    private volatile int count =0;
    public void inc(){
        try{
            Thread.sleep(3);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
//        synchronized (this) {
            count++;
//        }
    }

    @Override
    public String toString() {
        return "Counter{" +
                "count=" + count +
                '}';
    }

    public static void main(String[] args) {
        final Counter couner = new Counter();
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
