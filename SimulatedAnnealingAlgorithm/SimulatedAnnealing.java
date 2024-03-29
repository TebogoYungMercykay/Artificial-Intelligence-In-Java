import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Simulated Annealing algorithm for solving the campus tour problem.
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
        Solution currentSolution = generateInitialSolution(DISTANCES);

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
    private Solution generateInitialSolution(int[][] distances) {
        List<Integer> campuses = new ArrayList<>();
        for (int i = 0; i < NUM_CAMPUSES; i++) {
            campuses.add(i);
        }

        // Randomly shuffle the campuses
        Collections.shuffle(campuses);
        return new Solution(campuses, 0);
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