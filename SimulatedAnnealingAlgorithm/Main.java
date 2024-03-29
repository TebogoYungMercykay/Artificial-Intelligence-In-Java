import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Iterated Local Search (ILS) Algorithm
        SimulatedAnnealing ils = new SimulatedAnnealing();
        SolutionDetails detailsILS = ils.run();
        int totalDistanceILS = 0;
        int numSolutionsILS = detailsILS.getAllSolutions().size();
        int bestDistanceILS = Integer.MAX_VALUE;
        long totalRuntimeILS = 0;
        int[] bestRouteILS = null;
    
        // Calculate ILS statistics
        for (Solution solution : detailsILS.getAllSolutions()) {
            totalDistanceILS += solution.getDistance();
            bestDistanceILS = Math.min(bestDistanceILS, solution.getDistance());
            totalRuntimeILS += solution.getRuntime();
            // System.out.println("Route: " + solution.getRoute() + ", Distance: " + solution.getDistance() + ", Runtime: " + solution.getRuntime());
        }
    
        if (detailsILS.getBestSolution() != null) {
            bestRouteILS = detailsILS.getBestSolution().getRoute().stream()
                .mapToInt(Integer::intValue)
                .toArray();
        }
    
        // Convert total runtime from milliseconds to seconds for ILS
        double totalRuntimeILSSecs = totalRuntimeILS / 1000.0;
        
        // Print summary table
        System.out.println("--------------------------------------------");
        System.out.println("| Problem Set            | ILS             |");
        System.out.println("|------------------------|-----------------|");
        System.out.printf("| Best Solution(route)   | %-16s|%n", getRouteString(bestRouteILS));
        System.out.printf("| Objective Function Val | %-16d|%n", bestDistanceILS);
        System.out.printf("| Runtime (s)            | %-16.2f|%n", totalRuntimeILSSecs);
        System.out.printf("| Av Obj Function        | %-16.2f|%n", (double) totalDistanceILS / numSolutionsILS);
        System.out.println("--------------------------------------------");
    }
    
    private static String getRouteString(int[] route) {
        if (route == null) return "N/A";
        StringBuilder routeString = new StringBuilder("[");
        for (int i = 0; i < route.length; i++) {
            routeString.append(route[i]);
            if (i < route.length - 1) {
                routeString.append(", ");
            }
        }
        routeString.append("]");
        return routeString.toString();
    }
}
