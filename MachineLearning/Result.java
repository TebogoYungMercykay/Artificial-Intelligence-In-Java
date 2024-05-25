/**
 * @file Result.java
 * 
 * @author Selepe Sello
 * @date 25 may 2024
 * @version 1.0
 * @brief A class representing the result of a machine learning model evaluation.
 *
 * This class stores the evaluation metrics of a machine learning model, such as 
 * accuracy, specificity, sensitivity, and F-measure, along with the algorithm name, 
 * type of evaluation (training/testing), and runtime.
 */
public class Result {
    private String algorithm;    ///< The name of the algorithm
    private String type;         ///< The type of evaluation (e.g., Training, Testing)
    private long runtime;        ///< The runtime in milliseconds
    private double accuracy;     ///< The accuracy of the model
    private double specificity;  ///< The specificity of the model
    private double sensitivity;  ///< The sensitivity of the model
    private double fMeasure;     ///< The F-measure of the model

    /**
     * @brief Constructor for the Result class.
     *
     * @param algorithm The name of the algorithm.
     * @param type The type of evaluation (e.g., Training, Testing).
     * @param runtime The runtime in milliseconds.
     * @param accuracy The accuracy of the model.
     * @param specificity The specificity of the model.
     * @param sensitivity The sensitivity of the model.
     * @param fMeasure The F-measure of the model.
     */
    public Result(String algorithm, String type, long runtime, double accuracy, double specificity, double sensitivity, double fMeasure) {
        this.algorithm = algorithm;
        this.type = type;
        this.runtime = runtime;
        this.accuracy = accuracy;
        this.specificity = specificity;
        this.sensitivity = sensitivity;
        this.fMeasure = fMeasure;
    }

    /**
     * @brief Returns a string representation of the Result object.
     *
     * The string is formatted to display the algorithm name, runtime, type of 
     * evaluation, accuracy, specificity, sensitivity, and F-measure.
     *
     * @return A formatted string representing the Result object.
     */
    @Override
    public String toString() {
        return String.format("%-3s | %15d (%-8s) | %7.2f%% | %9.2f%% | %10.2f%% | %9.2f%%", 
                algorithm, runtime, type, accuracy * 100, specificity * 100, sensitivity * 100, fMeasure * 100);
    }
}
