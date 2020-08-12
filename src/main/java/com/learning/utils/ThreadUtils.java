package com.learning.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {

    public static void gracefulShutdown(ExecutorService executorService, long timeout, TimeUnit timeUnit) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(timeout, timeUnit)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            if (!executorService.isShutdown()) {
                executorService.shutdownNow();
            }
        } finally {
            if (!executorService.isShutdown()) {
                executorService.shutdownNow();
            }
        }
    }

}
