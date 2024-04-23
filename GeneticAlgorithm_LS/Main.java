import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Main extends Helper {
    public static void main(String[] args) {

        int RUN_COUNT = 1;

        boolean runGA = true;
        boolean runASO = true;
        List<Result> resultsGeneticAlgorithm = new ArrayList<>();
        List<Result> resultsGeneticAlgorithmILS = new ArrayList<>();

        HashMap<String, Double> optimums = new HashMap<String, Double>();
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

        HashMap<String, Knapsack> knapsacks = new HashMap<String, Knapsack>();
        knapsacks = readKnapsackData("Knapsack Instances");

        // Run through the genetic algorithm for each knapsack
        if (runGA) {
            for (String key : knapsacks.keySet()) {
                Knapsack knapsack = knapsacks.get(key);
                long seed = 7;
                double averageTime = 0;
                double bestFitness = 0;

                for (int i = 0; i < RUN_COUNT; i++) {
                    GA ga = new GA(knapsack);
                    averageTime += ga.getTimeElapsed();

                    if (ga.getBestFitness() > bestFitness) {
                        bestFitness = ga.getBestFitness();
                    }
                }

                averageTime /= RUN_COUNT;
                Result gaResult = new Result(key, "GA", seed, bestFitness, optimums.get(key), averageTime);
                resultsGeneticAlgorithm.add(gaResult);
            }
        }

        // run through ACO for each knapsack
        if (runASO) {
            for (String key : knapsacks.keySet()) {
                Knapsack knapsack = knapsacks.get(key);
                long seed = 7;
                double averageTime = 0;
                double bestFitness = 0;

                for (int i = 0; i < RUN_COUNT; i++) {
                    ACO aco = new ACO(knapsack);
                    averageTime += aco.getTimeElapsed();

                    if (bestFitness < aco.getBestFitness()) {
                        bestFitness = aco.getBestFitness();
                    }
                }

                averageTime /= RUN_COUNT;
                Result gaResult = new Result(key, "GA - ILS", seed, bestFitness, optimums.get(key), averageTime);
                resultsGeneticAlgorithmILS.add(gaResult);
            }
        }

        // print results
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
            Result result = resultsGeneticAlgorithm.get(i);
            Result result_1 = resultsGeneticAlgorithmILS.get(i);
            System.out.printf("%-20s | %-10s | %-10d | %-15.2f | %-13.2f | %-15.2f%n", 
                result.getProblemInstance(), 
                result.getAlgorithm(), 
                result.getSeedValue(), 
                result.getBestSolution(), 
                result.getKnownOptimum(), 
                result.getRuntimeSeconds());
            System.out.printf("%-20s | %-10s | %-10d | %-15.2f | %-13.2f | %-15.2f%n", 
                " ", 
                result_1.getAlgorithm(), 
                result_1.getSeedValue(), 
                result_1.getBestSolution(), 
                result_1.getKnownOptimum(), 
                result_1.getRuntimeSeconds());
            System.out.println("----------------------------------------------------------------------------------------------------");
        }
    }
}