package com.learning.projects.missionspace.rockets;

import com.learning.projects.missionspace.data.Item;
import com.learning.projects.missionspace.exceptions.NotImplementedException;

public abstract class Rocket implements SpaceShip {

    @Override
    public boolean launch() {
        return true;
    }

    @Override
    public boolean land() {
        return true;
    }

    @Override
    public boolean canCarry(Item item) {
        int currentWeight = this.getTotalWeight();
        int maxWeight = this.getMaxWeight();
        return (currentWeight + item.getWeight()) <= maxWeight;
    }

    @Override
    public void carry(Item item) {
        this.increaseLoad(item.getWeight());
    }

    public abstract void increaseLoad(int weight);

    public int getTotalWeight() {
        throw new NotImplementedException("Calling abstract class method of getCurrentWeight");
    }

    public int getMaxWeight() {
        throw new NotImplementedException("Calling abstract class method of getMaxWeight");
    }

    public void setChanceOfLaunchExplosion() {
        throw new NotImplementedException("Calling abstract class method of setChanceLaunched");
    }

    public void setChanceOfLandingCrash() {
        throw new NotImplementedException("Calling abstract class method of setChanceLanded");
    }

    public int getCost() {
        throw new NotImplementedException("Calling abstract class method of getCost");
    }

}
