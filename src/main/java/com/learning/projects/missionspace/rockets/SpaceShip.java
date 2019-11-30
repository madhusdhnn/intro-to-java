package com.learning.projects.missionspace.rockets;

import com.learning.projects.missionspace.data.Item;

public interface SpaceShip {

    boolean launch();

    boolean land();

    boolean canCarry(Item item);

    void carry(Item item);

}
