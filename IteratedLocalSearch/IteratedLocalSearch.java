import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class IteratedLocalSearch {

    private static final int NUM_CAMPUSES = 5;
    private static final int MAX_ITERATIONS = 1000;

    private static final int[][] DISTANCES = {
            {0, 15, 20, 22, 30},
            {15, 0, 10, 12, 25},
            {20, 10, 0, 8, 22},
            {22, 12, 8, 0, 18},
            {30, 25, 22, 18, 0}
    };

    public SolutionDetails run() {
        SolutionDetails allDetails = new SolutionDetails();

        // Main loop of ILS
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            // Generating an initial solution
            long startTime = System.currentTimeMillis();

            Solution currentSolution = generateInitialSolution(DISTANCES);

            // Performing local search on the initial solution
            currentSolution = localSearch(currentSolution);
            // Applying perturbation
            Solution newSolution = perturb(currentSolution);

            // Performing local search on the perturbed solution
            newSolution = localSearch(newSolution);

            // Updating current solution if the new solution is better
            if (newSolution.getDistance() < currentSolution.getDistance()) {
                currentSolution = newSolution;
            }
            
            long endTime = System.currentTimeMillis();
            long runtime = endTime - startTime;

            System.out.println("Start Time" + startTime + ", End Time: " + endTime + " (" + runtime + " ms)");
            currentSolution.setRuntime(runtime);

            // Adding solution details to the list
            allDetails.addSolution(currentSolution);
        }

        return allDetails;
    }

    private Solution generateInitialSolution(int[][] distances) {
        List<Integer> campuses = new ArrayList<>();
        for (int i = 0; i < NUM_CAMPUSES; i++) {
            campuses.add(i);
        }
        // Randomly shuffle the campuses
        Collections.shuffle(campuses);
        return new Solution(campuses, 0);
    }

    private Solution localSearch(Solution solution) {
        // Applying 2-opt local search
        Solution bestSolution = solution;
        boolean improved;
        do {
            improved = false;
            for (int i = 0; i < NUM_CAMPUSES - 1; i++) {
                for (int j = i + 1; j < NUM_CAMPUSES; j++) {
                    Solution newSolution = solution.twoOptSwap(i, j);
                    if (newSolution.getDistance() < bestSolution.getDistance()) {
                        bestSolution = newSolution;
                        improved = true;
                    }
                }
            }
            solution = bestSolution;
        } while (improved);
        return bestSolution;
    }

    private Solution perturb(Solution solution) {
        // Applying perturbation by swapping two random campuses
        Random random = new Random();
        int index1 = random.nextInt(NUM_CAMPUSES);
        int index2;
        do {
            index2 = random.nextInt(NUM_CAMPUSES);
        } while (index1 == index2);
        return solution.swapCampuses(index1, index2);
    }
}
