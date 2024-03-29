import java.util.Arrays;
import java.util.List;

/**
 * @file Main.java
 * 
 * @author Selepe Sello
 * @date 29 March 2021
 * @version 1.0
 * @brief The Main class to run the Simulated Annealing and Iterated Local Search algorithms.
 */

public class Main {
    public static void main(String[] args) {
        /**
         * Simulated Annealing (SA) Algorithm
         * Utilising the SimulatedAnnealing class
         */
        SimulatedAnnealing sa = new SimulatedAnnealing();
        SolutionDetails detailsSA = sa.run();
        double totalDistanceSA = 0;
        int bestDistanceSA = Integer.MAX_VALUE;
        int numSolutionsSA = detailsSA.getAllSolutions().size();
        double totalRuntimeSA = 0;
        String bestRouteSA = "null";
    
        // Calculate SA statistics
        totalDistanceSA = detailsSA.getAverageDistance();
        bestDistanceSA = detailsSA.getBestSolution().getDistance();
        totalRuntimeSA = detailsSA.getRuntime();
        bestRouteSA = detailsSA.getBestSolution().getRouteString();
    
        // Convert total runtime from milliseconds to seconds for SA
        double totalRuntimeSASecs = totalRuntimeSA / 1000.0;
    
        /**
         * Iterated Local Search (ILS) Algorithm
         * Utilising the IteratedLocalSearch class
         */
        IteratedLocalSearch ils = new IteratedLocalSearch();
        SolutionDetails detailsILS = ils.run();
        double totalDistanceILS = 0;
        int bestDistanceILS = Integer.MAX_VALUE;
        int numSolutionsILS = detailsILS.getAllSolutions().size();
        double totalRuntimeILS = 0;
        String bestRouteILS = "null";
    
        // Calculate ILS statistics
        totalDistanceILS = detailsILS.getAverageDistance();
        bestDistanceILS = detailsILS.getBestSolution().getDistance();
        totalRuntimeILS = detailsILS.getRuntime();
        bestRouteILS = detailsILS.getBestSolution().getRouteString();
    
        // Convert total runtime from milliseconds to seconds for ILS
        double totalRuntimeILSSecs = totalRuntimeILS / 1000.0;
        
        // Print summary table
        System.out.println("--------------------------------------------------------------------");
        System.out.println("| Problem Set            | ILS                | SA                 |");
        System.out.println("|------------------------|--------------------|--------------------|");
        System.out.printf("| Best Solution(route)   | %-19s| %-19s|%n", bestRouteILS, bestRouteSA);
        System.out.printf("| Objective Function Val | %-19d| %-19d|%n", bestDistanceILS, bestDistanceSA);
        System.out.printf("| Runtime(s)             | %-19.2f| %-19.2f|%n", totalRuntimeILSSecs, totalRuntimeSASecs);
        System.out.printf("| Runtime(ms)            | %-19.2f| %-19.2f|%n", totalRuntimeILS, totalRuntimeSA);
        System.out.printf("| Average Obj Function   | %-19.2f| %-19.2f|%n", totalDistanceILS, totalDistanceSA);
        System.out.printf("| Mumber of Solutions    | %-19s| %-19s|%n", numSolutionsILS, numSolutionsSA);
        System.out.println("--------------------------------------------------------------------");
        
        // Graphical Plot Representation Details
        // List<Solution> solutionsILS = detailsILS.getAllSolutions();
        // List<Solution> solutionsSA = detailsSA.getAllSolutions();

        System.out.println("SA Solutions:");
        detailsSA.printAllSolutions();
        System.out.println();
        System.out.println("ILS Solutions:");
        detailsILS.printAllSolutions();

        // System.out.println("ILS Solutions:");
        // for (int i = 0; i < solutionsILS.size(); i++) {
        //     System.out.println("Solution " + (i + 1) + ": " + solutions.get(i));
        // }
        // System.out.println("SA Solutions:");
        // for (int i = 0; i < solutionsSA.size(); i++) {
        //     System.out.println("Solution " + (i + 1) + ": " + solutions.get(i));
        // }
    }
}
