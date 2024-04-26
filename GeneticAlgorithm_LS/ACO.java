import java.util.ArrayList;
import java.util.Random;

/**
 * @file ACO.java
 * 
 * @author Selepe Sello
 * @date 26 April 2024
 * @version 1.0
 * @brief Ant Colony Optimization for the Knapsack Problem.
 */

public class ACO {
    // Member Variables

    private Integer seed;
    private Random random;

    private Knapsack knapsack;
    private ArrayList<Boolean[]> ants;
    private Boolean[] bestKnapsack;
    private double bestFitness;
    private int noImprovement = 0;
    private double timeTaken = 0;
    private int bestIteration = 0;

    // Ant Colony Optimization parameters (constants)
    private static final int MAX_ITERATIONS = 200;
    private static final int STOPPING_ITERATIONS = 100;
    private static final double ALPHA = 0.5;
    private static final double BETA = 2;
    private static final double EVAPORATION_RATE = 0.95;
    private static final double INITIAL_PHEROMONE = 0.5;
    private static final double UPDATE_STRENGTH = 0.5;

    /*
        0 = none,
        1 = Replace the worst item,
        2 = Assess all single item flips and choose the best
     */
    private static final int LS_METHOD = 0;

    // Ant Colony Optimization parameters (variables)
    int numAnts;
    private double[] pheromone;
    private double Q;

    public ACO(Knapsack initalKnapsack, Integer seed) {
        this.seed = seed;
        this.random = new Random(this.seed);

        double startTime = System.nanoTime();
        double previousBestFitness;

        knapsack = initalKnapsack;
        numAnts = knapsack.getItems().size();
        Q = knapsack.getCapacity() * UPDATE_STRENGTH;

        // Initialize the pheromone matrix
        pheromone = new double[knapsack.getItems().size()];
        for (int i = 0; i < knapsack.getItems().size(); i++) {
            pheromone[i] = INITIAL_PHEROMONE;
        }

        for (int i = 0; i < MAX_ITERATIONS; i++) {

            previousBestFitness = bestFitness;

            run();

            if (bestFitness > previousBestFitness) {
                bestIteration += noImprovement;
                noImprovement = 0;
            } else {
                noImprovement++;
                if (noImprovement > STOPPING_ITERATIONS) {
                    // reset the pheromone matrix
                    for (int j = 0; j < knapsack.getItems().size(); j++) {
                        pheromone[j] = INITIAL_PHEROMONE;
                    }
                }
            }
        }

        // seconds
        timeTaken = (System.nanoTime() - startTime) / 1000000000;
    }

    /**
     * @brief Run the ACO algorithm
     */
    public void run() {
        // Create new set of ants
        ants = new ArrayList<Boolean[]>();
        constructCandidateSolution();
        findBestSolution();
        updatePheromones();
        localSearch();
    }

    /**
     * @brief Construct candidate solutions
     */
    private void constructCandidateSolution() {

        for (int ant = 0; ant < numAnts; ant++) {
            Boolean[] solution = new Boolean[knapsack.getItems().size()];
            for (int i = 0; i < knapsack.getItems().size(); i++) {
                solution[i] = false;
            }

            double weight = 0;

            // Construct a solution. Keep adding items until the knapsack is full
            while (true) {
                // List of probabilities for each item
                Double[] probabilities = new Double[knapsack.getItems().size()];
                // Sum of probabilities
                double sum = 0;

                // Calculate the probability of selecting each item
                for (int i = 0; i < knapsack.getItems().size(); i++) {
                    if (!solution[i] && weight + knapsack.getItems().get(i).getWeight() <= knapsack.getCapacity()) {
                        probabilities[i] = decisionRule(i);
                        sum += probabilities[i];
                    } else {
                        probabilities[i] = 0.0;
                    }
                }

                // Generate a random number between 0 and sum
                double random = this.random.nextDouble() * sum;
                double cumulativeProbability = 0;
                int selected = -1;

                // Select an item. Items with higher probabilities make up a larger portion of
                // the sum and thus are more likely to be selected
                for (int i = 0; i < knapsack.getItems().size(); i++) {
                    if (probabilities[i] > 0) {
                        cumulativeProbability += probabilities[i];
                        if (cumulativeProbability >= random) {
                            selected = i;
                            break;
                        }
                    }
                }

                // No item was selected
                if (selected == -1) {
                    break;
                }

                // Add the item to the knapsack
                solution[selected] = true;
                weight += knapsack.getItems().get(selected).getWeight();
            }

            ants.add(solution);
        }
    }

    /**
     * @brief Calculate the decision probability for an item using the decision rule
     * 
     * @param item
     * @return probability
     */
    private double decisionRule(int item) {
        // Possibly change how the heuristic is defined
        double heuristic = knapsack.getItems().get(item).getValue() / knapsack.getItems().get(item).getWeight();
        return Math.pow(pheromone[item], ALPHA) * Math.pow(heuristic, BETA);
    }

    /**
     * @brief Update the pheromone matrix
     */
    private void updatePheromones() {
        for (int ant = 0; ant < numAnts; ant++) {
            double fitness = getSumFitness(ants.get(ant));

            double sumPheromone = 0;
            for (int j = 0; j < knapsack.getItems().size(); j++) {
                if (ants.get(ant)[j]) {
                    sumPheromone += pheromone[j];
                }
            }

            // Update the pheromone matrix
            for (int j = 0; j < knapsack.getItems().size(); j++) {
                if (ants.get(ant)[j]) {
                    pheromone[j] = pheromone[j] + (Q / fitness) * (pheromone[j] / sumPheromone);
                }
            }
        }

        for (int i = 0; i < knapsack.getItems().size(); i++) {
            pheromone[i] = (1 - EVAPORATION_RATE) * pheromone[i];
            if (pheromone[i] > 1) {
                pheromone[i] = 1;
            }
        }
    }

    /**
     * @brief Choose a local search method and run it
     */
    private void localSearch() {
        if (LS_METHOD == 1) {
            LSReplaceWorst();
        } else if (LS_METHOD == 2) {
            LSBestFlip();
        }

        findBestSolution();
    }

    /**
     * @brief Add remove one item and replace it with another
     */
    private void LSReplaceWorst() {
        for (int i = 0; i < numAnts; i++) {
            double oldFitness = getSumFitness(ants.get(i));

            // Find the best item to remove (the one with the lowest value to weight ratio)
            int remove = -1;
            double minRatio = Double.MAX_VALUE;
            for (int j = 0; j < knapsack.getItems().size(); j++) {
                if (ants.get(i)[j]) {
                    double ratio = knapsack.getItems().get(j).getValue() / knapsack.getItems().get(j).getWeight();
                    if (ratio < minRatio) {
                        minRatio = ratio;
                        remove = j;
                    }
                }
            }

            // find random item to add (one that is not already in the knapsack)
            ArrayList<Integer> outItems = new ArrayList<Integer>();
            for (int j = 0; j < knapsack.getItems().size(); j++) {
                if (!ants.get(i)[j]) {
                    outItems.add(j);
                }
            }

            if (remove == -1 || outItems.size() == 0) {
                continue;
            }

            int add = outItems.get((int) (this.random.nextDouble() * outItems.size()));

            // Remove the item with the lowest value to weight ratio and add a random item
            ants.get(i)[remove] = false;
            ants.get(i)[add] = true;

            double newFitness = getSumFitness(ants.get(i));

            // If the new solution is worse, revert back to the old solution
            if (newFitness < oldFitness) {
                ants.get(i)[remove] = true;
                ants.get(i)[add] = false;
            }
        }
    }

    /**
     * @brief Create a list of solutions by fliping every bit once and choose the
     *        best of these solutions
     */
    private void LSBestFlip() {
        for (int i = 0; i < numAnts; i++) {
            double oldFitness = getSumFitness(ants.get(i));
            double newFitness = 0;
            int fitnessIndex = -1;

            // Create a list of solutions by flipping each bit once
            Boolean[][] solutions = new Boolean[knapsack.getItems().size()][knapsack.getItems().size()];
            for (int j = 0; j < knapsack.getItems().size(); j++) {
                for (int k = 0; k < knapsack.getItems().size(); k++) {
                    solutions[j][k] = ants.get(i)[k];
                }
                solutions[j][j] = !solutions[j][j];
                newFitness = getSumFitness(solutions[j]);
                if (newFitness > oldFitness) {
                    fitnessIndex = j;
                    oldFitness = newFitness;
                }
            }

            if (fitnessIndex != -1) {
                ants.set(i, solutions[fitnessIndex]);
            }
        }
    }

    /**
     * @brief Helper function - Determines the fitness of a knapsack using the sum of the values of
     *        the knapsack
     */
    public double getSumFitness(Boolean[] solution) {
        double fitness = 0;
        if (knapsack.getWeight(solution) <= knapsack.getCapacity()) {
            fitness = knapsack.getValue(solution);
        }

        if (fitness % 1 > 0.0001) {
            fitness = Math.round(fitness * 10000.0) / 10000.0;
        }

        return fitness;
    }

    /**
     * @brief Helper function - finds the best solution in the list of ants
     */
    private void findBestSolution() {
        if (bestKnapsack == null) {
            bestKnapsack = ants.get(0);
        }

        bestFitness = getSumFitness(bestKnapsack);
        for (int i = 1; i < numAnts; i++) {
            double fitness = getSumFitness(ants.get(i));
            if (fitness > bestFitness) {
                bestKnapsack = ants.get(i);
                bestFitness = fitness;
            }
        }
    }

    /**
     * @brief Helper function - returns the best iteration
     */
    public int getBestIteration() {
        return bestIteration;
    }

    /**
     * @brief Helper function - returns the best fitness
     */
    public double getBestFitness() {
        return getSumFitness(bestKnapsack);
    }

    /**
     * @brief Helper function - returns the time taken to find the best solution
     */
    public double getTimeElapsed() {
        return timeTaken;
    }
}
