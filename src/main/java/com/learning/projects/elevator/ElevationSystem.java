package com.learning.projects.elevator;

import com.learning.projects.DesignProblem;
import com.learning.projects.elevator.system.Building;
import com.learning.projects.elevator.system.Elevator;

public class ElevationSystem implements DesignProblem {

    @Override
    public void run() {
        Elevator elevator = new Elevator(5);
        Building building = new Building(elevator);
        System.out.println(building);
        building.getElevator(2).elevate(4);
        System.out.println(building);
        building.getElevator(4).elevate(5);
        System.out.println(building);
        building.getElevator(0).elevate(3);
        System.out.println(building);
        building.getElevator(1).elevate(3);
        System.out.println(building);
    }
}
