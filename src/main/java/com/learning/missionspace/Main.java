package com.learning.missionspace;

import com.learning.missionspace.data.Item;
import com.learning.missionspace.rockets.impl.U1;
import com.learning.missionspace.rockets.impl.U2;
import com.learning.missionspace.simulation.Simulation;
import com.learning.utils.Console;

import java.io.File;
import java.util.List;

public class Main {

    private static final String PHASE_ONE_FILE = "phase-1.txt";
    private static final String PHASE_ONE_FILE_PATH = "data" + File.separator + PHASE_ONE_FILE;
    private static final String PHASE_TWO_FILE = "phase-2.txt";
    private static final String PHASE_TWO_FILE_PATH = "data" + File.separator + PHASE_TWO_FILE;

    public static void main(String[] args) {
        final Simulation simulation = new Simulation();

        Console.log("Loading Phase-1 items");
        List<Item> phaseOneItems = simulation.loadItems(PHASE_ONE_FILE_PATH);

        Console.log("Loading Phase-2 items");
        List<Item> phaseTwoItems = simulation.loadItems(PHASE_TWO_FILE_PATH);

        Console.log("Creating U1 rockets with phase-1 items");
        List<U1> phaseOneU1Rockets = simulation.loadU1(phaseOneItems);

        Console.log("Creating U1 rockets with phase-2 items");
        List<U1> phaseTwoU1Rockets = simulation.loadU1(phaseTwoItems);

        long budgetForPhaseOneU1Rockets = simulation.runSimulation(phaseOneU1Rockets);
        String phaseOneU1BudgetMessage = String.format("Budget for phase-1 items with U1 rockets $ %d (millions)", budgetForPhaseOneU1Rockets);
        Console.log(phaseOneU1BudgetMessage);

        long budgetForPhaseTwoU1Rockets = simulation.runSimulation(phaseTwoU1Rockets);
        String phaseTwoU1BudgetMessage = String.format("Budget for phase-2 items with U1 rockets $ %d (millions)", budgetForPhaseTwoU1Rockets);
        Console.log(phaseTwoU1BudgetMessage);

        Console.log("Creating U2 rockets with phase-1 items");
        List<U2> phaseOneU2Rockets = simulation.loadU2(phaseOneItems);

        Console.log("Creating U2 rockets with phase-2 items");
        List<U2> phaseTwoU2Rockets = simulation.loadU2(phaseTwoItems);

        long budgetForPhaseOneU2Rockets = simulation.runSimulation(phaseOneU2Rockets);
        String phaseOneU2BudgetMessage = String.format("Budget for phase-1 items with U2 rockets $ %d (millions)", budgetForPhaseOneU2Rockets);
        Console.log(phaseOneU2BudgetMessage);

        long budgetForPhaseTwoU2Rockets = simulation.runSimulation(phaseTwoU2Rockets);
        String phaseTwoU2BudgetMessage = String.format("Budget for phase-2 items with U2 rockets $ %d (millions)", budgetForPhaseTwoU2Rockets);
        Console.log(phaseTwoU2BudgetMessage);

        String resultU1Message = String.format("Total budget for U1 rockets with Phase-1 and Phase-2 items is - $ %d (millions)", (budgetForPhaseOneU1Rockets + budgetForPhaseTwoU1Rockets));
        Console.log(resultU1Message);

        String resultU2Message = String.format("Total budget for U2 rockets with Phase-1 and Phase-2 items is - $ %d (millions)", (budgetForPhaseOneU2Rockets + budgetForPhaseTwoU2Rockets));
        Console.log(resultU2Message);
    }

}
