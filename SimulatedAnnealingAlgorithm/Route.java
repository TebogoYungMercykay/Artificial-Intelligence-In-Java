import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @file Route.java
 * 
 * @author Selepe Sello
 * @date 29 March 2021
 * @version 1.0
 * @brief Class representing a Route to a problem.
 */

public class Route {
    /** The route representing the Route. */
    private final List<Integer> route;

    /** The runtime of the algorithm to obtain the Route. */
    private long runtime;

    /** The distance matrix between campuses. */
    private final int[][] distances = {
            {0, 15, 20, 22, 30},
            {15, 0, 10, 12, 25},
            {20, 10, 0, 8, 22},
            {22, 12, 8, 0, 18},
            {30, 25, 22, 18, 0}
    };

    /**
     * Constructor to initialize a Route.
     * @param route The route representing the Route.
     * @param runtime The runtime of the algorithm to obtain the Route.
     */
    public Route(List<Integer> route, long runtime) {
        this.route = route;
        this.runtime = runtime;
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

    /**
     * Calculate the total distance of the route.
     * @return The total distance.
     */
    public int getDistance() {
        int distance = 0;

        for (int i = 0; i < this.route.size() - 1; i++) {
            distance += this.distances[this.route.get(i) % 5][this.route.get(i + 1) % 5];
        }

        // Add the cost to the endpoint
        distance += this.distances[this.route.get(this.route.size() - 1) % 5][this.route.get(0) % 5];
        return distance;
    }

    /**
     * Check if this Route is better than another Route.
     * @param other The other Route to compare with.
     * @return True if this Route is better, false otherwise.
     */
    public Boolean isBetterThan(Route other) {
        if (other == null) return true;
        return this.getDistance() < other.getDistance();
    }

    /**
     * Get a string representation of the route.
     * @return The route as a string.
     */
    public String getRouteString() {
        if (this.route == null || this.route.isEmpty()) return "N/A";

        StringBuilder routeString = new StringBuilder("[");
        for (int i = 0; i < this.route.size(); i++) {
            routeString.append(this.route.get(i));
            if (i < this.route.size() - 1) {
                routeString.append(", ");
            }
        }

        routeString.append("]");
        return routeString.toString();
    }

    /**
     * Perform a 2-opt swap on the route.
     * @param i The index of the start of the segment to swap.
     * @param j The index of the end of the segment to swap.
     * @return The new Route after performing the swap.
     */
    public Route twoOptSwap(int i, int j) {
        List<Integer> newRoute = new ArrayList<>(route.subList(0, i));

        for (int k = j; k >= i; k--) {
            newRoute.add(route.get(k));
        }
        newRoute.addAll(route.subList(j + 1, route.size()));
        
        // Runtime is not used for the swapped Route
        return new Route(newRoute, 0);
    }

    /**
     * Swap the campuses at two given indexes in the route.
     * @param index1 The index of the first campus to swap.
     * @param index2 The index of the second campus to swap.
     * @return The new Route after swapping the campuses.
     */
    public Route swapCampuses(int index1, int index2) {
        List<Integer> newRoute = new ArrayList<>(this.route);
        Collections.swap(newRoute, index1, index2);
        
        // Runtime is not used for the swapped Route
        return new Route(newRoute, 0);
    }

    /**
     * Get the route representing the Route.
     * @return The route.
     */
    public List<Integer> getRoute() {
        return this.route;
    }

    /**
     * Get a string representation of the Route.
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "Route: " + this.getRouteString() + ", Distance: " + this.getDistance() + ", Runtime: " + this.runtime + "ms";
    }
}
