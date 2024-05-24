import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GP {
    private static final Random rand = new Random();
    private static final int POP_SIZE = 100;
    private static final int MAX_DEPTH = 5;
    private static final int GENERATIONS = 40;
    private static final double CROSSOVER_RATE = 0.9;
    private static final double MUTATION_RATE = 0.1;

    private static Node generateRandomTree(int maxDepth) {
        if (maxDepth == 0) {
            return rand.nextBoolean() ? new TerminalNode(rand.nextInt(19)) : new ConstantNode(rand.nextDouble() * 2 - 1);
        } else {
            FunctionNode node = new FunctionNode("+-*/".charAt(rand.nextInt(4)));
            node.left = generateRandomTree(maxDepth - 1);
            node.right = generateRandomTree(maxDepth - 1);
            return node;
        }
    }

    private static Individual tournamentSelection(List<Individual> population) {
        Individual best = population.get(rand.nextInt(POP_SIZE));
        for (int i = 1; i < 3; i++) {
            Individual challenger = population.get(rand.nextInt(POP_SIZE));
            if (challenger.fitness > best.fitness) {
                best = challenger;
            }
        }
        return best;
    }

    private static void crossover(Individual ind1, Individual ind2) {
        if (rand.nextDouble() < CROSSOVER_RATE) {
            Node temp = ind1.root.left;
            ind1.root.left = ind2.root.left;
            ind2.root.left = temp;
        }
    }

    private static void mutate(Individual ind) {
        if (rand.nextDouble() < MUTATION_RATE) {
            ind.root.left = generateRandomTree(MAX_DEPTH);
        }
    }

    public static void run(double[][] dataset, double[] labels) {
        List<Individual> population = new ArrayList<>();
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

            Individual best = population.stream().max((ind1, ind2) -> Double.compare(ind1.fitness, ind2.fitness)).get();
            System.out.println("Generation " + generation + ": Best fitness = " + best.fitness);
        }
    }
}
