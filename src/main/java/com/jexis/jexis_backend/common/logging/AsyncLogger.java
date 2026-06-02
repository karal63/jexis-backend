package com.jexis.jexis_backend.common.logging;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Reusable asynchronous logger for the whole backend.
 * It uses a background thread to consume log messages from a queue,
 * which keeps the main application flow fast and decoupled.
 */
@Component
public class AsyncLogger {
    private final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    private final Thread loggerThread;

    public AsyncLogger() {
        loggerThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String message = logQueue.take();
                    System.out.println("[ASYNC-LOG] " + message);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "global-async-logger");

        loggerThread.setDaemon(true);
        loggerThread.start();
    }

    public void info(String message) {
        logQueue.offer(message);
    }

    public void info(String category, String message) {
        logQueue.offer("[" + category + "] " + message);
    }

    public void shutdown() {
        loggerThread.interrupt();
    }
}
