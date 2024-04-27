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

    // Member Functions for Ant Colony Optimization

    public ACO(Knapsack initalKnapsack, Integer seed) {
        this.seed = seed;
        this.random = new Random(this.seed);

        double startTime = System.nanoTime();
        double previousBestFitness;

        this.knapsack = initalKnapsack;
        this.numAnts = this.knapsack.getItems().size();
        this.Q = this.knapsack.getCapacity() * UPDATE_STRENGTH;

        // Initialize the pheromone matrix
        this.pheromone = new double[this.knapsack.getItems().size()];
        for (int i = 0; i < this.knapsack.getItems().size(); i++) {
            this.pheromone[i] = INITIAL_PHEROMONE;
        }

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            previousBestFitness = this.bestFitness;

            run();

            if (this.bestFitness > previousBestFitness) {
                this.bestIteration += this.noImprovement;
                this.noImprovement = 0;
            } else {
                this.noImprovement++;
                if (this.noImprovement > STOPPING_ITERATIONS) {
                    // reset the pheromone matrix
                    for (int j = 0; j < this.knapsack.getItems().size(); j++) {
                        this.pheromone[j] = INITIAL_PHEROMONE;
                    }
                }
            }
        }

        // seconds
        this.timeTaken = (System.nanoTime() - startTime) / 1000000000;
    }

    /**
     * @brief Run the ACO algorithm
     */
    public void run() {
        // Create new set of ants
        this.ants = new ArrayList<Boolean[]>();
        this.constructCandidateSolution();
        this.findBestSolution();
        this.updatePheromones();
        this.localSearch();
    }

    /**
     * @brief Construct candidate solutions
     */
    private void constructCandidateSolution() {

        for (int ant = 0; ant < this.numAnts; ant++) {
            Boolean[] solution = new Boolean[this.knapsack.getItems().size()];
            for (int i = 0; i < this.knapsack.getItems().size(); i++) {
                solution[i] = false;
            }

            double weight = 0;

            // Construct a solution. Keep adding items until the knapsack is full
            while (true) {
                // List of probabilities for each item
                Double[] probabilities = new Double[this.knapsack.getItems().size()];
                // Sum of probabilities
                double sum = 0;

                // Calculate the probability of selecting each item
                for (int i = 0; i < this.knapsack.getItems().size(); i++) {
                    if (!solution[i] && weight + this.knapsack.getItems().get(i).getWeight() <= this.knapsack.getCapacity()) {
                        probabilities[i] = this.decisionRule(i);
                        sum += probabilities[i];
                    } else {
                        probabilities[i] = 0.0;
                    }
                }

                // Generate a random number between 0 and sum
                double randomNum = this.random.nextDouble() * sum;
                double cumulativeProbability = 0;
                int selected = -1;

                // Select an item. Items with higher probabilities make up a larger portion of
                // the sum and thus are more likely to be selected
                for (int i = 0; i < this.knapsack.getItems().size(); i++) {
                    if (probabilities[i] > 0) {
                        cumulativeProbability += probabilities[i];
                        if (cumulativeProbability >= randomNum) {
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
                weight += this.knapsack.getItems().get(selected).getWeight();
            }

            this.ants.add(solution);
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
        double heuristic = this.knapsack.getItems().get(item).getValue() / this.knapsack.getItems().get(item).getWeight();
        return Math.pow(this.pheromone[item], ALPHA) * Math.pow(heuristic, BETA);
    }

    /**
     * @brief Update the pheromone matrix
     */
    private void updatePheromones() {
        for (int ant = 0; ant < this.numAnts; ant++) {
            double fitness = getSumFitness(this.ants.get(ant));

            double sumPheromone = 0;
            for (int j = 0; j < this.knapsack.getItems().size(); j++) {
                if (this.ants.get(ant)[j]) {
                    sumPheromone += this.pheromone[j];
                }
            }

            // Update the pheromone matrix
            for (int j = 0; j < this.knapsack.getItems().size(); j++) {
                if (this.ants.get(ant)[j]) {
                    this.pheromone[j] = this.pheromone[j] + (this.Q / fitness) * (this.pheromone[j] / sumPheromone);
                }
            }
        }

        for (int i = 0; i < this.knapsack.getItems().size(); i++) {
            this.pheromone[i] = (1 - EVAPORATION_RATE) * this.pheromone[i];
            if (this.pheromone[i] > 1) {
                this.pheromone[i] = 1;
            }
        }
    }

    /**
     * @brief Choose a local search method and run it
     */
    private void localSearch() {
        if (LS_METHOD == 1) {
            this.LSReplaceWorst();
        } else if (LS_METHOD == 2) {
            this.LSBestFlip();
        }

        this.findBestSolution();
    }

    /**
     * @brief Add remove one item and replace it with another
     */
    private void LSReplaceWorst() {
        for (int i = 0; i < this.numAnts; i++) {
            double oldFitness = this.getSumFitness(this.ants.get(i));

            // Find the best item to remove (the one with the lowest value to weight ratio)
            int remove = -1;
            double minRatio = Double.MAX_VALUE;
            for (int j = 0; j < this.knapsack.getItems().size(); j++) {
                if (this.ants.get(i)[j]) {
                    double ratio = this.knapsack.getItems().get(j).getValue() / this.knapsack.getItems().get(j).getWeight();
                    if (ratio < minRatio) {
                        minRatio = ratio;
                        remove = j;
                    }
                }
            }

            // find random item to add (one that is not already in the knapsack)
            ArrayList<Integer> outItems = new ArrayList<Integer>();
            for (int j = 0; j < this.knapsack.getItems().size(); j++) {
                if (!this.ants.get(i)[j]) {
                    outItems.add(j);
                }
            }

            if (remove == -1 || outItems.size() == 0) {
                continue;
            }

            int add = outItems.get((int) (this.random.nextDouble() * outItems.size()));

            // Remove the item with the lowest value to weight ratio and add a random item
            this.ants.get(i)[remove] = false;
            this.ants.get(i)[add] = true;

            double newFitness = this.getSumFitness(this.ants.get(i));

            // If the new solution is worse, revert back to the old solution
            if (newFitness < oldFitness) {
                this.ants.get(i)[remove] = true;
                this.ants.get(i)[add] = false;
            }
        }
    }

    /**
     * @brief Create a list of solutions by fliping every bit once and choose the
     *        best of these solutions
     */
    private void LSBestFlip() {
        for (int i = 0; i < this.numAnts; i++) {
            double oldFitness = this.getSumFitness(this.ants.get(i));
            double newFitness = 0;
            int fitnessIndex = -1;

            // Create a list of solutions by flipping each bit once
            Boolean[][] solutions = new Boolean[this.knapsack.getItems().size()][this.knapsack.getItems().size()];
            for (int j = 0; j < this.knapsack.getItems().size(); j++) {
                for (int k = 0; k < this.knapsack.getItems().size(); k++) {
                    solutions[j][k] = this.ants.get(i)[k];
                }
                solutions[j][j] = !solutions[j][j];
                newFitness = this.getSumFitness(solutions[j]);
                if (newFitness > oldFitness) {
                    fitnessIndex = j;
                    oldFitness = newFitness;
                }
            }

            if (fitnessIndex != -1) {
                this.ants.set(i, solutions[fitnessIndex]);
            }
        }
    }

    /**
     * @brief Helper function - Determines the fitness of a knapsack using the sum of the values of
     *        the knapsack
     */
    public double getSumFitness(Boolean[] solution) {
        double fitness = 0;
        if (this.knapsack.getWeight(solution) <= this.knapsack.getCapacity()) {
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
        if (this.bestKnapsack == null) {
            this.bestKnapsack = this.ants.get(0);
        }

        this.bestFitness = this.getSumFitness(this.bestKnapsack);
        for (int i = 1; i < this.numAnts; i++) {
            double fitness = this.getSumFitness(this.ants.get(i));
            if (fitness > this.bestFitness) {
                this.bestKnapsack = this.ants.get(i);
                this.bestFitness = fitness;
            }
        }
    }

    /**
     * @brief Helper function - returns the best iteration
     */
    public int getBestIteration() {
        return this.bestIteration;
    }

    /**
     * @brief Helper function - returns the best fitness
     */
    public double getBestFitness() {
        return this.getSumFitness(this.bestKnapsack);
    }

    /**
     * @brief Helper function - returns the time taken to find the best solution
     */
    public double getTimeElapsed() {
        return this.timeTaken;
    }

    public void printACOParameters(String state) {
        if (state.toUpperCase() == "INITIAL") {
            System.out.println("Ant Colony Optimization Parameters (Initial):");
        } else {
            System.out.println("Ant Colony Optimization Parameters (Final):");
        }
        System.out.println("Max Iterations: " + MAX_ITERATIONS);
        System.out.println("Stopping Iterations: " + STOPPING_ITERATIONS);
        System.out.println("Alpha: " + ALPHA);
        System.out.println("Beta: " + BETA);
        System.out.println("Evaporation Rate: " + EVAPORATION_RATE);
        System.out.println("Initial Pheromone: " + INITIAL_PHEROMONE);
        System.out.println("Update Strength: " + UPDATE_STRENGTH);
        System.out.println("Local Search Method: " + LS_METHOD);
    }
}
