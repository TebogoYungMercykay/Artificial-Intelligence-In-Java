import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @file IteratedLocalSearch.java
 * 
 * @author Selepe Sello
 * @date 29 March 2024
 * @version 1.0
 * @brief Class representing the Iterated Local Search algorithm for solving optimization problems.
 */

public class IteratedLocalSearch {

    /**  Constants */
    private static final int NUM_CAMPUSES = 5;
    private static final int MAX_ITERATIONS = 3;
    private static final Random random = new Random();
    private static final RouteDetails routesList = new RouteDetails();

    /**
     * Runs the Iterated Local Search algorithm to find the best Route.
     * 
     * @return RouteDetails containing the details of the best Route found
     */
    public RouteDetails run() {
        // Main loop of the Iterated Local Search algorithm
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            // Generate & Perform local search on the initial Route
            Route currentRoute = generateInitialRoute();
            currentRoute = localSearch(currentRoute);

            // Perturb the current Route
            Route newRoute = perturb(currentRoute);
            
            // Perform local search on the perturbed Route
            Route newBestRoute = localSearch(newRoute);
            
            // Update the current Route if the new best Route is better
            if (newBestRoute.isBetterThan(currentRoute)) {
                currentRoute = newBestRoute;
            }
            
            // Update the best Route in the RouteDetails
            if (currentRoute.isBetterThan(routesList.getBestRoute())) {
                routesList.setBestRoute(currentRoute);
            }
        }
        
        long endTime = System.currentTimeMillis();
        long runtime = endTime - startTime;
        routesList.setRuntime(runtime);

        return routesList;
    }

    /**
     * Generates an initial Route by randomly shuffling campuses.
     * @param distances the distance matrix
     * @return the initial Route
     */
    private Route generateInitialRoute() {
        List<Integer> campuses = new ArrayList<>();
        for (int i = 0; i < NUM_CAMPUSES; i++) {
            campuses.add(i);
        }
        // Adding the starting campus at the end
        campuses.add(0);

        // Randomly shuffle the campuses from index 1 to NUM_CAMPUSES
        Collections.shuffle(campuses.subList(1, NUM_CAMPUSES));
        return new Route(campuses, 0);
    }

    /**
     * Performs local search to improve the given Route.
     * @param Route The Route to improve.
     * @return The improved Route.
     */
    private Route localSearch(Route Route) {
        Route bestRoute = Route;
        boolean improved;
        do {
            improved = false;
            for (int i = 1; i < NUM_CAMPUSES; i++) {
                for (int j = i + 1; j <= NUM_CAMPUSES; j++) {
                    Route newRoute = Route.twoOptSwap(i, j);
                    routesList.addRoute(newRoute);
                    if (newRoute.isBetterThan(bestRoute)) {
                        bestRoute = newRoute;
                        improved = true;
                    }
                }
            }
            Route = bestRoute;
        } while (improved);
        return bestRoute;
    }

    /**
     * Perturbs the given Route to explore new Routes.
     * @param Route The Route to perturb.
     * @return The perturbed Route.
     */
    private Route perturb(Route Route) {
        int index1 = 1 + random.nextInt(NUM_CAMPUSES - 1);
        int index2;
        do {
            index2 = 1 + random.nextInt(NUM_CAMPUSES - 1);
        } while (index1 == index2);
        return Route.swapCampuses(index1, index2);
    }
}
