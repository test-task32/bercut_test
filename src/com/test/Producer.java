package com.test;

import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final BlockingQueue<String> buffer;
    private static final int PERFORMANCE_DELAY_MS = 500;

    public Producer(BlockingQueue<String> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = createMessage();
                buffer.put(message);
                System.out.println("Produced message: " + message);
                Thread.sleep(PERFORMANCE_DELAY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String createMessage() {
        return new StringBuilder()
                .append("message_id: ")
                .append(UUID.randomUUID())
                .append("; time: ")
                .append(LocalTime.now()).toString();
    }
}
