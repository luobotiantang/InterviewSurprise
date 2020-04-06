package com.wh.com;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @description: 生产者
 * @author: WangHu
 * @create: 2020-03-16 18:45
 **/
public class Provider {

    static LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<String>();

    public static void putMessage(String message) {
        try {
            linkedBlockingQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Consumer {
        public static String getMessage() {
            return linkedBlockingQueue.poll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                for (int i = 0; i < 1000; i++) {
                    message = "生产者：" + i;
                    putMessage(message);
                    System.out.println(message);
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println("消费者" + Consumer.getMessage());
                }
            }
        });

        thread.start();
        thread.join();
        thread1.start();
    }
}



