import java.util.ArrayList;
import java.util.Random;

/**
 * @file GA.java
 * 
 * @author Selepe Sello
 * @date 26 April 2024
 * @version 1.0
 * @brief Genetic Algorithm for the Knapsack Problem.
 */

public class GA {
    // Member Variables

    private Integer seed;
    private Random random;

    private Knapsack knapsack;
    private ArrayList<Boolean[]> knapsackPopulation;
    private ArrayList<Boolean[]> nextGenerationPopulation;
    private Boolean[] bestKnapsack;
    private double bestFitness;
    private int noImprovement = 0;
    private double averageFitness = 0;
    private long timeTaken = 0;
    private ArrayList<Boolean[]> winners;
    private int bestIteration = 0;

    // Genetic Algorithm parameters
    private static final int POPULATION_MULTIPLIER = 8;
    private static final double CROSSOVER_RATE = 0.3;
    private static final double MUTATION_RATE = 0.4;
    private static final int MAX_GENERATIONS = 500;
    private static final int STOPPING_ITERATIONS = 250;
    private static final int PENALTY_FACTOR = 10;
    private static final double TOURNAMENT_PORTION = 0.2;
    private static final double INITIAL_BIT_PROBABILITY = 0.1;

    // Genetic Algorithm parameters (variables)
    private int populationSize;
    private int tournamentSize;

    /**
     * @brief Constructor for the GA class.
     * @param initialKnapsack The initial knapsack.
     */
    public GA(Knapsack initialKnapsack, Integer seed) {
        this.seed = seed;
        this.random = new Random(this.seed);

        long startTime = System.nanoTime();

        // Create the initial population
        this.knapsack = initialKnapsack;
        this.populationSize = (int)(initialKnapsack.getItems().size() * POPULATION_MULTIPLIER);
        this.tournamentSize = (int)(this.populationSize * TOURNAMENT_PORTION);
        if (this.tournamentSize < 2) {
            this.tournamentSize = 2;
        } else if (this.tournamentSize % 2 == 1) {
            this.tournamentSize++;
        }

        this.knapsackPopulation = new ArrayList<>();

        for (int i = 0; i < this.populationSize; i++) {
            Boolean[] chromosome = new Boolean[this.knapsack.getItems().size()];
            for (int j = 0; j < this.knapsack.getItems().size(); j++) {
                chromosome[j] = this.random.nextDouble() < INITIAL_BIT_PROBABILITY;
            }
            this.knapsackPopulation.add(chromosome);
        }

        for (int i = 0; i < MAX_GENERATIONS; i++) {
            if (this.noImprovement >= STOPPING_ITERATIONS) {
                break;
            }
            run();
        }

        setBestKnapsack();
        this.timeTaken = (System.nanoTime() - startTime) / 1000000000;
    }

    /**
     * @brief Run the GA algorithm.
     */
    public void run() {
        tournamentSelection();
        onePointCrossover();
        bitFlipMutation();
        replacePopulation();
        this.knapsackPopulation = this.nextGenerationPopulation;
    }

    /**
     * @brief Selects a knapsack from the population using tournament selection.
     */
    public void tournamentSelection() {
        this.winners = new ArrayList<>();

        while (this.knapsackPopulation.size() > 0 && this.winners.size() < this.populationSize) {
            // Select random knapsacks from the population to compete in the tournament
            ArrayList<Boolean[]> competitors = new ArrayList<>();
            for (int i = 0; i < this.tournamentSize; i++) {
                int randomIndex = (int)(this.random.nextDouble() * this.knapsackPopulation.size());
                competitors.add(this.knapsackPopulation.get(randomIndex));
            }

            Boolean[] winner = new Boolean[this.knapsack.getItems().size()];
            double winnerFitness = -1 * Double.MAX_VALUE;

            for (Boolean[] competitor : competitors) {
                double competitorFitness = getSumFitness(competitor);
                if (competitorFitness > winnerFitness) {
                    winner = competitor;
                    winnerFitness = competitorFitness;
                }
            }

            this.winners.add(winner);
        }
    }

    /**
     * @brief Performs one point crossover on two knapsacks.
     */
    public void onePointCrossover() {
        this.nextGenerationPopulation = new ArrayList<>();

        for (int i = 0; i < this.winners.size(); i += 2) {
            Boolean[] parent1 = this.winners.get(i);
            Boolean[] parent2 = this.winners.get(i + 1);

            // Determine if crossover will occur
            if (this.random.nextDouble() < CROSSOVER_RATE) {
                // Determine the crossover point
                int crossoverPoint = (int)(this.random.nextDouble() * this.knapsack.getItems().size());

                // Perform crossover
                Boolean[] child1 = new Boolean[this.knapsack.getItems().size()];
                Boolean[] child2 = new Boolean[this.knapsack.getItems().size()];

                for (int j = 0; j < crossoverPoint; j++) {
                    child1[j] = parent1[j];
                    child2[j] = parent2[j];
                }

                for (int j = crossoverPoint; j < this.knapsack.getItems().size(); j++) {
                    child1[j] = parent2[j];
                    child2[j] = parent1[j];
                }

                this.nextGenerationPopulation.add(child1);
                this.nextGenerationPopulation.add(child2);
            } else {
                this.nextGenerationPopulation.add(parent1);
                this.nextGenerationPopulation.add(parent2);
            }
        }
    }

    /**
     * @brief Performs bit flip mutation on a chromosome.
     */
    public void bitFlipMutation() {
        for (Boolean[] chromosome : this.nextGenerationPopulation) {
            // Determine if mutation will occur
            if (this.random.nextDouble() < MUTATION_RATE) {
                // Determine the mutation point
                int mutationPoint = (int)(this.random.nextDouble() * this.knapsack.getItems().size());
                // Perform mutation
                chromosome[mutationPoint] = !chromosome[mutationPoint];
            }
        }
    }

    /**
     * @brief Replaces the old population with the new population, or keeps the old
     * population if the new population is worse.
     */
    public void replacePopulation() {
        double currentAverageFitness = getAverageFitness();

        if (currentAverageFitness > this.averageFitness) {
            this.averageFitness = currentAverageFitness;
            this.bestIteration += this.noImprovement;
            this.noImprovement = 0;
            // Clear out the old population
            this.knapsackPopulation = new ArrayList<>();
        } else {
            this.noImprovement++;

            while (this.nextGenerationPopulation.size() < this.populationSize) {
                Boolean[] chromosome = new Boolean[this.knapsack.getItems().size()];
                for (int j = 0; j < this.knapsack.getItems().size(); j++) {
                    chromosome[j] = this.random.nextDouble() < 0.5;
                }
                this.nextGenerationPopulation.add(chromosome);
            }

            this.knapsackPopulation = this.nextGenerationPopulation;
        }
    } 

    /**
     * @brief Helper Function - Find the best knapsack in the population and set it as the best
     * knapsack, also set the best fitness.
     */
    public void setBestKnapsack() {
        int bestIndex = 0;
        double currBestFitness = 0;

        for (int i = 0; i < this.populationSize; i++) {
            double fitness = getSumFitness(this.knapsackPopulation.get(i));
            if (fitness > currBestFitness) {
                currBestFitness = fitness;
                bestIndex = i;
            }
        }

        this.bestKnapsack = this.knapsackPopulation.get(bestIndex);
        this.bestFitness = currBestFitness;
    }

    /**
     * @brief Helper Function - Gets the best knapsack and returns it.
     * @return The best knapsack.
     */
    public Boolean[] getBestKnapsack() {
        return this.bestKnapsack;
    }

    /**
     * @brief Helper Function - Gets the best fitness and returns it.
     * @return The best fitness.
     */
    public double getBestFitness() {
        return this.bestFitness;
    }

    /**
     * @brief Helper Function - Gets the best iteration and returns it.
     * @return The best iteration.
     */
    public int getBestIteration() {
        return this.bestIteration;
    }

    /**
     * @brief Helper Function - Determines the fitness of a knapsack using a penalty if the weight
     * exceeds the capacity.
     * @param chromosome The chromosome.
     * @return The fitness.
     */
    public double getPenaltyFitness(Boolean[] chromosome) {
        double weight = this.knapsack.getWeight(chromosome);
        double penalty = Math.max(0, weight - this.knapsack.getCapacity()) * PENALTY_FACTOR;

        return this.knapsack.getValue(chromosome) - penalty;
    }

    /**
     * @brief Helper Function - Determines the fitness of a knapsack by summing the value of the items in the knapsack.
     * If the knapsack is over capacity, the fitness is 0. A higher fitness is better.
     * @param chromosome
     * @return fitness
     */
    public double getSumFitness(Boolean[] chromosome) {
        double fitness = 0;

        if (this.knapsack.getWeight(chromosome) <= this.knapsack.getCapacity()) {
            fitness = this.knapsack.getValue(chromosome);
        }

        if (fitness % 1 > 0.0001) {
            return Math.round(fitness * 10000.0) / 10000.0;
        }

        return fitness;
    }

    /**
     * @brief Helper Function - Determines the average fitness of the population.
     * @return The average fitness.
     */
    public double getAverageFitness() {
        double totalFitness = 0;
        for (Boolean[] individual : this.knapsackPopulation) {
            totalFitness += getPenaltyFitness(individual);
        }

        return totalFitness / this.populationSize;
    }

    /**
     * @brief Helper Function - Get the time elapsed.
     * @return The time elapsed.
     */
    public double getTimeElapsed() {
        return this.timeTaken;
    }

    /**
     * Get the seed value.
     * @return The seed value.
     */
    public double getSeedValue() {
        return this.seed;
    }

    /**
     * @brief Helper Function - Create a deep copy of a chromosome.
     * @param chromosome The chromosome.
     * @return The copy of the chromosome.
     */
    public Boolean[] copyChromosome(Boolean[] chromosome) {
        Boolean[] copy = new Boolean[chromosome.length];
        for (int i = 0; i < chromosome.length; i++) {
            copy[i] = chromosome[i];
        }

        return copy;
    }

    public void printGAParameters(String state) {
        if (state.toUpperCase() == "INITIAL") {
            System.out.println("Genetic Algorithm Parameters (Initial):");
        } else {
            System.out.println("Genetic Algorithm Parameters (Final):");
        }
        System.out.println("Population Multiplier: " + POPULATION_MULTIPLIER);
        System.out.println("Crossover Rate: " + CROSSOVER_RATE);
        System.out.println("Mutation Rate: " + MUTATION_RATE);
        System.out.println("Max Generations: " + MAX_GENERATIONS);
        System.out.println("Stopping Iterations: " + STOPPING_ITERATIONS);
        System.out.println("Penalty Factor: " + PENALTY_FACTOR);
        System.out.println("Tournament Portion: " + TOURNAMENT_PORTION);
        System.out.println("Initial Bit Probability: " + INITIAL_BIT_PROBABILITY);
    }
}
