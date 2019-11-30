package com.learning.projects.guessmovie.game;

public class Result {

    private final String result;
    private final String message;

    Result(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
