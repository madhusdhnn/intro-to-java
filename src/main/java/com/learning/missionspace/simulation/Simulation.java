package com.learning.missionspace.simulation;

import com.learning.missionspace.data.Item;
import com.learning.missionspace.exceptions.RuntimeIOException;
import com.learning.missionspace.rockets.Rocket;
import com.learning.missionspace.rockets.impl.U1;
import com.learning.missionspace.rockets.impl.U2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulation {

    private static final Logger LOGGER = Logger.getLogger(Simulation.class.getSimpleName());

    public List<Item> loadItems(String filePath) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(getFilePathFromResource(filePath));
             Stream<String> lines = bufferedReader.lines()) {
            return lines.map(line -> line.split("="))
                    .filter(itemNameAndWeightArray -> itemNameAndWeightArray.length > 0)
                    .map(itemNameAndWeightArray -> new Item(itemNameAndWeightArray[0], Integer.parseInt(itemNameAndWeightArray[1])))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeIOException(String.format("Error while processing input file - %s", ex.getMessage()), ex);
        }
    }

    public List<U1> loadU1(List<Item> items) {
        final List<U1> u1Rockets = new ArrayList<>();
        U1 u1 = new U1();
        for (Item item : items) {
            if (u1.canCarry(item)) {
                u1.carry(item);
            } else {
                LOGGER.info("[U1] Can not carry item. Max weight reached. Setting up chance of launch explosion and landing crash");
                u1.setChanceOfLaunchExplosion();
                u1.setChanceOfLandingCrash();
                LOGGER.info("[U1] Creating new U1 rocket");
                u1Rockets.add(u1);
                u1 = new U1();
                u1.carry(item);
            }
        }
        if (u1.getTotalWeight() > 0) {
            u1Rockets.add(u1);
        }
        return u1Rockets;
    }

    public List<U2> loadU2(List<Item> items) {
        final List<U2> u2Rockets = new ArrayList<>();
        U2 u2 = new U2();
        for (Item item : items) {
            if (u2.canCarry(item)) {
                u2.carry(item);
            } else {
                LOGGER.info("[U2] Can not carry item. Max weight reached. Setting up chance of launch explosion and landing crash");
                u2.setChanceOfLaunchExplosion();
                u2.setChanceOfLandingCrash();
                LOGGER.info("[U2] Creating new U2 rocket");
                u2Rockets.add(u2);
                u2 = new U2();
                u2.carry(item);
            }
        }
        if (u2.getTotalWeight() > 0) {
            u2Rockets.add(u2);
        }
        return u2Rockets;
    }

    public <R extends Rocket> long runSimulation(List<R> rockets) {
        if (rockets.isEmpty()) {
            return 0L;
        }
        long trial = 0L;
        for (Rocket rocket : rockets) {
            while (!rocket.launch()) {
                rocket.launch();
                trial++;
            }
            while (!rocket.land()) {
                rocket.land();
                trial++;
            }
        }
        return rockets.get(0).getCost() * (trial + rockets.size());
    }

    private static Path getFilePathFromResource(String relativePath) {
        return Paths.get("src", "main", "resources", relativePath);
    }
}
