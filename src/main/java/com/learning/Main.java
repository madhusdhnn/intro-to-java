package com.learning;

import com.learning.projects.ProblemDetail;
import com.learning.projects.elevator.ElevationSystem;
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

    private static final Map<Integer, ProblemDetail> PROBLEM_DETAILS = getProblemDetailsMap();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        if (args.length == 0) {
            showUsage();
            sleep(500);
            System.exit(-1);
        }

        Integer problemCode = Integer.parseInt(args[0]);

        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        if (PROBLEM_DETAILS.containsKey(problemCode)) {
            ProblemDetail problemDetail = PROBLEM_DETAILS.get(problemCode);
            Console.log(String.format("Starting problem: %s\n", problemDetail.getName()), true);
            ScheduledFuture<?> future = scheduler.schedule(problemDetail.createInstance(), 300, MILLISECONDS);
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

    private static Map<Integer, ProblemDetail> getProblemDetailsMap() {
        Map<Integer, ProblemDetail> map = new HashMap<>();
        map.put(1, new ProblemDetail(1, "mission-space", MissionSpace::new));
        map.put(2, new ProblemDetail(2, "guess-the-movie", GuessTheMovie::new));
        map.put(3, new ProblemDetail(3, "elevator-system", ElevationSystem::new));
        return map;
    }

    private static void showUsage() {
        new Thread(() -> {
            Console.log("\nUsage: java -jar java-problems-<VERSION>.jar <PROBLEM_CODE>\n");
            Console.log("Where <PROBLEM_CODE> is one of:");
            Set<Map.Entry<Integer, ProblemDetail>> entries = PROBLEM_DETAILS.entrySet();
            for (Map.Entry<Integer, ProblemDetail> entry : entries) {
                ProblemDetail problemDetail = entry.getValue();
                Console.log(String.format("%d for %s", problemDetail.getGameCode(), problemDetail.getName()));
            }
        }, "usage-thread").start();
    }
}
