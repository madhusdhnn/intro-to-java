package com.learning.projects.elevator.system;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class Building {

    private final Elevator elevator;

    public Elevator getElevator(int sourceFloor) {

        int currentFloor = this.elevator.getCurrentFloor();
        if (sourceFloor == currentFloor) {
            return this.elevator;
        }

        System.out.printf("No of floor(s) travelling before loading persons - %d%n", Math.abs(sourceFloor - currentFloor));
        return this.elevator.changeFloor(sourceFloor);
    }

}
