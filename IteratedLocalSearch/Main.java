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
        RouteDetails detailsILS = ils.run();

        // Calculate ILS statistics
        double totalDistanceILS = detailsILS.getAverageDistance();
        int bestDistanceILS = detailsILS.getBestRoute().getDistance();
        double totalRuntimeILS = detailsILS.getTotalRuntime();
        String bestRouteILS = detailsILS.getBestRoute().getRouteString();
    
        // Convert total runtime from milliseconds to seconds for ILS
        double totalRuntimeILSSecs = totalRuntimeILS / 1000.0;
        
        // Print summary table
        System.out.println("----------------------------------------------");
        System.out.println("| Problem Set            | ILS               |");
        System.out.println("|------------------------|-------------------|");
        System.out.printf("| Best Solution(route)   | %-18s|%n", bestRouteILS);
        System.out.printf("| Objective Function Val | %-18d|%n", bestDistanceILS);
        System.out.printf("| Runtime (s)            | %-18.2f|%n", totalRuntimeILSSecs);
        System.out.printf("| Runtime (ms)           | %-18.2f|%n", totalRuntimeILS);
        System.out.printf("| Av Obj Function        | %-18.2f|%n", totalDistanceILS);
        System.out.println("----------------------------------------------");
    }
}
