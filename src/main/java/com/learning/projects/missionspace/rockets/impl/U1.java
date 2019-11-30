package com.learning.projects.missionspace.rockets.impl;

import com.learning.projects.missionspace.rockets.Rocket;

import java.util.Objects;
import java.util.Random;

public class U1 extends Rocket {

    private static final int ROCKET_WEIGHT = 10 * 1000; // tonnes to kgs
    private static final int MAX_WEIGHT = 18 * 1000; // tonnes to kgs
    private static final int CARGO_WEIGHT = MAX_WEIGHT - ROCKET_WEIGHT; // in kgs
    private static final int COST = 100_000_000;

    private double chanceOfLaunchExplosion;
    private double chanceOfLandingCrash;
    private int totalWeight;

    public U1() {
        this.totalWeight = 0;
        this.chanceOfLaunchExplosion = 0.0;
        this.chanceOfLandingCrash = 0.0;
    }

    @Override
    public boolean launch() {
        return this.chanceOfLaunchExplosion <= new Random().nextDouble();
    }

    @Override
    public boolean land() {
        return this.chanceOfLandingCrash <= new Random().nextDouble();
    }

    @Override
    public void increaseLoad(int weight) {
        this.totalWeight += weight;
    }

    @Override
    public int getTotalWeight() {
        return this.totalWeight;
    }

    @Override
    public int getMaxWeight() {
        return MAX_WEIGHT;
    }

    @Override
    public void setChanceOfLaunchExplosion() {
        chanceOfLaunchExplosion = 0.05 * ((double) getTotalWeight() / getMaxWeight());
    }

    @Override
    public void setChanceOfLandingCrash() {
        chanceOfLandingCrash = 0.01 * ((double) getTotalWeight() / getMaxWeight());
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    public String toString() {
        return "U1{" +
                "chanceOfLaunchExplosion=" + chanceOfLaunchExplosion +
                ", chanceOfLandingCrash=" + chanceOfLandingCrash +
                ", totalWeight=" + totalWeight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        U1 u1 = (U1) o;
        return Double.compare(u1.chanceOfLaunchExplosion, chanceOfLaunchExplosion) == 0 &&
                Double.compare(u1.chanceOfLandingCrash, chanceOfLandingCrash) == 0 &&
                totalWeight == u1.totalWeight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chanceOfLaunchExplosion, chanceOfLandingCrash, totalWeight);
    }
}
