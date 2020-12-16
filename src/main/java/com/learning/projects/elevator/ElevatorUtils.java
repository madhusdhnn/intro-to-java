package com.learning.projects.elevator;

public class ElevatorUtils {

    public static void checkPreCondition(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

}
