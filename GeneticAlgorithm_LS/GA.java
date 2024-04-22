import java.util.ArrayList;
import java.util.Random;

/**
 * Genetic Algorithm for the Knapsack Problem.
 */
public class GA extends Helper {

    private static final int seed = 8;
    private static final Random random = new Random(seed);

    // random.nextInt()

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

    private int populationSize;
    private int tournamentSize;

    /**
     * Constructor for the GA class.
     * @param initialKnapsack The initial knapsack.
     */
    public GA(Knapsack initialKnapsack) {
        long startTime = System.nanoTime();

        // Create the initial population
        knapsack = initialKnapsack;
        populationSize = (int)(initialKnapsack.getItems().size() * POPULATION_MULTIPLIER);
        tournamentSize = (int)(populationSize * TOURNAMENT_PORTION);
        if (tournamentSize < 2) {
            tournamentSize = 2;
        } else if (tournamentSize % 2 == 1) {
            tournamentSize++;
        }

        knapsackPopulation = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            Boolean[] chromosome = new Boolean[knapsack.getItems().size()];
            for (int j = 0; j < knapsack.getItems().size(); j++) {
                chromosome[j] = random.nextInt() < INITIAL_BIT_PROBABILITY;
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
     * Run the GA algorithm.
     */
    public void run() {
        tournamentSelection();
        onePointCrossover();
        bitFlipMutation();
        replacePopulation();
        knapsackPopulation = nextGenerationPopulation;
    }

    /**
     * Selects a knapsack from the population using tournament selection.
     */
    public void tournamentSelection() {
        winners = new ArrayList<>();

        while (knapsackPopulation.size() > 0 && winners.size() < populationSize) {
            // Select random knapsacks from the population to compete in the tournament
            ArrayList<Boolean[]> competitors = new ArrayList<>();
            for (int i = 0; i < tournamentSize; i++) {
                int randomIndex = (int)(random.nextInt() * knapsackPopulation.size());
                competitors.add(knapsackPopulation.get(randomIndex));
            }

            Boolean[] winner = new Boolean[knapsack.getItems().size()];
            double winnerFitness = -1 * Double.MAX_VALUE;

            for (Boolean[] competitor : competitors) {
                double competitorFitness = getSumFitness(competitor);
                if (competitorFitness > winnerFitness) {
                    winner = competitor;
                    winnerFitness = competitorFitness;
                }
            }

            winners.add(winner);
        }
    }

    /**
     * Performs one point crossover on two knapsacks.
     */
    public void onePointCrossover() {
        nextGenerationPopulation = new ArrayList<>();

        for (int i = 0; i < winners.size(); i += 2) {
            Boolean[] parent1 = winners.get(i);
            Boolean[] parent2 = winners.get(i + 1);

            // Determine if crossover will occur
            if (random.nextInt() < CROSSOVER_RATE) {
                // Determine the crossover point
                int crossoverPoint = (int)(random.nextInt() * knapsack.getItems().size());

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
     * Performs bit flip mutation on a chromosome.
     */
    public void bitFlipMutation() {
        for (Boolean[] chromosome : nextGenerationPopulation) {
            // Determine if mutation will occur
            if (random.nextInt() < MUTATION_RATE) {
                // Determine the mutation point
                int mutationPoint = (int)(random.nextInt() * knapsack.getItems().size());
                // Perform mutation
                chromosome[mutationPoint] = !chromosome[mutationPoint];
            }
        }
    }

    /**
     * Replaces the old population with the new population, or keeps the old
     * population if the new population is worse.
     */
    public void replacePopulation() {
        double currentAverageFitness = getAverageFitness();

        if (currentAverageFitness > averageFitness) {
            averageFitness = currentAverageFitness;
            bestIteration += noImprovement;
            noImprovement = 0;
            // Clear out the old population
            knapsackPopulation = new ArrayList<>();
        } else {
            noImprovement++;

            while (nextGenerationPopulation.size() < populationSize) {
                Boolean[] chromosome = new Boolean[knapsack.getItems().size()];
                for (int j = 0; j < knapsack.getItems().size(); j++) {
                    chromosome[j] = random.nextInt() < 0.5;
                }
                nextGenerationPopulation.add(chromosome);
            }

            knapsackPopulation = nextGenerationPopulation;
        }
    }

    /**
     * Find the best knapsack in the population and set it as the best
     * knapsack, also set the best fitness.
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
     * Gets the best knapsack and returns it.
     * @return The best knapsack.
     */
    public Boolean[] getBestKnapsack() {
        return bestKnapsack;
    }

    /**
     * Gets the best fitness and returns it.
     * @return The best fitness.
     */
    public double getBestFitness() {
        return bestFitness;
    }

    /**
     * Gets the best iteration and returns it.
     * @return The best iteration.
     */
    public int getBestIteration() {
        return bestIteration;
    }

    /**
     * Determines the fitness of a knapsack using a penalty if the weight
     * exceeds the capacity.
     * @param chromosome The chromosome.
     * @return The fitness.
     */
    public double getPenaltyFitness(Boolean[] chromosome) {
        double weight = knapsack.getWeight(chromosome);
        double penalty = Math.max(0, weight - knapsack.getCapacity()) * PENALTY_FACTOR;
        return knapsack.getValue(chromosome) - penalty;
    }

    /**
     * Determines the fitness of a knapsack by summing the value of the items
     * in the knapsack. If the knapsack is over capacity, the fitness is 0.
     * @param chromosome The chromosome.
     * @return The fitness.
     */
    public double getSumFitness(Boolean[] chromosome) {
        double weight = knapsack.getWeight(chromosome);
        double value = knapsack.getValue(chromosome);
        if (weight > knapsack.getCapacity()) {
            return 0;
        }
        return value;
    }

    /**
     * Determines the average fitness of the population.
     * @return The average fitness.
     */
    public double getAverageFitness() {
        double totalFitness = 0;
        for (Boolean[] individual : knapsackPopulation) {
            totalFitness += getPenaltyFitness(individual);
        }
        return totalFitness / populationSize;
    }

    /**
     * Get the time elapsed.
     * @return The time elapsed.
     */
    public double getTimeElapsed() {
        return timeTaken;
    }

    /**
     * Create a deep copy of a chromosome.
     * @param chromosome The chromosome.
     * @return The copy of the chromosome.
     */
    public Boolean[] copyChromosome(Boolean[] chromosome) {
        Boolean[] copy = new Boolean[chromosome.length];
        System.arraycopy(chromosome, 0, copy, 0, chromosome.length);
        return copy;
    }
}