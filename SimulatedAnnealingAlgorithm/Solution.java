import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    private final List<Integer> route;
    private final int[][] distances;
    private int distance;

    public Solution(List<Integer> route, int[][] distances) {
        this.route = route;
        this.distances = distances;
        calculateDistance();
    }

    private void calculateDistance() {
        distance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            distance += distances[route.get(i)][route.get(i + 1)];
        }
        distance += distances[route.get(route.size() - 1)][route.get(0)]; // Return to the starting point
    }

    public Solution twoOptSwap(int i, int j) {
        List<Integer> newRoute = new ArrayList<>(route.subList(0, i));
        for (int k = j; k >= i; k--) {
            newRoute.add(route.get(k));
        }
        newRoute.addAll(route.subList(j + 1, route.size()));
        return new Solution(newRoute, distances);
    }

    public Solution swapCampuses(int index1, int index2) {
        List<Integer> newRoute = new ArrayList<>(route);
        Collections.swap(newRoute, index1, index2);
        return new Solution(newRoute, distances);
    }

    public List<Integer> getRoute() {
        return route;
    }

    public int getDistance() {
        return distance;
    }
}
