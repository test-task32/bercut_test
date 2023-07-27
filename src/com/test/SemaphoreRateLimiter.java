package com.test;

import java.util.concurrent.Semaphore;

public class SemaphoreRateLimiter implements RateLimiter {

    private final Semaphore semaphore;
    private final int messageLimit;
    private final int periodMs;
    private long lastReleaseTime;

    public SemaphoreRateLimiter(int messageLimit, int period_ms) {
        this.messageLimit = messageLimit;
        this.periodMs = period_ms;
        semaphore = new Semaphore(messageLimit);
        lastReleaseTime = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean acquire() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastReleaseTime;
        if (elapsedTime >= periodMs) {
            int permissionsToRelease = messageLimit - semaphore.availablePermits();
            semaphore.release(permissionsToRelease);
            lastReleaseTime = currentTime;
        }
        return semaphore.tryAcquire();
    }
}
