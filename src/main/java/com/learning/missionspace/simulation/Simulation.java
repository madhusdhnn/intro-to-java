package com.learning.missionspace.simulation;

import com.learning.missionspace.data.Item;
import com.learning.missionspace.rockets.Rocket;
import com.learning.missionspace.rockets.impl.U1;
import com.learning.missionspace.rockets.impl.U2;
import com.learning.utils.Console;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.learning.utils.FileUtils.readFile;

public class Simulation {

    public List<Item> loadItems(String relativePath) {
        Path filePath = getFilePathFromResource(relativePath);
        return readFile(filePath, lines -> lines
                .map(line -> line.split("="))
                .filter(itemNameAndWeightArray -> itemNameAndWeightArray.length > 0)
                .map(itemNameAndWeightArray -> new Item(itemNameAndWeightArray[0], Integer.parseInt(itemNameAndWeightArray[1])))
                .collect(Collectors.toList()));
    }

    public List<U1> loadU1(List<Item> items) {
        final List<U1> u1Rockets = new ArrayList<>();
        U1 u1 = new U1();
        for (Item item : items) {
            if (u1.canCarry(item)) {
                u1.carry(item);
            } else {
                Console.log("[U1] Can not carry item. Max weight reached. Setting up chance of launch explosion and landing crash");
                u1.setChanceOfLaunchExplosion();
                u1.setChanceOfLandingCrash();
                Console.log("[U1] Creating new U1 rocket");
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
                Console.log("[U2] Can not carry item. Max weight reached. Setting up chance of launch explosion and landing crash");
                u2.setChanceOfLaunchExplosion();
                u2.setChanceOfLandingCrash();
                Console.log("[U2] Creating new U2 rocket");
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
