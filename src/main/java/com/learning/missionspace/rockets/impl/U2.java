package com.learning.missionspace.rockets.impl;

import com.learning.missionspace.rockets.Rocket;

import java.util.Objects;
import java.util.Random;

public class U2 extends Rocket {

    private static final int ROCKET_WEIGHT = 18 * 1000; // tonnes to kgs
    private static final int MAX_WEIGHT = 29 * 1000; // tonnes to kgs
    private static final int CARGO_WEIGHT = MAX_WEIGHT - ROCKET_WEIGHT; // in kgs
    private static final int COST = 120_000_000;

    private double chanceOfLaunchExplosion;
    private double chanceOfLandingCrash;
    private int totalWeight;

    public U2() {
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
        chanceOfLaunchExplosion = 0.04 * ((double) getTotalWeight() / getMaxWeight());
    }

    @Override
    public void setChanceOfLandingCrash() {
        chanceOfLandingCrash = 0.08 * ((double) getTotalWeight() / getMaxWeight());
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    public String toString() {
        return "U2{" +
                "chanceOfLaunchExplosion=" + chanceOfLaunchExplosion +
                ", chanceOfLandingCrash=" + chanceOfLandingCrash +
                ", totalWeight=" + totalWeight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        U2 u2 = (U2) o;
        return Double.compare(u2.chanceOfLaunchExplosion, chanceOfLaunchExplosion) == 0 &&
                Double.compare(u2.chanceOfLandingCrash, chanceOfLandingCrash) == 0 &&
                totalWeight == u2.totalWeight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chanceOfLaunchExplosion, chanceOfLandingCrash, totalWeight);
    }

}
