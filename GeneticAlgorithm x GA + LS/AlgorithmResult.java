/**
 * @file AlgorithmResult.java
 * 
 * @author Selepe Sello
 * @date 26 April 2024
 * @version 1.0
 * @brief The AlgorithmResult class represents the result of a problem instance solved by an algorithm.
 */

 public class AlgorithmResult {
    private String problemInstance;
    private String algorithm;
    private long seedValue;
    private double bestSolution;
    private double knownOptimum;
    private double runtimeSeconds;

    /**
     * Constructs a new Result with the specified values.
     *
     * @param problemInstance the problem instance
     * @param algorithm the algorithm used
     * @param seedValue the seed value used
     * @param bestSolution the best solution found
     * @param knownOptimum the known optimum of the problem instance
     * @param runtimeSeconds the runtime in seconds
     */
    public AlgorithmResult(String problemInstance, String algorithm, long seedValue, double bestSolution, double knownOptimum, double runtimeSeconds) {
        this.problemInstance = problemInstance;
        this.algorithm = algorithm;
        this.seedValue = seedValue;
        this.bestSolution = bestSolution;
        this.knownOptimum = knownOptimum;
        this.runtimeSeconds = runtimeSeconds;
    }

    /**
     * Returns the problem instance.
     *
     * @return the problem instance
     */
    public String getProblemInstance() {
        return problemInstance;
    }

    /**
     * Sets the problem instance.
     *
     * @param problemInstance the problem instance
     */
    public void setProblemInstance(String problemInstance) {
        this.problemInstance = problemInstance;
    }

    /**
     * Returns the algorithm used.
     *
     * @return the algorithm
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * Sets the algorithm used.
     *
     * @param algorithm the algorithm
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Returns the seed value used.
     *
     * @return the seed value
     */
    public long getSeedValue() {
        return seedValue;
    }

    /**
     * Sets the seed value used.
     *
     * @param seedValue the seed value
     */
    public void setSeedValue(long seedValue) {
        this.seedValue = seedValue;
    }

    /**
     * Returns the best solution found.
     *
     * @return the best solution
     */
    public double getBestSolution() {
        return bestSolution;
    }

    /**
     * Sets the best solution found.
     *
     * @param bestSolution the best solution
     */
    public void setBestSolution(double bestSolution) {
        this.bestSolution = bestSolution;
    }

    /**
     * Returns the known optimum of the problem instance.
     *
     * @return the known optimum
     */
    public double getKnownOptimum() {
        return knownOptimum;
    }

    /**
     * Sets the known optimum of the problem instance.
     *
     * @param knownOptimum the known optimum
     */
    public void setKnownOptimum(double knownOptimum) {
        this.knownOptimum = knownOptimum;
    }

    /**
     * Returns the runtime in seconds.
     *
     * @return the runtime in seconds
     */
    public double getRuntimeSeconds() {
        return runtimeSeconds;
    }

    /**
     * Sets the runtime in seconds.
     *
     * @param runtimeSeconds the runtime in seconds
     */
    public void setRuntimeSeconds(double runtimeSeconds) {
        this.runtimeSeconds = runtimeSeconds;
    }
}