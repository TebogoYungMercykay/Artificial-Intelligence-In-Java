import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GP {
    private final Random rand;
    private static final int POP_SIZE = 100;
    private static final int MAX_DEPTH = 5;
    private static final int GENERATIONS = 50;
    private static final double CROSSOVER_RATE = 0.9;
    private static final double MUTATION_RATE = 0.1;

    private List<Individual> population;
    // to store the number of features
    private int featureCount;

    // Performance metrics
    private double truePositive = 0;
    private double trueNegative = 0;
    private double falsePositive = 0;
    private double falseNegative = 0;

    public GP(long seed, int featureCount) {
        this.rand = new Random(seed);
        this.featureCount = featureCount; // initialize feature count
    }

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

    private void crossover(Individual ind1, Individual ind2) {
        if (rand.nextDouble() < CROSSOVER_RATE) {
            Node temp = ind1.root.left;
            ind1.root.left = ind2.root.left;
            ind2.root.left = temp;
        }
    }

    private void mutate(Individual ind) {
        if (rand.nextDouble() < MUTATION_RATE) {
            ind.root.left = generateRandomTree(MAX_DEPTH);
        }
    }

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

    public Individual getBestIndividual() {
        return population.stream().max((ind1, ind2) -> Double.compare(ind1.fitness, ind2.fitness)).get();
    }

    public void evaluateModel(Individual best, double[][] testDataset, double[] testLabels) {
        resetValues();

        // Update the Values
        for (int i = 0; i < testDataset.length; i++) {
            double actual = testLabels[i];
            double predicted = best.predict(testDataset[i]);

            updateMetrics(predicted, actual);
        }
    }

    public void resetValues() {
        // Reset the Values
        truePositive = 0;
        trueNegative = 0;
        falsePositive = 0;
        falseNegative = 0;
    }

    // Update performance metrics based on prediction and target
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

    // Calculate and return accuracy
    public double getAccuracy() {
        return (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
    }

    // Calculate and return specificity
    public double getSpecificity() {
        return trueNegative / (trueNegative + falsePositive);
    }

    // Calculate and return sensitivity
    public double getSensitivity() {
        return truePositive / (truePositive + falseNegative);
    }

    // Calculate and return F-measure
    public double getFMeasure() {
        double precision = truePositive / (truePositive + falsePositive);
        double recall = truePositive / (truePositive + falseNegative);
        return 2 * (precision * recall) / (precision + recall);
    }
}
