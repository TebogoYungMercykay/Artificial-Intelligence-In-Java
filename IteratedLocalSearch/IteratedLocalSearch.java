import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @file IteratedLocalSearch.java
 * 
 * @author Selepe Sello
 * @date 29 March 2021
 * @version 1.0
 * @brief Class representing the Iterated Local Search algorithm for solving optimization problems.
 */

public class IteratedLocalSearch {

    /**  Constants */
    private static final int NUM_CAMPUSES = 5;
    private static final int MAX_ITERATIONS = 100;
    private static final Random random = new Random();

    /**
     * Runs the Iterated Local Search algorithm to find the best solution.
     * 
     * @return SolutionDetails containing the details of the best solution found
     */
    public SolutionDetails run() {
        // Initialize SolutionDetails to store all solutions and other details
        SolutionDetails solutionsList = new SolutionDetails();

        // Main loop of the Iterated Local Search algorithm
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            // Generate & Perform local search on the initial solution
            Solution currentSolution = generateInitialSolution();
            currentSolution = localSearch(currentSolution);

            long startTime = System.currentTimeMillis();

            // Perturb the current solution
            Solution newSolution = perturb(currentSolution);

            // Perform local search on the perturbed solution
            Solution newBestSolution = localSearch(newSolution);

            long endTime = System.currentTimeMillis();
            long runtime = endTime - startTime;
            newBestSolution.setRuntime(runtime);
            solutionsList.addSolution(newBestSolution);

            // Update the current solution if the new best solution is better
            if (newBestSolution.isBetterThan(currentSolution)) {
                currentSolution = newBestSolution;
            }

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
     * Performs local search to improve the given solution.
     * @param solution The solution to improve.
     * @return The improved solution.
     */
    private Solution localSearch(Solution solution) {
        Solution bestSolution = solution;
        boolean improved;
        do {
            improved = false;
            for (int i = 1; i < NUM_CAMPUSES; i++) {
                for (int j = i + 1; j <= NUM_CAMPUSES; j++) {
                    Solution newSolution = solution.twoOptSwap(i, j);
                    if (newSolution.isBetterThan(bestSolution)) {
                        bestSolution = newSolution;
                        improved = true;
                    }
                }
            }
            solution = bestSolution;
        } while (improved);
        return bestSolution;
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
