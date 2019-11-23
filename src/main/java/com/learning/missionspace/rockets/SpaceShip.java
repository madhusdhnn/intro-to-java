package com.learning.missionspace.rockets;

import com.learning.missionspace.data.Item;

public interface SpaceShip {

    boolean launch();

    boolean land();

    boolean canCarry(Item item);

    void carry(Item item);

}
