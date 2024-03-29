import java.util.ArrayList;
import java.util.List;

/**
 * Class representing details of multiple solutions.
 */
public class SolutionDetails {
    /** List of solutions. */
    private final List<Solution> solutions;

    /** The best solution among all solutions. */
    private Solution bestSolution;

    /**
     * Constructor to initialize an empty solution details object.
     */
    public SolutionDetails() {
        this.solutions = new ArrayList<>();
        this.bestSolution = null;
    }

    /**
     * Add a solution to the list of solutions.
     * Update the best solution if necessary.
     * @param solution The solution to add.
     */
    public void addSolution(Solution solution) {
        this.solutions.add(solution);
        if (bestSolution == null || solution.getDistance() < bestSolution.getDistance()) {
            bestSolution = solution;
        }
    }

    /**
     * Get the best solution among all solutions.
     * @return The best solution.
     */
    public Solution getBestSolution() {
        return bestSolution;
    }

    /**
     * Set the best solution among all solutions.
     * @param bestSolution The best solution to set.
     */
    public void setBestSolution(Solution bestSolution) {
        this.bestSolution = bestSolution;
    }

    /**
     * Calculate the average distance of all solutions.
     * @return The average distance.
     */
    public double getAverageDistance() {
        if (solutions.isEmpty()) return 0.0;
        int totalDistance = 0;
        for (Solution solution : solutions) {
            totalDistance += solution.getDistance();
        }
        return (double) totalDistance / solutions.size();
    }

    /**
     * Get a copy of the list of all solutions.
     * @return A list containing all solutions.
     */
    public List<Solution> getAllSolutions() {
        return new ArrayList<>(solutions);
    }

    /**
     * Print all solutions.
     */
    public void printAllSolutions() {
        System.out.println("All Solutions:");
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println("Solution " + (i + 1) + ": " + solutions.get(i));
        }
    }
}
