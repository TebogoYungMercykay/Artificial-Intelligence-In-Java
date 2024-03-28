public class SolutionDetails {
    private final Solution solution;
    private final long runtime;

    public SolutionDetails(Solution solution, long runtime) {
        this.solution = solution;
        this.runtime = runtime;
    }

    public int getDistance() {
        return solution.getDistance();
    }

    public long getRuntime() {
        return runtime;
    }

    public Solution getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        return "Route: " + solution.getRoute() + ", Distance: " + solution.getDistance() + ", Runtime: " + runtime + "ms";
    }
}
