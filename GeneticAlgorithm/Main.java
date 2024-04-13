import java.util.HashMap;

public class Main extends Helper {
    public static void main(String[] args) {

        int RUN_COUNT = 1;

        boolean runGA = true;
        boolean runASO = true;

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

        boolean first = true;

        // Run through the genetic algorithm for each knapsack
        if (runGA) {
            for (String key : knapsacks.keySet()) {

                Knapsack knapsack = knapsacks.get(key);

                int averageIterations = 0;
                int hits = 0;
                int averageOutBy = 0;
                double averageTime = 0;
                double bestFitness = 0;

                for (int i = 0; i < RUN_COUNT; i++) {
                    GA ga = new GA(knapsack);

                    if (first) {
                        first = false;
                        ga.printParameters();
                    }

                    averageIterations += ga.getBestIteration();

                    if (ga.getBestFitness() == optimums.get(key)) {
                        hits++;
                    } else {
                        averageOutBy += (-1 * (ga.getBestFitness() - optimums.get(key)));
                    }
                    averageTime += ga.getTimeElapsed();

                    if (ga.getBestFitness() > bestFitness) {
                        bestFitness = ga.getBestFitness();
                    }

                }

                averageIterations /= RUN_COUNT;
                averageTime /= RUN_COUNT;

                if (hits < RUN_COUNT) {
                    averageOutBy /= (RUN_COUNT - hits);
                }

                if (hits >= Math.floor(RUN_COUNT / 2) && hits != 0) {
                    System.out.print("\033[32mMajority Optimal:\033[0m Avg Time: ");
                    System.out.printf("%.3f", averageTime);
                    System.out.println(" seconds");
                } else {
                    System.out.print("\033[31mMajority Not Optimal:\033[0m Avg Time: ");
                    System.out.printf("%.3f", averageTime);
                    System.out.println(" seconds | Best: " + bestFitness);
                }

                System.out.println(key + " - Optimal: " + optimums.get(key) + " | Average Iterations: "
                        + (averageIterations) + " | Found Optimal: " + hits + "/" + RUN_COUNT
                        + " | Average Out By: " + averageOutBy);

            }
        }

        // run through ACO for each knapsack
        if (runASO) {

            first = true;

            for (String key : knapsacks.keySet()) {

                Knapsack knapsack = knapsacks.get(key);

                int averageIterations = 0;
                int hits = 0;
                int averageOutBy = 0;
                double averageTime = 0;
                double bestFitness = 0;

                for (int i = 0; i < RUN_COUNT; i++) {
                    ACO aco = new ACO(knapsack);

                    if (first) {
                        first = false;
                        aco.printParameters();
                    }

                    averageIterations += aco.getBestIteration();
                    if (aco.getBestFitness() == optimums.get(key)) {
                        hits++;
                    } else {
                        averageOutBy += (-1 * (aco.getBestFitness() - optimums.get(key)));
                    }

                    if (bestFitness < aco.getBestFitness()) {
                        bestFitness = aco.getBestFitness();
                    }

                    averageTime += aco.getTimeElapsed();

                }

                averageIterations /= RUN_COUNT;
                averageTime /= RUN_COUNT;

                if (hits < RUN_COUNT) {
                    averageOutBy /= (RUN_COUNT - hits);
                }
                if (hits >= Math.floor(RUN_COUNT / 2) && hits != 0) {
                    System.out.print("\033[32mMajority Optimal:\033[0m Avg Time: ");
                    System.out.printf("%.3f", averageTime);
                    System.out.println(" seconds");

                } else {
                    System.out.print("\033[31mMajority Not Optimal:\033[0m Avg Time: ");
                    System.out.printf("%.3f", averageTime);
                    System.out.println(" seconds | Best: " + bestFitness);
                }

                System.out.println(key + " - Optimal: " + optimums.get(key) + " | Average Iterations: "
                        + averageIterations + " | Found Optimal: " + hits + "/" +
                        RUN_COUNT
                        + " | Average Out By: " + averageOutBy);
            }
        }

        System.out.println("Done");
    }
}