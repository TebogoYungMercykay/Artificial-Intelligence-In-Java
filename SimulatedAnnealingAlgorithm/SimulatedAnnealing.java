import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {
    private static final int NUM_CAMPUSES = 5;
    private static final int MAX_ITERATIONS = 1000;
    private static final int[][] DISTANCES = {
            {0, 15, 20, 22, 30},
            {15, 0, 10, 12, 25},
            {20, 10, 0, 8, 22},
            {22, 12, 8, 0, 18},
            {30, 25, 22, 18, 0}
    };

    public List<SolutionDetails> run() {
        List<SolutionDetails> allDetails = new ArrayList<>();

        // Main loop of Simulated Annealing
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            // Generating an initial solution
            
            Solution currentSolution = generateInitialSolution(DISTANCES);
            
            long startTime = System.currentTimeMillis();

            // Performing simulated annealing
            Solution newSolution = simulatedAnnealing(currentSolution);

            long endTime = System.currentTimeMillis();

            long runtime = endTime - startTime;

            allDetails.add(new SolutionDetails(newSolution, runtime));
        }

        return allDetails;
    }

    private Solution generateInitialSolution(int[][] distances) {
        List<Integer> campuses = new ArrayList<>();
        for (int i = 0; i < NUM_CAMPUSES; i++) {
            campuses.add(i);
        }
        Collections.shuffle(campuses); // Randomly shuffle the campuses
        return new Solution(campuses, distances);
    }

    private Solution simulatedAnnealing(Solution solution) {
        Random random = new Random();
        double temperature = 10000;
        double coolingRate = 0.003;

        Solution bestSolution = new Solution(solution.getRoute(), DISTANCES);
        int bestDistance = bestSolution.getDistance();

        while (temperature > 1) {
            Solution newSolution = new Solution(solution.getRoute(), DISTANCES);
            perturb(newSolution.getRoute());

            int currentDistance = solution.getDistance();
            int newDistance = newSolution.getDistance();

            if (acceptanceProbability(currentDistance, newDistance, temperature, random) > random.nextDouble()) {
                solution = new Solution(newSolution.getRoute(), DISTANCES);
            }

            if (newDistance < bestDistance) {
                bestSolution = new Solution(newSolution.getRoute(), DISTANCES);
                bestDistance = newDistance;
            }

            temperature *= (1 - coolingRate);
        }

        return bestSolution;
    }

    private void perturb(List<Integer> route) {
        Random random = new Random();
        int index1 = random.nextInt(route.size());
        int index2;
        do {
            index2 = random.nextInt(route.size());
        } while (index1 == index2);
        Collections.swap(route, index1, index2);
    }

    private double acceptanceProbability(int currentDistance, int newDistance, double temperature, Random random) {
        if (newDistance < currentDistance) {
            return 1.0;
        }
        return Math.exp((currentDistance - newDistance) / temperature);
    }
}
