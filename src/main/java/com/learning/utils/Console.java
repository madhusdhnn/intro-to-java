package com.learning.utils;

public class Console {

    public static void log(String message) {
        log(message, false);
    }

    public static void log(String message, boolean showThreadName) {
        if (showThreadName) {
            System.out.printf("[%s] %s%n", Thread.currentThread().getName(), message);
        } else {
            System.out.printf("%s%n", message);
        }
    }

}
