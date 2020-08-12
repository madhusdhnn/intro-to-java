package com.learning.projects;

import java.util.function.Supplier;

public class GameDetail {

    private final Integer gameCode;
    private final String name;
    private final Supplier<? extends Game> gameInstanceCreator;

    public GameDetail(Integer gameCode, String name, Supplier<? extends Game> gameInstanceCreator) {
        this.gameCode = gameCode;
        this.name = name;
        this.gameInstanceCreator = gameInstanceCreator;
    }

    public Integer getGameCode() {
        return gameCode;
    }

    public String getName() {
        return name;
    }

    public Game createGameInstance() {
        return gameInstanceCreator.get();
    }

}
