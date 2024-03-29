import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a solution to a problem.
 */
public class Solution {
    /** The route representing the solution. */
    private final List<Integer> route;

    /** The runtime of the algorithm to obtain the solution. */
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
     * Constructor to initialize a solution.
     * @param route The route representing the solution.
     * @param runtime The runtime of the algorithm to obtain the solution.
     */
    public Solution(List<Integer> route, long runtime) {
        this.route = route;
        this.runtime = runtime;
    }

    /**
     * Get the runtime of the algorithm to obtain the solution.
     * @return The runtime in milliseconds.
     */
    public long getRuntime() {
        return this.runtime;
    }

    /**
     * Set the runtime of the algorithm to obtain the solution.
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
            distance += this.distances[this.route.get(i)][this.route.get(i + 1)];
        }

        // Add the cost to the endpoint
        distance += this.distances[this.route.get(this.route.size() - 1)][this.route.get(0)];
        return distance;
    }

    /**
     * Check if this solution is better than another solution.
     * @param other The other solution to compare with.
     * @return True if this solution is better, false otherwise.
     */
    public Boolean isBetterThan(Solution other) {
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
     * @return The new solution after performing the swap.
     */
    public Solution twoOptSwap(int i, int j) {
        List<Integer> newRoute = new ArrayList<>(route.subList(0, i));

        for (int k = j; k >= i; k--) {
            newRoute.add(route.get(k));
        }
        newRoute.addAll(route.subList(j + 1, route.size()));
        
        // Runtime is not used for the swapped solution
        return new Solution(newRoute, 0);
    }

    /**
     * Swap the campuses at two given indexes in the route.
     * @param index1 The index of the first campus to swap.
     * @param index2 The index of the second campus to swap.
     * @return The new solution after swapping the campuses.
     */
    public Solution swapCampuses(int index1, int index2) {
        List<Integer> newRoute = new ArrayList<>(this.route);
        Collections.swap(newRoute, index1, index2);
        
        // Runtime is not used for the swapped solution
        return new Solution(newRoute, 0);
    }

    /**
     * Get the route representing the solution.
     * @return The route.
     */
    public List<Integer> getRoute() {
        return this.route;
    }

    /**
     * Get a string representation of the solution.
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "Route: " + this.getRouteString() + ", Distance: " + this.getDistance() + ", Runtime: " + this.runtime + "ms";
    }
}
