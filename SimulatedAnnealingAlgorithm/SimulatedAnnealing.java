import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @file SimulatedAnnealing.java
 * 
 * @author Selepe Sello
 * @date 29 March 2021
 * @version 1.0
 * @brief Simulated Annealing algorithm for solving the campus tour problem.
 */

public class SimulatedAnnealing {

    /**  Constants */
    private static final int NUM_CAMPUSES = 5;
    private static final int MAX_ITERATIONS = 100;
    private static final double INITIAL_TEMPERATURE = 1000;
    private static final double COOLING_RATE = 0.99;
    private static final Random random = new Random();

    /**
     * Runs the Simulated Annealing algorithm to find the best solution.
     * 
     * @return SolutionDetails containing the details of the best solution found
     */
    public SolutionDetails run() {
        // Initialize SolutionDetails to store all solutions and other details
        SolutionDetails solutionsList = new SolutionDetails();
        Solution currentSolution = generateInitialSolution();
        // solutionsList.addSolution(currentSolution);

        // Initialize temperature
        double temperature = INITIAL_TEMPERATURE;

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            long startTime = System.currentTimeMillis();
            
            // Perturb the current solution
            Solution newSolution = perturb(currentSolution);
            
            // Calculate the change in distance between current and new solution
            double deltaDistance = newSolution.getDistance() - currentSolution.getDistance();
            
            // Acceptance criterion based on the Metropolis criterion
            if (deltaDistance < 0 || Math.exp(-deltaDistance / temperature) > random.nextDouble()) {
                currentSolution = newSolution;
            }
            
            // Cool down the temperature
            temperature *= COOLING_RATE;
            
            long endTime = System.currentTimeMillis();
            long runtime = endTime - startTime;
            newSolution.setRuntime(runtime);
            currentSolution.setRuntime(runtime);

            solutionsList.addSolution(newSolution);

            // Update the best solution in the SolutionDetails
            if (currentSolution.isBetterThan(solutionsList.getBestSolution())) {
                solutionsList.setBestSolution(currentSolution);
            }

        }

        return solutionsList;
    }

    /**
     * Generates an initial solution by randomly shuffling campuses.
     * @param distances the distance matrix
     * @return the initial solution
     */
    private Solution generateInitialSolution() {
        List<Integer> campuses = new ArrayList<>();
        for (int i = 0; i < NUM_CAMPUSES; i++) {
            campuses.add(i);
        }
        // Adding the starting campus at the end
        campuses.add(0);

        // Randomly shuffle the campuses from index 1 to NUM_CAMPUSES
        Collections.shuffle(campuses.subList(1, NUM_CAMPUSES));
        return new Solution(campuses, 0);
    }

    /**
     * Perturbs the given solution to explore new solutions.
     * @param solution The solution to perturb.
     * @return The perturbed solution.
     */
    private Solution perturb(Solution solution) {
        int index1 = 1 + random.nextInt(NUM_CAMPUSES - 1);
        int index2;
        do {
            index2 = 1 + random.nextInt(NUM_CAMPUSES - 1);
        } while (index1 == index2);
        return solution.swapCampuses(index1, index2);
    }
}