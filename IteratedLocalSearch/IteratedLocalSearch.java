import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing the Iterated Local Search algorithm for solving optimization problems.
 */
public class IteratedLocalSearch {

    /**  Constants */
    private static final int NUM_CAMPUSES = 5;
    private static final int MAX_ITERATIONS = 100;

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

            // Update the current solution if the new best solution is better
            if (newBestSolution.isBetterThan(currentSolution)) {
                currentSolution = newBestSolution;
            }

            // Update the best solution in the SolutionDetails
            if (currentSolution.isBetterThan(solutionsList.getBestSolution())) {
                solutionsList.setBestSolution(currentSolution);
            }

            solutionsList.addSolution(currentSolution);
        }

        return solutionsList;
    }

    /**
     * Generates the initial solution for the Iterated Local Search algorithm.
     * @return The initial solution.
     */
    private Solution generateInitialSolution() {
        List<Integer> route = new ArrayList<>();
        // Generate the route [0, 1, 2, 3, 4, 0]
        for (int i = 0; i <= NUM_CAMPUSES; i++) {
            route.add(i % (NUM_CAMPUSES + 1));
        }
        // Shuffle the route, excluding start and end campuses
        shuffleRoute(route);
        // Runtime is not used for initial solution
        return new Solution(route, 0);
    }

    /**
     * Shuffles the route randomly, excluding the start and end campuses.
     * @param route The route to shuffle.
     */
    private void shuffleRoute(List<Integer> route) {
        Random random = new Random();
        // Start from index 1 to exclude start and end campuses
        for (int i = 1; i < NUM_CAMPUSES; i++) {
            int j = random.nextInt(NUM_CAMPUSES - 1) + 1;
            int temp = route.get(i);
            route.set(i, route.get(j));
            route.set(j, temp);
        }
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
        Random random = new Random();
        int index1 = 1 + random.nextInt(NUM_CAMPUSES - 1);
        int index2;
        do {
            index2 = 1 + random.nextInt(NUM_CAMPUSES - 1);
        } while (index1 == index2);
        return solution.swapCampuses(index1, index2);
    }
}
