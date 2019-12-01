package com.learning.projects.guessmovie;

import com.learning.projects.guessmovie.data.Movie;
import com.learning.projects.guessmovie.data.ThrowableConsumer;
import com.learning.projects.guessmovie.game.MovieGuessGame;
import com.learning.projects.guessmovie.game.Result;
import com.learning.utils.Console;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.learning.utils.FileUtils.readFile;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class GuessTheMovie implements Runnable {

    private static final String MOVIES_FILE = "movies.txt";
    private static final String FILE_PATH = "data" + File.separator + MOVIES_FILE;

    @Override
    public void run() {
        Path filePath = Paths.get("src", "main", "resources", FILE_PATH);
        List<Movie> movies = readFile(filePath, lines -> lines.map(Movie::new).collect(Collectors.toList()));
        Movie randomMovie = movies.get(new Random().nextInt(movies.size()));
        playGame(randomMovie);
    }

    private void playGame(Movie pickedMovie) {
        withReader(reader -> {
            final MovieGuessGame movieGuessGame = MovieGuessGame.getInstance();
            int chances = movieGuessGame.getTotalChances();
            showWelcome(chances);

            long startTime = System.currentTimeMillis();

            movieGuessGame.start(pickedMovie);
            showGuess(movieGuessGame);

            while (chances > 0) {
                char ch = read(reader);
                if (ch == '\0') {
                    Console.log("WARNING: Enter a valid character");
                } else if (!movieGuessGame.guess(ch)) {
                    chances--;
                    Console.log(String.format("No of chances left: %d", chances), true);
                } else {
                    if (movieGuessGame.isFinished()) {
                        break;
                    }
                }
                showGuess(movieGuessGame);
            }

            Result result = movieGuessGame.stop();
            long endTime = System.currentTimeMillis();

            showResult(result, (endTime - startTime));
        });
    }

    private static void withReader(ThrowableConsumer<BufferedReader> consumer) {
        try (InputStreamReader isr = new InputStreamReader(System.in);
             BufferedReader reader = new BufferedReader(isr)) {
            consumer.accept(reader);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private static void showResult(Result result, long timeTakenInMillis) {
        Console.log(result.getResult(), true);
        Console.log(result.getMessage(), true);
        Console.log(String.format("Time taken: %d (sec)", MILLISECONDS.toSeconds(timeTakenInMillis)), true);
    }

    private static char read(BufferedReader reader) throws IOException {
        System.out.print(String.format("[%s] Guess a letter:", Thread.currentThread().getName()));
        String input = reader.readLine();
        return (input == null || input.isEmpty()) ? '\0' : input.charAt(0);
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
        Console.log(String.format("You are guessing: %s", movieGuessGame.getBlanks()), true);
    }

    private static void showWrongGuesses(MovieGuessGame movieGuessGame) {
        Console.log(String.format("You have guessed (%d) wrong letters: %s", movieGuessGame.getNumberOfWrongLetters(), movieGuessGame.getWrongLetters()), true);
    }

}
