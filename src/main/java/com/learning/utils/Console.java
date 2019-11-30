package com.learning.utils;

import java.io.PrintStream;

public class Console {

    private static final PrintStream out = System.out;

    private Console() {
    }

    public static void log(String message) {
        out.println(message);
    }

}
