import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @file Solution.java
 * 
 * @author Selepe Sello
 * @date 29 March 2021
 * @version 1.0
 * @brief Simulated Annealing algorithm for solving the campus tour problem.
 */

public class SimulatedAnnealing {

    /**  Constants */
    private static final int NUM_CAMPUSES = 5;
    private static final int MAX_ITERATIONS = 1000;
    private static final double INITIAL_TEMPERATURE = 1000;
    private static final double COOLING_RATE = 0.99;

    /**  Distance matrix  */
    private static final int[][] DISTANCES = {
            {0, 15, 20, 22, 30},
            {15, 0, 10, 12, 25},
            {20, 10, 0, 8, 22},
            {22, 12, 8, 0, 18},
            {30, 25, 22, 18, 0}
    };

    /**
     * Runs the Simulated Annealing algorithm to find the best solution.
     * 
     * @return SolutionDetails containing the details of the best solution found
     */
    public SolutionDetails run() {
        // Initialize SolutionDetails to store all solutions and other details
        SolutionDetails solutionsList = new SolutionDetails();
        Random random = new Random();
        Solution currentSolution = generateInitialSolution();

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

            currentSolution.setRuntime(runtime);

            // Update the best solution in the SolutionDetails
            if (currentSolution.isBetterThan(solutionsList.getBestSolution())) {
                solutionsList.setBestSolution(currentSolution);
            }

            solutionsList.addSolution(currentSolution);
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
        this.shuffleRoute(campuses);

        return new Solution(campuses, 0);
    }

    /**
     * Shuffles the route randomly, excluding the start and end campuses.
     * @param route The route to shuffle.
     */
    private void shuffleRoute(List<Integer> route) {
        Random random = new Random();
        // Start from index 1 to exclude start and end campuses
        for (int i = 1; i < route.size() - 1; i++) {
            int j = random.nextInt(route.size() - 2) + 1; // Exclude the last index
            int temp = route.get(i);
            route.set(i, route.get(j));
            route.set(j, temp);
        }
    }

    /**
     * Perturbs the current solution by swapping two random campuses.
     * @param solution the current solution
     * @return the perturbed solution
     */
    private Solution perturb(Solution solution) {
        Random random = new Random();
        int index1 = random.nextInt(NUM_CAMPUSES);
        int index2;

        do {
            index2 = random.nextInt(NUM_CAMPUSES);
        } while (index1 == index2);
        return solution.swapCampuses(index1, index2);
    }
}