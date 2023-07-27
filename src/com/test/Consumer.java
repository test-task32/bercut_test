package com.test;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<String> buffer;
    private final RateLimiter rateLimiter;
    private static final int PERFORMANCE_DELAY_MS = 1000;
    private static final int DELAY_BEFORE_ACQUIRE_TRYING_MS = 10;

    public Consumer(BlockingQueue<String> buffer, RateLimiter rateLimiter) {
        this.buffer = buffer;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (rateLimiter.acquire()) {
                    String message = buffer.take();
                    System.out.println("Consumed message: " + message);
                    Thread.sleep(PERFORMANCE_DELAY_MS);
                } else {
                    Thread.sleep(DELAY_BEFORE_ACQUIRE_TRYING_MS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
