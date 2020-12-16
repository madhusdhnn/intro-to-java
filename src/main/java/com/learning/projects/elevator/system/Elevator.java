package com.learning.projects.elevator.system;

import lombok.Getter;
import lombok.ToString;

import static com.learning.projects.elevator.ElevatorUtils.checkPreCondition;

@Getter
@ToString
public class Elevator {

    private int currentFloor;
    private Direction direction;
    private final int totalFloorsInBuilding;

    public Elevator(int totalFloors) {
        this.currentFloor = 0;
        this.direction = null;
        this.totalFloorsInBuilding = totalFloors;
    }

    public void elevate(int destinationFloor) {
        checkPreCondition(destinationFloor < 0, "Floor can't be negative");
        checkPreCondition(destinationFloor > this.totalFloorsInBuilding, "Floor can't be greater than total floors in the building");

        if (destinationFloor == this.currentFloor) {
            System.out.println("Same floor!");
            return;
        }

        int cFloor = this.currentFloor;
        if (destinationFloor > cFloor) {
            for (int i = 1; i <= destinationFloor - cFloor; i++) {
                this.currentFloor += 1;
            }
            this.direction = isTopFloor(destinationFloor) ? Direction.DOWN : Direction.UP;
        } else {
            for (int i = cFloor; i > destinationFloor; i--) {
                this.currentFloor -= 1;
            }
            this.direction = isGroundFloor(destinationFloor) ? Direction.UP : Direction.DOWN;
        }
    }

    private boolean isTopFloor(int floor) {
        return floor == this.totalFloorsInBuilding;
    }

    private boolean isGroundFloor(int floor) {
        return floor == 0;
    }

    public void changeDirection(Direction direction) {
        this.direction = direction;
    }

    Elevator changeFloor(int floor) {
        checkPreCondition(floor < 0, "Floor can't be negative");
        checkPreCondition(floor > this.totalFloorsInBuilding, "Floor can't be greater than total floors in the building");
        this.currentFloor = floor;
        return this;
    }

}
