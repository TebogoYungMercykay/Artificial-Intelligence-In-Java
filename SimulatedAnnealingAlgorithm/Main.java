import java.util.List;

/**
 * @file Main.java
 * 
 * @author Selepe Sello
 * @date 29 March 2021
 * @version 1.0
 * @brief The Main class to run the Simulated Annealing algorithm.
 */

public class Main {
    public static void main(String[] args) {
        // Iterated Local Search (SA) Algorithm
        SimulatedAnnealing sa = new SimulatedAnnealing();
        SolutionDetails detailsSA = sa.run();
        double totalDistanceSA = 0;
        int bestDistanceSA = Integer.MAX_VALUE;
        double totalRuntimeSA = 0;
        String bestRouteSA = "null";
    
        // Calculate SA statistics
        totalDistanceSA = detailsSA.getAverageDistance();
        bestDistanceSA = detailsSA.getBestSolution().getDistance();
        totalRuntimeSA = detailsSA.getTotalRuntime();
        bestRouteSA = detailsSA.getBestSolution().getRouteString();
    
        // Convert total runtime from milliseconds to seconds for SA
        double totalRuntimeSASecs = totalRuntimeSA / 1000.0;
        
        // Print summary table
        System.out.println("--------------------------------------------");
        System.out.println("| Problem Set            | SA              |");
        System.out.println("|------------------------|-----------------|");
        System.out.printf("| Best Solution(route)   | %-16s|%n", bestRouteSA);
        System.out.printf("| Objective Function Val | %-16d|%n", bestDistanceSA);
        System.out.printf("| Runtime (s)            | %-16.2f|%n", totalRuntimeSASecs);
        System.out.printf("| Av Obj Function        | %-16.2f|%n", totalDistanceSA);
        System.out.println("--------------------------------------------");
    }
}
