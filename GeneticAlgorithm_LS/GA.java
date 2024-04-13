import java.util.ArrayList;

// Genetic Algorithm For the Knapsack Problem
public class GA extends Helper {

    Knapsack knapsack;
    ArrayList<Boolean[]> knapsackPopulation;
    ArrayList<Boolean[]> nextGenerationPopulation;
    Boolean[] bestKnapsack;
    double bestFitness;
    int noImprovement = 0;
    double averageFitness = 0;
    long timeTaken = 0;

    ArrayList<Boolean[]> winners;

    int bestIteration = 0;

    // GA Parameters (constants)
    final int POPULATION_MULTIPLIER = 8;
    final double CROSSOVER_RATE = 0.3;
    final double MUTATION_RATE = 0.4;
    final int MAX_GENERATIONS = 500;
    final int STOPPING_ITERATIONS = 250;
    final int PENALTY_FACTOR = 10;
    final double TOURNAMENT_PORTION = 0.2;
    final double INITIAL_BIT_PROBABILITY = 0.1;

    // GA Parameters (variables)
    int populationSize;
    int tournamentSize;

    public GA(Knapsack initalKnapsack) {

        long startTime = System.nanoTime();

        // Create the initial population
        knapsack = initalKnapsack;

        populationSize = (int) (initalKnapsack.getItems().size() * POPULATION_MULTIPLIER);
        tournamentSize = (int) (populationSize * TOURNAMENT_PORTION);
        if (tournamentSize < 2) {
            tournamentSize = 2;
        } else if (tournamentSize % 2 == 1) {
            tournamentSize++;
        }

        knapsackPopulation = new ArrayList<Boolean[]>();

        for (int i = 0; i < populationSize; i++) {
            Boolean[] chromosome = new Boolean[knapsack.getItems().size()];
            for (int j = 0; j < knapsack.getItems().size(); j++) {
                chromosome[j] = Math.random() < INITIAL_BIT_PROBABILITY;
            }
            knapsackPopulation.add(chromosome);
        }

        for (int i = 0; i < MAX_GENERATIONS; i++) {

            if (noImprovement >= STOPPING_ITERATIONS) {
                break;
            }

            run();

        }

        setBestKnapsack();

        timeTaken = (System.nanoTime() - startTime) / 1000000000;
    }

    /**
     * @brief Run the GA algorithm
     */
    public void run() {

        // Select two knapsacks from the population
        // System.out.println("Tournament Phase");
        tournamentSelection();

        // Perform crossover on the two knapsacks
        // System.out.println("Crossover Phase");
        onePointCrossover();

        // Perform mutation on the two knapsacks
        // System.out.println("Mutation Phase");
        bitFlipMutation();

        // Fill the rest of the new population with random knapsacks
        // System.out.println("Random Phase");
        replacePopulation();

        // Replace the old population with the new population
        knapsackPopulation = nextGenerationPopulation;

    }

    /**
     * @brief Selects a knapsack from the population using tournament selection
     */
    public void tournamentSelection() {

        winners = new ArrayList<Boolean[]>();

        while (knapsackPopulation.size() > 0 && winners.size() < populationSize) {

            // Select random knapsacks from the population to compete in the tournament
            ArrayList<Boolean[]> competitors = new ArrayList<Boolean[]>();
            for (int i = 0; i < tournamentSize; i++) {
                int randomIndex = (int) (Math.random() * knapsackPopulation.size());
                competitors.add(knapsackPopulation.get(randomIndex));
            }

            Boolean[] winner = new Boolean[knapsack.getItems().size()];
            double winnerFitness = -1 * Double.MAX_VALUE;

            for (int i = 0; i < competitors.size(); i++) {
                double competitorFitness = getSumFitness(competitors.get(i));
                if (competitorFitness > winnerFitness) {
                    winner = competitors.get(i);
                    winnerFitness = competitorFitness;
                }
            }

            winners.add(winner);
        }

    }

    /**
     * @brief Performs one point crossover on two knapsacks
     */
    public void onePointCrossover() {

        nextGenerationPopulation = new ArrayList<Boolean[]>();

        for (int i = 0; i < winners.size(); i += 2) {

            Boolean[] parent1 = winners.get(i);
            Boolean[] parent2 = winners.get(i + 1);

            // Determine if crossover will occur
            if (Math.random() < CROSSOVER_RATE) {

                // Determine the crossover point
                int crossoverPoint = (int) (Math.random() * knapsack.getItems().size());

                // Perform crossover
                Boolean[] child1 = new Boolean[knapsack.getItems().size()];
                Boolean[] child2 = new Boolean[knapsack.getItems().size()];

                for (int j = 0; j < crossoverPoint; j++) {
                    child1[j] = parent1[j];
                    child2[j] = parent2[j];
                }

                for (int j = crossoverPoint; j < knapsack.getItems().size(); j++) {
                    child1[j] = parent2[j];
                    child2[j] = parent1[j];
                }

                nextGenerationPopulation.add(child1);
                nextGenerationPopulation.add(child2);

            } else {

                nextGenerationPopulation.add(parent1);
                nextGenerationPopulation.add(parent2);

            }

        }

    }

    /**
     * @brief Performs bit flip mutation on a chromosome
     */
    public void bitFlipMutation() {

        for (int i = 0; i < nextGenerationPopulation.size(); i++) {

            Boolean[] chromosome = nextGenerationPopulation.get(i);

            // Determine if mutation will occur
            if (Math.random() < MUTATION_RATE) {

                // Determine the mutation point
                int mutationPoint = (int) (Math.random() * knapsack.getItems().size());
                // Perform mutation
                chromosome[mutationPoint] = !chromosome[mutationPoint];

            }
        }
    }

    /**
     * @brief Replaces the old population with the new population, or keeps the old
     *        population if the new population is worse.
     */
    public void replacePopulation() {

        double currentAverageFitness = getAverageFitness();

        if (currentAverageFitness > averageFitness) {
            averageFitness = currentAverageFitness;
            bestIteration += noImprovement;
            noImprovement = 0;
            // Clear out the old population
            knapsackPopulation = new ArrayList<Boolean[]>();
        } else {
            noImprovement++;

            while (nextGenerationPopulation.size() < populationSize) {

                Boolean[] chromosome = new Boolean[knapsack.getItems().size()];
                for (int j = 0; j < knapsack.getItems().size(); j++) {
                    chromosome[j] = Math.random() < 0.5;
                }
                nextGenerationPopulation.add(chromosome);

            }

            knapsackPopulation = nextGenerationPopulation;
        }
    }

    // === Helper Functions ===

    /**
     * @brief Checks if index of chromosone is in tournament list
     * 
     * @param tournamentSpace
     */
    public boolean isInTournament(int[] tournamentSpace, int index) {
        for (int i = 0; i < tournamentSpace.length; i++) {
            if (tournamentSpace[i] == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * @brief Find the best knapsack in the population and set it as the best
     *        knapsack, also set the best fitness
     * 
     */
    public void setBestKnapsack() {
        int bestIndex = 0;
        double bestFitness = 0;

        for (int i = 0; i < populationSize; i++) {
            double fitness = getSumFitness(knapsackPopulation.get(i));
            if (fitness > bestFitness) {
                bestFitness = fitness;
                bestIndex = i;
            }
        }

        bestKnapsack = knapsackPopulation.get(bestIndex);
        this.bestFitness = bestFitness;

    }

    /**
     * @brief Gets the best knapsack and returns it
     * 
     * @return bestKnapsack
     */
    public Boolean[] getBestKnapsack() {
        return bestKnapsack;
    }

    /**
     * @brief Gets the best fitness and returns it
     * 
     * @return bestFitness
     */
    public double getBestFitness() {
        return bestFitness;
    }

    /**
     * @brief Gets the best iteration and returns it
     * 
     * @return bestIteration
     */
    public int getBestIteration() {
        return bestIteration;
    }

    /**
     * @brief Determines the fitness of a knapsack using a penalty if the weight
     *        exceeds the capacity
     * 
     * @param chromosome
     * @return fitness
     */
    public double getPenaltyFitness(Boolean[] chromosome) {
        double fitness = 0;
        double weight = knapsack.getWeight(chromosome);
        double penalty = Math.max(0, weight - knapsack.getCapacity()) * PENALTY_FACTOR;
        // if (weight <= knapsack.getCapacity()) {
        fitness = knapsack.getValue(chromosome) - penalty;
        // }
        return fitness;
    }

    /**
     * @brief Determines the fitness of a knapsack by summing the value of the items
     *        in the knapsack.
     *        If the knapsack is over capacity, the fitness is 0. A higher fitness
     *        is better.
     * 
     * @param chromosome
     * @return fitness
     */
    public double getSumFitness(Boolean[] chromosome) {
        double fitness = 0;
        double weight = knapsack.getWeight(chromosome);
        double value = knapsack.getValue(chromosome);
        if (weight <= knapsack.getCapacity()) {
            fitness = value;
        }

        if (fitness % 1 > 0.0001) {
            fitness = Math.round(fitness * 10000.0) / 10000.0;
        }

        return fitness;
    }

    /**
     * @brief Determines the average fitness of the population
     * 
     * @return averageFitness
     */
    public double getAverageFitness() {
        double averageFitness = 0;
        for (int i = 0; i < populationSize; i++) {
            averageFitness += getPenaltyFitness(knapsackPopulation.get(i));
        }
        averageFitness /= populationSize;
        return averageFitness;
    }

    /**
     * @brief get the time
     * 
     */
    public double getTimeElapsed() {
        return timeTaken;
    }

    /**
     * @brief create a deep copy of a chromosome
     * 
     * @param chromosome
     */
    public Boolean[] copyChromosome(Boolean[] chromosome) {
        Boolean[] copy = new Boolean[chromosome.length];
        for (int i = 0; i < chromosome.length; i++) {
            copy[i] = chromosome[i];
        }
        return copy;
    }

    /**
     * @brief print out the parameters
     */
    public void printParameters() {
        System.out.println("\n=== GA Parameters ===");
        System.out.println("| Population Multiplier:   " + POPULATION_MULTIPLIER);
        System.out.println("| Crossover Rate:          " + CROSSOVER_RATE);
        System.out.println("| Mutation Rate:           " + MUTATION_RATE);
        System.out.println("| Max Generations:         " + MAX_GENERATIONS);
        System.out.println("| Stopping Iterations:     " + STOPPING_ITERATIONS);
        System.out.println("| Penalty Factor:          " + PENALTY_FACTOR);
        System.out.println("| Tournament Portion:      " + TOURNAMENT_PORTION);
        System.out.println("| Initial Bit Probability: " + INITIAL_BIT_PROBABILITY);
        System.out.println("=== === ===");
    }

}
