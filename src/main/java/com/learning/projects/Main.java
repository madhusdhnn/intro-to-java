package com.learning.projects;

import com.learning.projects.guessmovie.GuessTheMovie;
import com.learning.projects.missionspace.MissionSpace;
import com.learning.utils.Console;

public class Main {

    private static final String MISSION_SPACE = "mission-space";
    private static final String GUESS_THE_MOVIE = "guess-the-movie";

    public static void main(String[] args) {
        if (args.length == 0) {
            showUsage();
            sleep(500);
            System.exit(-1);
        }

        String game = args[0];

        if (GUESS_THE_MOVIE.equalsIgnoreCase(game)) {
            Console.log(String.format("Starting Game: %s\n", game), true);
            Thread guessTheMovieThread = new Thread(new GuessTheMovie(), GUESS_THE_MOVIE);
            sleep(300);
            guessTheMovieThread.start();
        } else if (MISSION_SPACE.equalsIgnoreCase(game)) {
            Console.log(String.format("Starting Game: %s\n", game), true);
            Thread missionSpaceThread = new Thread(new MissionSpace(), MISSION_SPACE);
            sleep(300);
            missionSpaceThread.start();
        } else {
            showUsage();
            sleep(500);
            System.exit(-1);
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            // ignore
        }
    }

    private static void showUsage() {
        new Thread(() -> {
            Console.log("\nUsage: java -jar intro-to-java-<VERSION>.jar <GAME>\n");
            Console.log("Where <GAME> is one of:");
            Console.log(String.format("1. %s", GUESS_THE_MOVIE));
            Console.log(String.format("2. %s", MISSION_SPACE));
        }, "usage").start();
    }
}
