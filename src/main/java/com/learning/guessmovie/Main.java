package com.learning.guessmovie;

import com.learning.guessmovie.data.Movie;
import com.learning.guessmovie.game.MovieGuessGame;
import com.learning.guessmovie.game.Result;
import com.learning.utils.Console;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.learning.utils.FileUtils.readFile;

public class Main {

    private static final String MOVIES_FILE = "movies.txt";
    private static final String FILE_PATH = "data" + File.separator + MOVIES_FILE;

    public static void main(String[] args) {
        Main main = new Main();
        Path filePath = Paths.get("src", "main", "resources", FILE_PATH);
        List<Movie> movies = readFile(filePath, lines -> lines.map(Movie::new).collect(Collectors.toList()));
        Movie randomMovie = movies.get(new Random().nextInt(movies.size()));
        main.playGame(randomMovie);
    }

    private void playGame(Movie pickedMovie) {
        try (InputStream is = System.in;
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader reader = new BufferedReader(isr)) {

            final MovieGuessGame movieGuessGame = MovieGuessGame.getInstance();
            int chances = movieGuessGame.getTotalChances();
            showWelcome(chances);

            movieGuessGame.start(pickedMovie);
            showGuess(movieGuessGame);

            while (chances > 0) {
                char ch = read(reader);
                if (!movieGuessGame.guess(ch)) {
                    chances--;
                    Console.log(String.format("No of chances left: %d", chances));
                } else {
                    if (movieGuessGame.isCompleted()) {
                        break;
                    }
                }
                showGuess(movieGuessGame);
            }

            Result result = movieGuessGame.stop();
            showResult(result);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private static void showResult(Result result) {
        Console.log(result.getResult());
        Console.log(result.getMessage());
    }

    private static char read(BufferedReader reader) throws IOException {
        System.out.print("Guess a letter:");
        return reader.readLine().charAt(0);
    }

    private static void showWelcome(int chances) {
        Console.log("Welcome to Guess The Movie game\n");
        Console.log("Instructions:");
        Console.log(String.format("1. You have totally %d chances", chances));
        Console.log("2. Each wrong guess will reduce a chance, but as far as the guesses are correct, you won't lose the chance\n");
        Console.log("NOTE: Characters are case sensitive\n");
    }

    private static void showGuess(MovieGuessGame movieGuessGame) {
        showRemainingGuess(movieGuessGame);
        showWrongGuesses(movieGuessGame);
    }

    private static void showRemainingGuess(MovieGuessGame movieGuessGame) {
        Console.log(String.format("You are guessing: %s", movieGuessGame.getBlanks()));
    }

    private static void showWrongGuesses(MovieGuessGame movieGuessGame) {
        Console.log(String.format("You have guessed (%d) wrong letters: %s", movieGuessGame.getNumberOfWrongLetters(), movieGuessGame.getWrongLetters()));
    }

}
