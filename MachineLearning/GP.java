import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @file GP.java
 * 
 * @author Selepe Sello
 * @date 25 may 2024
 * @version 1.0
 * @brief Genetic Programming (GP) algorithm implementation.
 */

public class GP {
    private final Random rand; ///< Random number generator
    private static final int POP_SIZE = 100; ///< Population size
    private static final int MAX_DEPTH = 5; ///< Maximum depth of generated trees
    private static final int GENERATIONS = 50; ///< Number of generations
    private static final double CROSSOVER_RATE = 0.9; ///< Crossover rate
    private static final double MUTATION_RATE = 0.1; ///< Mutation rate

    private List<Individual> population; ///< Population of individuals
    private int featureCount; ///< Number of features in the dataset

    // Performance metrics
    private double truePositive = 0; ///< True positive count
    private double trueNegative = 0; ///< True negative count
    private double falsePositive = 0; ///< False positive count
    private double falseNegative = 0; ///< False negative count

    /**
     * @brief Constructor for GP algorithm.
     * @param seed Seed for random number generation.
     * @param featureCount Number of features in the dataset.
     */
    public GP(long seed, int featureCount) {
        this.rand = new Random(seed);
        this.featureCount = featureCount;
    }

    /**
     * @brief Generates a random tree with given maximum depth.
     * @param maxDepth Maximum depth of the generated tree.
     * @return Root node of the generated tree.
     */
    private Node generateRandomTree(int maxDepth) {
        if (maxDepth == 0) {
            return rand.nextBoolean() ? new TerminalNode(rand.nextInt(featureCount)) : new ConstantNode(rand.nextDouble() * 2 - 1);
        } else {
            FunctionNode node = new FunctionNode("+-*/".charAt(rand.nextInt(4)));
            node.left = generateRandomTree(maxDepth - 1);
            node.right = generateRandomTree(maxDepth - 1);
            return node;
        }
    }

    /**
     * @brief Selects an individual using tournament selection.
     * @param population Population of individuals.
     * @return Selected individual.
     */
    private Individual tournamentSelection(List<Individual> population) {
        Individual best = population.get(rand.nextInt(POP_SIZE));
        for (int i = 1; i < 3; i++) {
            Individual challenger = population.get(rand.nextInt(POP_SIZE));
            if (challenger.fitness > best.fitness) {
                best = challenger;
            }
        }
        return best;
    }

    /**
     * @brief Performs crossover between two individuals.
     * @param ind1 First individual.
     * @param ind2 Second individual.
     */
    private void crossover(Individual ind1, Individual ind2) {
        if (rand.nextDouble() < CROSSOVER_RATE) {
            Node temp = ind1.root.left;
            ind1.root.left = ind2.root.left;
            ind2.root.left = temp;
        }
    }

    /**
     * @brief Performs mutation on an individual.
     * @param ind Individual to mutate.
     */
    private void mutate(Individual ind) {
        if (rand.nextDouble() < MUTATION_RATE) {
            ind.root.left = generateRandomTree(MAX_DEPTH);
        }
    }

    /**
     * @brief Runs the GP algorithm.
     * @param dataset Input dataset.
     * @param labels Labels of the dataset.
     */
    public void run(double[][] dataset, double[] labels) {
        population = new ArrayList<>();
        for (int i = 0; i < POP_SIZE; i++) {
            Node root = generateRandomTree(MAX_DEPTH);
            Individual ind = new Individual(root);
            ind.evaluate(dataset, labels);
            population.add(ind);
        }

        for (int generation = 0; generation < GENERATIONS; generation++) {
            List<Individual> newPopulation = new ArrayList<>();
            for (int i = 0; i < POP_SIZE; i++) {
                Individual parent1 = tournamentSelection(population);
                Individual parent2 = tournamentSelection(population);
                Individual offspring = parent1.copy();
                crossover(offspring, parent2);
                mutate(offspring);
                offspring.evaluate(dataset, labels);
                newPopulation.add(offspring);
            }
            population = newPopulation;
        }
    }

    /**
     * @brief Gets the best individual from the population.
     * @return Best individual.
     */
    public Individual getBestIndividual() {
        return population.stream().max((ind1, ind2) -> Double.compare(ind1.fitness, ind2.fitness)).get();
    }

    /**
     * @brief Evaluates the model using the best individual.
     * @param best Best individual.
     * @param testDataset Test dataset.
     * @param testLabels Labels of the test dataset.
     */
    public void evaluateModel(Individual best, double[][] testDataset, double[] testLabels) {
        resetValues();

        // Update the Values
        for (int i = 0; i < testDataset.length; i++) {
            double actual = testLabels[i];
            double predicted = best.predict(testDataset[i]);

            updateMetrics(predicted, actual);
        }
    }

    /**
     * @brief Resets the performance metrics.
     */
    public void resetValues() {
        // Reset the Values
        truePositive = 0;
        trueNegative = 0;
        falsePositive = 0;
        falseNegative = 0;
    }

    /**
     * @brief Updates performance metrics based on prediction and target.
     * @param prediction Predicted value.
     * @param target Target value.
     */
    private void updateMetrics(double prediction, double target) {
        if (prediction == 1.0 && target == 1.0) {
            truePositive++;
        } else if (prediction == 0.0 && target == 0.0) {
            trueNegative++;
        } else if (prediction == 1.0 && target == 0.0) {
            falsePositive++;
        } else if (prediction == 0.0 && target == 1.0) {
            falseNegative++;
        }
    }

    /**
     * @brief Calculates and returns the accuracy of the model.
     * @return Accuracy as a double.
     */
    public double getAccuracy() {
        return (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
    }

    /**
     * @brief Calculates and returns the specificity of the model.
     * @return Specificity as a double.
     */
    public double getSpecificity() {
        return trueNegative / (trueNegative + falsePositive);
    }

    /**
     * @brief Calculates and returns the sensitivity of the model.
     * @return Sensitivity as a double.
     */
    public double getSensitivity() {
        return truePositive / (truePositive + falseNegative);
    }

    /**
     * @brief Calculates and returns the F-measure of the model.
     * @return F-measure as a double.
     */
    public double getFMeasure() {
        double precision = truePositive / (truePositive + falsePositive);
        double recall = truePositive / (truePositive + falseNegative);
        return 2 * (precision * recall) / (precision + recall);
    }
}
