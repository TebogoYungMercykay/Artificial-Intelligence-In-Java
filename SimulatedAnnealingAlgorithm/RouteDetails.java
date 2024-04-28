import java.util.ArrayList;
import java.util.List;

/**
 * @file RouteDetails.java
 * 
 * @author Selepe Sello
 * @date 29 March 2024
 * @version 1.0
 * @brief Class representing details of multiple routes.
 */

public class RouteDetails {
    /** List of routes. */
    private final List<Route> routes;

    /** The best Route among all routes. */
    private Route bestRoute;
    private long runtime;

    /**
     * Constructor to initialize an empty Route details object.
     */
    public RouteDetails() {
        this.routes = new ArrayList<>();
        this.bestRoute = null;
    }

    /**
     * Add a Route to the list of routes.
     * Update the best Route if necessary.
     * @param Route The Route to add.
     */
    public void addRoute(Route route) {
        this.routes.add(route);
        if (bestRoute == null || route.getDistance() < bestRoute.getDistance()) {
            bestRoute = route;
        }
    }

    /**
     * Get the best Route among all routes.
     * @return The best Route.
     */
    public Route getBestRoute() {
        return bestRoute;
    }

    /**
     * Set the best Route among all routes.
     * @param bestRoute The best Route to set.
     */
    public void setBestRoute(Route bestRoute) {
        this.bestRoute = bestRoute;
    }

    /**
     * Calculate the average distance of all routes.
     * @return The average distance.
     */
    public double getAverageDistance() {
        if (routes.isEmpty()) return 0.0;
        int totalDistance = 0;
        for (Route Route : routes) {
            totalDistance += Route.getDistance();
        }
        return (double) totalDistance / routes.size();
    }

    /**
     * Calculate the total runtime of all routes.
     * @return The total runtime.
     */
    public double getTotalRuntime() {
        if (routes.isEmpty()) return 0.0;
        int totalRuntime = 0;
        for (Route Route : routes) {
            totalRuntime += Route.getRuntime();
        }
        return (double) totalRuntime / routes.size();
    }

    /**
     * Get a copy of the list of all routes.
     * @return A list containing all routes.
     */
    public List<Route> getAllRoutes() {
        return new ArrayList<>(routes);
    }

    /**
     * Print all routes.
     */
    public void printAllRoutes() {
        for (int i = 0; i < routes.size(); i++) {
            System.out.println("Route " + (i + 1) + ": " + routes.get(i));
        }
    }

    /**
     * Get the runtime of the algorithm to obtain the Route.
     * @return The runtime in milliseconds.
     */
    public long getRuntime() {
        return this.runtime;
    }

    /**
     * Set the runtime of the algorithm to obtain the Route.
     * @param runtime The runtime to set in milliseconds.
     */
    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }
}
