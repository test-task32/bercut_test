package com.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    private static final int APP_RUN_TIME_MS = 60 * 1000;
    private static final int MESSAGE_LIMIT = 5;
    private static final int PERIOD_MS = 10 * 1000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Application started");

        BlockingQueue<String> buffer = new LinkedBlockingQueue();
        RateLimiter rateLimiter = new SemaphoreRateLimiter(MESSAGE_LIMIT, PERIOD_MS);

        Consumer consumer = new Consumer(buffer, rateLimiter);
        Producer producer = new Producer(buffer);

        Thread consumerThread = new Thread(consumer);
        Thread producerThread = new Thread(producer);


        //Потоки были сделаны демонами только ради удобства завершения программы.
        //Можно было воспользоваться interrupt() и обработкой
        //while(!Thread.currentThread().isInterrupted()) внутри потоков
        consumerThread.setDaemon(true);
        producerThread.setDaemon(true);

        consumerThread.start();
        producerThread.start();

        Thread.sleep(APP_RUN_TIME_MS);

        System.out.println("Application finished");
    }
}
