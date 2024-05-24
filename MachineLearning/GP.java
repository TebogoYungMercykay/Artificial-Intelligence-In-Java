import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GP {
    private final Random rand;
    private static final int POP_SIZE = 100;
    private static final int MAX_DEPTH = 5;
    private static final int GENERATIONS = 40;
    private static final double CROSSOVER_RATE = 0.9;
    private static final double MUTATION_RATE = 0.1;

    private List<Individual> population;
    private double accuracy;
    private double specificity;
    private double sensitivity;
    private double fMeasure;
    // to store the number of features
    private int featureCount;

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

            // Individual best = getBestIndividual();
            // System.out.println("Generation " + generation + ": Best fitness = " + best.fitness);
        }
    }

    public Individual getBestIndividual() {
        return population.stream().max((ind1, ind2) -> Double.compare(ind1.fitness, ind2.fitness)).get();
    }

    public void evaluateModel(Individual best, double[][] testDataset, double[] testLabels) {
        int tp = 0, tn = 0, fp = 0, fn = 0;

        for (int i = 0; i < testDataset.length; i++) {
            double actual = testLabels[i];
            double predicted = best.predict(testDataset[i]);

            if (predicted == 1.0) {
                if (actual == 1.0) tp++;
                else fp++;
            } else {
                if (actual == 0.0) tn++;
                else fn++;
            }
        }

        this.accuracy = (double) (tp + tn) / (tp + tn + fp + fn);
        this.specificity = (double) tn / (tn + fp);
        this.sensitivity = (double) tp / (tp + fn);
        double precision = tp + fp == 0 ? 0 : (double) tp / (tp + fp);
        this.fMeasure = precision + sensitivity == 0 ? 0 : 2 * ((precision * sensitivity) / (precision + sensitivity));
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getSpecificity() {
        return specificity;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public double getFMeasure() {
        return fMeasure;
    }
}
