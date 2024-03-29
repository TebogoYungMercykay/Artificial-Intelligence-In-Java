import java.util.List;

/**
 * @file Main.java
 * 
 * @author Selepe Sello
 * @date 29 March 2021
 * @version 1.0
 * @brief The Main class to run the Iterated Local Search algorithm.
 */

public class Main {
    public static void main(String[] args) {
        // Iterated Local Search (ILS) Algorithm
        IteratedLocalSearch ils = new IteratedLocalSearch();
        SolutionDetails detailsILS = ils.run();
        double totalDistanceILS = 0;
        int bestDistanceILS = Integer.MAX_VALUE;
        double totalRuntimeILS = 0;
        String bestRouteILS = "null";
    
        // Calculate ILS statistics
        totalDistanceILS = detailsILS.getAverageDistance();
        bestDistanceILS = detailsILS.getBestSolution().getDistance();
        totalRuntimeILS = detailsILS.getTotalRuntime();
        bestRouteILS = detailsILS.getBestSolution().getRouteString();
    
        // Convert total runtime from milliseconds to seconds for ILS
        double totalRuntimeILSSecs = totalRuntimeILS / 1000.0;
        
        // Print summary table
        System.out.println("--------------------------------------------");
        System.out.println("| Problem Set            | ILS             |");
        System.out.println("|------------------------|-----------------|");
        System.out.printf("| Best Solution(route)   | %-16s|%n", bestRouteILS);
        System.out.printf("| Objective Function Val | %-16d|%n", bestDistanceILS);
        System.out.printf("| Runtime (s)            | %-16.2f|%n", totalRuntimeILSSecs);
        System.out.printf("| Av Obj Function        | %-16.2f|%n", totalDistanceILS);
        System.out.println("--------------------------------------------");
    }
}
