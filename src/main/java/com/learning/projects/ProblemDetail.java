package com.learning.projects;

import java.util.function.Supplier;

public class ProblemDetail {

    private final Integer gameCode;
    private final String name;
    private final Supplier<? extends DesignProblem> problemInstanceCreator;

    public ProblemDetail(Integer gameCode, String name, Supplier<? extends DesignProblem> problemInstanceCreator) {
        this.gameCode = gameCode;
        this.name = name;
        this.problemInstanceCreator = problemInstanceCreator;
    }

    public Integer getGameCode() {
        return gameCode;
    }

    public String getName() {
        return name;
    }

    public DesignProblem createInstance() {
        return problemInstanceCreator.get();
    }

}
