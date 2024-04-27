import java.io.File;
import java.util.List;
import java.util.HashMap;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;

/**
 * @file Main.java
 * 
 * @author Selepe Sello
 * @date 26 April 2024
 * @version 1.0
 * @brief The Main class is the entry point of the program. It contains the main method
 * that executes the genetic algorithm and ant colony optimization algorithms
 * for solving the knapsack problem.
 */

public class Main {
    /**
     * Reads knapsack data from files in a specified folder.
     *
     * @param folderName the name of the folder containing the files
     * @return a HashMap where the keys are file names and the values are Knapsack objects
     */
    public static HashMap<String, Knapsack> readKnapsackData(String folderName) {
        HashMap<String, Knapsack> knapsacks = new HashMap<>();
        File folder = new File("./Code" + File.separator + folderName);
        if (!folder.exists()) {
            folder = new File("./" + folderName);
        }

        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                Knapsack knapsack = null;
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line = br.readLine();
                    int capacity = Integer.parseInt(line.split(" ")[1]);
                    knapsack = new Knapsack(capacity);
                    while ((line = br.readLine()) != null) {
                        String[] lineSplit = line.split(" ");
                        KnapsackItem item = new KnapsackItem(Double.parseDouble(lineSplit[0]), Double.parseDouble(lineSplit[1]));
                        knapsack.addItem(item);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (knapsack != null) {
                    knapsacks.put(file.getName(), knapsack);
                }
            }
        }

        return knapsacks;
    }

    /**
     * Prints a solution.
     *
     * @param solution the solution to print
     */
    public void printSolution(Boolean[] solution) {
        System.out.print("[");
        for (int i = 0; i < solution.length; i++) {
            if (solution[i] == null) {
                System.out.print("_");
            } else if (solution[i]) {
                System.out.print(" 1 ");
            } else {
                System.out.print(" 0 ");
            }
            if (i != solution.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * Initializes the data required for running the algorithms.
     *
     * @param optimums  The map of known optimum values for each knapsack instance.
     */
    public static void initializeData(HashMap<String, Double> optimums) {
        // Initialize the known optimum values for each knapsack instance
        optimums.put("f1_l-d_kp_10_269", 295.0);
        optimums.put("f2_l-d_kp_20_878", 1024.0);
        optimums.put("f3_l-d_kp_4_20", 35.0);
        optimums.put("f4_l-d_kp_4_11", 23.0);
        optimums.put("f5_l-d_kp_15_375", 481.0694);
        optimums.put("f6_l-d_kp_10_60", 52.0);
        optimums.put("f7_l-d_kp_7_50", 107.0);
        optimums.put("f8_l-d_kp_23_10000", 9767.0);
        optimums.put("f9_l-d_kp_5_80", 130.0);
        optimums.put("f10_l-d_kp_20_879", 1025.0);
        optimums.put("knapPI_1_100_1000_1", 9147.0);
    }

    /**
     * Runs the genetic algorithm for each knapsack instance and stores the results.
     *
     * @param knapsacks               The map of knapsack instances.
     * @param optimums                The map of known optimum values for each knapsack instance.
     * @param resultsGeneticAlgorithm The list to store the results of the genetic algorithm.
     * @param RUN_COUNT               The number of times to run the genetic algorithm.
     */
    public static void runGeneticAlgorithm(
        HashMap<String, Knapsack> knapsacks, HashMap<String, Double> optimums,
        List<AlgorithmResult> resultsGeneticAlgorithm, int RUN_COUNT, Integer seed
    ) {
        // Run the genetic algorithm for each knapsack instance
        for (String key : knapsacks.keySet()) {
            Knapsack knapsack = knapsacks.get(key);
            double averageTime = 0;
            double bestFitness = 0;

            for (int i = 0; i < RUN_COUNT; i++) {
                GA ga = new GA(knapsack, seed);
                averageTime += ga.getTimeElapsed();

                System.out.print("\nProblem Instance: " + key);
                ga.printGAParameters("Final");

                if (ga.getBestFitness() > bestFitness) {
                    bestFitness = ga.getBestFitness();
                }
            }

            averageTime /= RUN_COUNT;
            AlgorithmResult gaResult = new AlgorithmResult(key, "GA", seed, bestFitness, optimums.get(key), averageTime);
            resultsGeneticAlgorithm.add(gaResult);
        }
    }

    /**
     * Runs the ant colony optimization algorithm for each knapsack instance and stores the results.
     *
     * @param knapsacks                        The map of knapsack instances.
     * @param optimums                         The map of known optimum values for each knapsack instance.
     * @param resultsGeneticAlgorithmILS       The list to store the results of the genetic algorithm with ILS.
     * @param RUN_COUNT                        The number of times to run the ant colony optimization algorithm.
     */
    public static void runAntColonyOptimization(
        HashMap<String, Knapsack> knapsacks, HashMap<String, Double> optimums,
        List<AlgorithmResult> resultsGeneticAlgorithmILS, int RUN_COUNT, Integer seed
    ) {
        // Run the ant colony optimization algorithm for each knapsack instance
        for (String key : knapsacks.keySet()) {
            Knapsack knapsack = knapsacks.get(key);
            double averageTime = 0;
            double bestFitness = 0;

            for (int i = 0; i < RUN_COUNT; i++) {
                ACO aco = new ACO(knapsack, seed);
                averageTime += aco.getTimeElapsed();

                System.out.print("\nProblem Instance: " + key);
                aco.printACOParameters("Final");

                if (bestFitness < aco.getBestFitness()) {
                    bestFitness = aco.getBestFitness();
                }
            }

            averageTime /= RUN_COUNT;
            AlgorithmResult gaResult = new AlgorithmResult(key, "GA - ILS", seed, bestFitness, optimums.get(key), averageTime);
            resultsGeneticAlgorithmILS.add(gaResult);
        }
    }

    /**
     * The main method of the program.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {

        int RUN_COUNT = 1;
        Integer SEED_VALUE = 24;
        System.out.println("Running Genetic Algorithm and Ant Colony Optimization for the Knapsack Problem");
        boolean exeGeneticAlgorithm = true;
        boolean exeAntColonyOptimization = true;
        List<AlgorithmResult> resultsGeneticAlgorithm = new ArrayList<>();
        List<AlgorithmResult> resultsGeneticAlgorithmILS = new ArrayList<>();

        HashMap<String, Double> optimums = new HashMap<String, Double>();
        // Read the knapsack data from the specified directory
        HashMap<String, Knapsack> knapsacks = readKnapsackData("Knapsack Instances");
        initializeData(optimums);
        
        if (exeGeneticAlgorithm && exeAntColonyOptimization) {
            // Run the genetic algorithm and ant colony optimization algorithm
            System.out.println("\t=> Currently Running the Genetic Algorithm for the Knapsack Problem");
            runGeneticAlgorithm(knapsacks, optimums, resultsGeneticAlgorithm, RUN_COUNT, SEED_VALUE);
            System.out.println("\t=> Currently Running the Ant Colony Optimization for the Knapsack Problem");
            runAntColonyOptimization(knapsacks, optimums, resultsGeneticAlgorithmILS, RUN_COUNT, SEED_VALUE);
        } else if (exeGeneticAlgorithm) {
            // Run the genetic algorithm
            System.out.println("\t=> Currently Running the Genetic Algorithm for the Knapsack Problem");
            runGeneticAlgorithm(knapsacks, optimums, resultsGeneticAlgorithm, RUN_COUNT, SEED_VALUE);
        } else if (exeAntColonyOptimization) {
            // Run the ant colony optimization algorithm
            System.out.println("\t=> Currently Running the Ant Colony Optimization for the Knapsack Problem");
            runAntColonyOptimization(knapsacks, optimums, resultsGeneticAlgorithmILS, RUN_COUNT, SEED_VALUE);
        } else {
            System.out.println("Please set exeGeneticAlgorithm or exeAntColonyOptimization to true to run the genetic algorithm or ACO respectively.");
        }

        System.out.println("\nPresenting Results for the Knapsack Problem.\n");

        // Print the results
        System.out.printf("%-20s | %-10s | %-10s | %-15s | %-13s | %-15s%n", 
            "Problem Instance", 
            "Algorithm", 
            "Seed Value", 
            "Best Solution", 
            "Known Optimum", 
            "Runtime (seconds)"
        );
        System.out.println("----------------------------------------------------------------------------------------------------");
        for (int i = 0; i < resultsGeneticAlgorithmILS.size(); i++) {
            AlgorithmResult result = resultsGeneticAlgorithm.get(i);
            AlgorithmResult result1 = resultsGeneticAlgorithmILS.get(i);
            System.out.printf("%-20s | %-10s | %-10d | %-15.2f | %-13.2f | %-15.2f%n", 
                result.getProblemInstance(), 
                result.getAlgorithm(), 
                result.getSeedValue(), 
                result.getBestSolution(), 
                result.getKnownOptimum(), 
                result.getRuntimeSeconds());
            System.out.printf("%-20s | %-10s | %-10d | %-15.2f | %-13.2f | %-15.2f%n", 
                " ", 
                result1.getAlgorithm(), 
                result1.getSeedValue(), 
                result1.getBestSolution(), 
                result1.getKnownOptimum(), 
                result1.getRuntimeSeconds());
            System.out.println("----------------------------------------------------------------------------------------------------");
        }
    }
}
