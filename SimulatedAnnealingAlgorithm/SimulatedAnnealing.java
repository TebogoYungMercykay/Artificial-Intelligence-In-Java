import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @file SimulatedAnnealing.java
 * 
 * @author Selepe Sello
 * @date 29 March 2021
 * @version 1.0
 * @brief Simulated Annealing algorithm for solving the campus tour problem.
 */

public class SimulatedAnnealing {

    /**  Constants */
    private static final int NUM_CAMPUSES = 5;
    private static final int MAX_ITERATIONS = 140;
    private static final double INITIAL_TEMPERATURE = 1000;
    private static final double COOLING_RATE = 0.99;
    private static final Random random = new Random();
    private static final RouteDetails routesList = new RouteDetails();

    /**
     * Runs the Simulated Annealing algorithm to find the best Route.
     * 
     * @return RouteDetails containing the details of the best Route found
     */
    public RouteDetails run() {
        long startTime = System.currentTimeMillis();
        Route currentRoute = generateInitialRoute();
        // routesList.addRoute(currentRoute);

        // Initialize temperature
        double temperature = INITIAL_TEMPERATURE;

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            
            // Perturb the current Route
            Route newRoute = perturb(currentRoute);
            
            // Calculate the change in distance between current and new Route
            double deltaDistance = newRoute.getDistance() - currentRoute.getDistance();
            
            // Acceptance criterion based on the Metropolis criterion
            if (deltaDistance < 0 || Math.exp(-deltaDistance / temperature) > random.nextDouble()) {
                currentRoute = newRoute;
            }
            
            // Cool down the temperature
            temperature *= COOLING_RATE;

            routesList.addRoute(newRoute);

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