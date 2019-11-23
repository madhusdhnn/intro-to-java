package com.learning.missionspace;

import com.learning.missionspace.data.Item;
import com.learning.missionspace.rockets.impl.U1;
import com.learning.missionspace.rockets.impl.U2;
import com.learning.missionspace.simulation.Simulation;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getSimpleName());

    private static final String PHASE_ONE_FILE = "phase-1.txt";
    private static final String PHASE_ONE_FILE_PATH = "data" + File.separator + PHASE_ONE_FILE;
    private static final String PHASE_TWO_FILE = "phase-2.txt";
    private static final String PHASE_TWO_FILE_PATH = "data" + File.separator + PHASE_TWO_FILE;

    public static void main(String[] args) {
        final Simulation simulation = new Simulation();

        LOGGER.info("Loading Phase-1 items");
        List<Item> phaseOneItems = simulation.loadItems(PHASE_ONE_FILE_PATH);

        LOGGER.info("Loading Phase-2 items");
        List<Item> phaseTwoItems = simulation.loadItems(PHASE_TWO_FILE_PATH);

        LOGGER.info("Creating U1 rockets with phase-1 items");
        List<U1> phaseOneU1Rockets = simulation.loadU1(phaseOneItems);

        LOGGER.info("Creating U1 rockets with phase-2 items");
        List<U1> phaseTwoU1Rockets = simulation.loadU1(phaseTwoItems);

        long budgetForPhaseOneU1Rockets = simulation.runSimulation(phaseOneU1Rockets);
        String phaseOneU1BudgetMessage = String.format("Budget for phase-1 items with U1 rockets $ %d (millions)", budgetForPhaseOneU1Rockets);
        LOGGER.info(phaseOneU1BudgetMessage);

        long budgetForPhaseTwoU1Rockets = simulation.runSimulation(phaseTwoU1Rockets);
        String phaseTwoU1BudgetMessage = String.format("Budget for phase-2 items with U1 rockets $ %d (millions)", budgetForPhaseTwoU1Rockets);
        LOGGER.info(phaseTwoU1BudgetMessage);

        LOGGER.info("Creating U2 rockets with phase-1 items");
        List<U2> phaseOneU2Rockets = simulation.loadU2(phaseOneItems);

        LOGGER.info("Creating U2 rockets with phase-2 items");
        List<U2> phaseTwoU2Rockets = simulation.loadU2(phaseTwoItems);

        long budgetForPhaseOneU2Rockets = simulation.runSimulation(phaseOneU2Rockets);
        String phaseOneU2BudgetMessage = String.format("Budget for phase-1 items with U2 rockets $ %d (millions)", budgetForPhaseOneU2Rockets);
        LOGGER.info(phaseOneU2BudgetMessage);

        long budgetForPhaseTwoU2Rockets = simulation.runSimulation(phaseTwoU2Rockets);
        String phaseTwoU2BudgetMessage = String.format("Budget for phase-2 items with U2 rockets $ %d (millions)", budgetForPhaseTwoU2Rockets);
        LOGGER.info(phaseTwoU2BudgetMessage);

        String resultU1Message = String.format("Total budget for U1 rockets with Phase-1 and Phase-2 items is - $ %d (millions)", (budgetForPhaseOneU1Rockets + budgetForPhaseTwoU1Rockets));
        LOGGER.info(resultU1Message);

        String resultU2Message = String.format("Total budget for U2 rockets with Phase-1 and Phase-2 items is - $ %d (millions)", (budgetForPhaseOneU2Rockets + budgetForPhaseTwoU2Rockets));
        LOGGER.info(resultU2Message);
    }

}
