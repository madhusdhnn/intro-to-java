package com.learning.projects.guessmovie.game;

import com.learning.projects.guessmovie.data.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MovieGuessGame implements Game {

    private static final MovieGuessGame INSTANCE = new MovieGuessGame();
    private static final int CHANCES = 10;
    private final List<String> wrongLetters = new ArrayList<>();

    private String blanks = "";
    private Movie pickedMovie = null;

    private boolean completed = false;
    private boolean started = false;

    private MovieGuessGame() {
    }

    public static MovieGuessGame getInstance() {
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

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void start(Movie pickedMovie) {
        this.pickedMovie = pickedMovie;
        this.started = true;
        this.blanks = getInitialBlanks(pickedMovie.getName().length());
    }

    @Override
    public boolean guess(char ch) {
        checkGameStarted();
        String name = pickedMovie.getName();
        String character = String.valueOf(ch);
        if (name.contains(character)) {
            if (!this.blanks.contains(character))
                updateBlanks(ch);
            if (this.blanks.equalsIgnoreCase(name)) {
                completed = true;
            }
        } else {
            if (this.wrongLetters.indexOf(character) == -1) {
                this.wrongLetters.add(character);
                return false;
            }
        }
        return true;
    }

    @Override
    public Result stop() {
        checkGameStarted();
        String name = pickedMovie.getName();
        return (this.wrongLetters.isEmpty() || this.blanks.equals(name))
                ? new Result("You win!", String.format("You have guessed \'%s\' correctly", name))
                : new Result("You lost!", String.format("The movie is \'%s\'", name));
    }

    private void updateBlanks(char guessedCharacter) {
        String movieName = this.pickedMovie.getName();
        char[] chars = movieName.toCharArray();
        final StringBuilder builder = new StringBuilder();
        for (char ch : chars) {
            if (guessedCharacter == ch || ch == 32) {
                builder.append(ch);
            } else {
                builder.append("-");
            }
        }
        this.blanks = mergeBlanks(this.blanks, builder.toString());
    }

    private String mergeBlanks(String oldStr, String newStr) {
        if (oldStr == null || newStr == null)
            return "";
        if (oldStr.length() != newStr.length())
            return "";

        char[] oldChars = oldStr.toCharArray();
        char[] newChars = newStr.toCharArray();

        char[] result = new char[oldChars.length];

        for (int i = 0; i < oldChars.length; i++) {
            if (oldChars[i] == '-' && newChars[i] == '-') {
                result[i] = '-';
            } else {
                if (oldChars[i] != '-')
                    result[i] = oldChars[i];
                else if (newChars[i] != '-')
                    result[i] = newChars[i];
            }
        }
        return new String(result);
    }

    private void checkGameStarted() {
        if (!this.started)
            throw new RuntimeException("Please start a new game");
    }

    private String getInitialBlanks(int length) {
        return IntStream.range(0, length).mapToObj(i -> "-").collect(Collectors.joining());
    }

}
