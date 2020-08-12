package com.learning.projects.guessmovie.game;

import com.learning.projects.guessmovie.data.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.learning.utils.StringUtils.toCapitalize;

public class MovieGuessGameEngine {

    private static final MovieGuessGameEngine INSTANCE;
    private static final int CHANCES;

    static {
        INSTANCE = new MovieGuessGameEngine();
        CHANCES = 10;
    }

    private final List<String> wrongLetters = new ArrayList<>();

    private String blanks = "";
    private Movie pickedMovie = null;

    private boolean finished = true;
    private boolean started = false;

    private MovieGuessGameEngine() {
    }

    public static MovieGuessGameEngine getInstance() {
        return INSTANCE;
    }

    public int getTotalChances() {
        return CHANCES;
    }

    public String getBlanks() {
        return this.blanks;
    }

    public String getWrongLetters() {
        return this.wrongLetters.stream().map(wrongLetter -> String.format("%s ", wrongLetter)).collect(Collectors.joining());
    }

    public int getNumberOfWrongLetters() {
        return this.wrongLetters.size();
    }

    public boolean isFinished() {
        return finished;
    }

    public void start(Movie pickedMovie) {
        checkGameFinished();
        synchronized (this) {
            this.pickedMovie = pickedMovie;
            this.started = true;
            this.finished = false;
            this.blanks = getInitialBlanks(pickedMovie.getName().length());
        }
    }

    public boolean guess(char ch) {
        checkGameStarted();
        String name = pickedMovie.getName();
        String character = String.valueOf(ch);
        if (name.contains(character)) {
            if (this.blanks.indexOf(ch) == -1)
                updateBlanks(ch);
            finishGameIfCompleted(name);
            return true;
        } else {
            if (!this.wrongLetters.contains(character)) {
                this.wrongLetters.add(character);
            }
            return false;
        }
    }

    public Result stop() {
        checkGameStarted();
        synchronized (this) {
            this.started = false;
            this.finished = true;
        }
        String name = pickedMovie.getName();
        return (this.wrongLetters.isEmpty() || this.blanks.equals(name))
                ? new Result("You win!", String.format("You have guessed '%s' correctly", toCapitalize(name)))
                : new Result("You lost!", String.format("The movie is '%s'", toCapitalize(name)));
    }

    private void finishGameIfCompleted(String name) {
        synchronized (this) {
            if (this.blanks.equalsIgnoreCase(name)) {
                this.finished = true;
            }
        }
    }

    private synchronized void updateBlanks(char guessedCharacter) {
        String movieName = this.pickedMovie.getName();

        char[] movieNameChars = movieName.toCharArray();
        char[] blankChars = this.blanks.toCharArray();

        for (int i = 0; i < movieNameChars.length; i++) {
            char ch;
            if ((ch = movieNameChars[i]) == guessedCharacter || ch == 32 && blankChars[i] == '-') {
                blankChars[i] = ch;
            }
        }

        this.blanks = String.valueOf(blankChars);
    }

    private void checkGameStarted() {
        if (!this.started)
            throw new RuntimeException("Please start a new game");
    }

    private void checkGameFinished() {
        if (!this.finished) {
            throw new RuntimeException("Game has already started");
        }
    }

    private String getInitialBlanks(int length) {
        return IntStream.range(0, length).mapToObj(i -> "-").collect(Collectors.joining());
    }

}
