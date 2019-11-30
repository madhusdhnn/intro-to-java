package com.learning.utils;

public class Console {

    public static void log(String message) {
        log(message, false);
    }

    public static void log(String message, boolean showThreadName) {
        if (showThreadName) {
            System.out.println(String.format("[%s] %s", Thread.currentThread().getName(), message));
        } else {
            System.out.println(String.format("%s", message));
        }
    }

}
