import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Simulated Annealing (SA) Algorithm
        SimulatedAnnealing sa = new SimulatedAnnealing();
        List<SolutionDetails> allDetailsSimulatedAnnealing = sa.run();
        int totalDistanceSA = 0;
        int numSolutionsSA = allDetailsSimulatedAnnealing.size();
        int bestDistanceSA = Integer.MAX_VALUE;
        long totalRuntimeSA = 0;
        int[] bestRouteSA = null;
    
        // Calculate SA statistics
        for (SolutionDetails details : allDetailsSimulatedAnnealing) {
            totalDistanceSA += details.getDistance();
            bestDistanceSA = Math.min(bestDistanceSA, details.getDistance());
            totalRuntimeSA += details.getRuntime();
        }
    
        if (!allDetailsSimulatedAnnealing.isEmpty()) {
            bestRouteSA = allDetailsSimulatedAnnealing.get(0).getSolution().getRoute().stream()
                .mapToInt(Integer::intValue)
                .toArray();
        }
    
        // Convert total runtime from milliseconds to seconds for SA
        double totalRuntimeSASecs = totalRuntimeSA / 1000.0;
        
        // Print summary table
        System.out.println("--------------------------------------------");
        System.out.println("| Problem Set            | SA              |");
        System.out.println("|------------------------|-----------------|");
        System.out.printf("| Best Solution(route)   | %-16s|%n",  getRouteString(bestRouteSA));
        System.out.printf("| Objective Function Val | %-16d|%n",  bestDistanceSA);
        System.out.printf("| Runtime (s)            | %-16.2f|%n", totalRuntimeSASecs);
        System.out.printf("| Av Obj Function        | %-16.2f|%n", (double) totalDistanceSA / numSolutionsSA);
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