package com.wh.com;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @description: 设计3个存在相互依赖关系的线程
 * @author: WangHu
 * @create: 2020-03-10 10:37
 **/
public class RelyThread {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        //线程A
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1);
                    System.out.println(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        //线程B
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1);
                    System.out.println(2);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        //线程C
        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1);
                    System.out.println(3);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        threadA.start();
        cyclicBarrier.await();
        threadB.start();
        cyclicBarrier.await();
        threadC.start();
        cyclicBarrier.await();
    }
}
