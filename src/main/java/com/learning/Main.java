package com.learning;

import com.learning.projects.GameDetail;
import com.learning.projects.guessmovie.GuessTheMovie;
import com.learning.projects.missionspace.MissionSpace;
import com.learning.utils.Console;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static com.learning.utils.ThreadUtils.gracefulShutdown;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {

    private static final Map<Integer, GameDetail> GAME_DETAILS = getGameDetailsMap();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        if (args.length == 0) {
            showUsage();
            sleep(500);
            System.exit(-1);
        }

        Integer gameCode = Integer.parseInt(args[0]);

        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        if (GAME_DETAILS.containsKey(gameCode)) {
            GameDetail gameDetail = GAME_DETAILS.get(gameCode);
            Console.log(String.format("Starting Game: %s\n", gameDetail.getName()), true);
            ScheduledFuture<?> future = scheduler.schedule(gameDetail.createGameInstance(), 300, MILLISECONDS);
            future.get();
            sleep(1000);
            Console.log("Starting to attempt graceful shutdown..");
            gracefulShutdown(scheduler, 5, SECONDS);
            Console.log("Done");
        } else {
            showUsage();
            sleep(500);
            gracefulShutdown(scheduler, 500, MILLISECONDS);
            System.exit(-1);
        }
    }

    private static void stopGameEngineRunner(ScheduledExecutorService scheduler) {

    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            // ignore
        }
    }

    private static Map<Integer, GameDetail> getGameDetailsMap() {
        Map<Integer, GameDetail> map = new HashMap<>();
        map.put(1, new GameDetail(1, "mission-space", MissionSpace::new));
        map.put(2, new GameDetail(2, "guess-the-movie", GuessTheMovie::new));
        return map;
    }

    private static void showUsage() {
        new Thread(() -> {
            Console.log("\nUsage: java -jar intro-to-java-<VERSION>.jar <GAME_CODE>\n");
            Console.log("Where <GAME_CODE> is one of:");
            Set<Map.Entry<Integer, GameDetail>> entries = GAME_DETAILS.entrySet();
            for (Map.Entry<Integer, GameDetail> entry : entries) {
                GameDetail gameDetail = entry.getValue();
                Console.log(String.format("%d for %s", gameDetail.getGameCode(), gameDetail.getName()));
            }
        }, "usage-thread").start();
    }
}
