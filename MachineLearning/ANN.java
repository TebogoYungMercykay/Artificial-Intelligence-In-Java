import java.util.Arrays;
import java.util.Random;

/**
 * @file ANN.java
 * 
 * @author Selepe Sello
 * @date 25 may 2024
 * @version 1.0
 * @brief Class representing an Artificial Neural Network (ANN) with one hidden layer.
 */

public class ANN {
    // Variables
    private Double[] inputLayerNodes; ///< Nodes in the input layer
    private Double[] hiddenLayerNodes; ///< Nodes in the hidden layer
    private Double outputLayerNode; ///< Node in the output layer

    // Weights
    private Double[][] weightsIH; ///< Weights between input and hidden layers
    private Double[] weightsHO; ///< Weights between hidden and output layers
    private double[] hiddenBiasWeights; ///< Bias weights for hidden layer nodes
    private double outputBiasWeight; ///< Bias weight for output layer node

    // Random object
    private Random random; ///< Random object for initializing weights

    // Parameters
    private final double learningRate = 0.01; ///< Learning rate for the ANN
    private final int hiddenLayerSize = 4; ///< Number of nodes in the hidden layer

    // Performance metrics
    private double truePositive = 0; ///< True positive count
    private double trueNegative = 0; ///< True negative count
    private double falsePositive = 0; ///< False positive count
    private double falseNegative = 0; ///< False negative count

    /**
     * @brief Constructor for ANN.
     * @param seed Random seed for weight initialization.
     * @param inputSize Size of the input layer.
     */
    public ANN(long seed, int inputSize) {
        this.random = new Random(seed);
        initialize(inputSize - 1);
    }

    /**
     * @brief Initializes the network by setting up nodes and weights.
     * @param inputSize Size of the input layer.
     */
    private void initialize(int inputSize) {
        inputLayerNodes = new Double[inputSize];
        hiddenLayerNodes = new Double[hiddenLayerSize];
        hiddenBiasWeights = new double[hiddenLayerSize];
        weightsIH = new Double[inputSize][hiddenLayerSize];
        weightsHO = new Double[hiddenLayerSize];

        // Initialize weights and biases
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < hiddenLayerSize; j++) {
                weightsIH[i][j] = randomInitilization();
                if (i == 0) {
                    hiddenBiasWeights[j] = randomInitilization();
                }
            }
        }
        for (int i = 0; i < hiddenLayerSize; i++) {
            weightsHO[i] = randomInitilization();
        }
        outputBiasWeight = randomInitilization();
    }

    /**
     * @brief Performs the feedforward step in the network.
     */
    private void feedforward() {
        // Calculate hidden layer activations
        for (int i = 0; i < hiddenLayerNodes.length; i++) {
            hiddenLayerNodes[i] = 0.0;
            for (int j = 0; j < inputLayerNodes.length; j++) {
                hiddenLayerNodes[i] += inputLayerNodes[j] * weightsIH[j][i];
            }
            hiddenLayerNodes[i] += hiddenBiasWeights[i];
            hiddenLayerNodes[i] = ReLU(hiddenLayerNodes[i]);
        }

        // Calculate output layer activation
        outputLayerNode = 0.0;
        for (int j = 0; j < hiddenLayerNodes.length; j++) {
            outputLayerNode += hiddenLayerNodes[j] * weightsHO[j];
        }
        outputLayerNode += outputBiasWeight;
        outputLayerNode = sigmoid(outputLayerNode);
    }

    /**
     * @brief Performs the backpropagation step to adjust weights based on error.
     * @param target Target value for the output.
     */
    private void backpropagation(Double target) {
        Double outputError = calcOutputError(outputLayerNode, target);

        // Adjust weights and biases for hidden to output layer
        for (int i = 0; i < weightsHO.length; i++) {
            weightsHO[i] += learningRate * outputError * hiddenLayerNodes[i];
        }
        outputBiasWeight += learningRate * outputError;

        // Calculate hidden layer errors
        Double[] hiddenError = new Double[hiddenLayerNodes.length];
        for (int i = 0; i < hiddenLayerNodes.length; i++) {
            hiddenError[i] = calcHiddenError(hiddenLayerNodes[i], outputError, weightsHO, i);
        }

        // Adjust weights and biases for input to hidden layer
        for (int i = 0; i < weightsIH.length; i++) {
            for (int j = 0; j < weightsIH[i].length; j++) {
                weightsIH[i][j] += learningRate * hiddenError[j] * inputLayerNodes[i];
            }
        }
        for (int i = 0; i < hiddenBiasWeights.length; i++) {
            hiddenBiasWeights[i] += learningRate * hiddenError[i];
        }
    }

    // Activation functions
    /**
     * @brief ReLU activation function.
     * @param n Input value.
     * @return Output after applying ReLU.
     */
    private double ReLU(double n) {
        return Math.max(0, n);
    }

    /**
     * @brief Sigmoid activation function.
     * @param n Input value.
     * @return Output after applying sigmoid.
     */
    private double sigmoid(double n) {
        return 1 / (1 + Math.exp(-n));
    }

    // Error calculations
    /**
     * @brief Calculates the output error.
     * @param output Actual output.
     * @param target Target output.
     * @return Calculated output error.
     */
    private double calcOutputError(Double output, Double target) {
        return (target - output) * sigmoidDerivative(output);
    }

    /**
     * @brief Calculates the derivative of the sigmoid function.
     * @param n Input value.
     * @return Sigmoid derivative.
     */
    private double sigmoidDerivative(double n) {
        return n * (1 - n);
    }

    /**
     * @brief Calculates the hidden layer error.
     * @param hidden Hidden node value.
     * @param outputError Error in the output layer.
     * @param weightsHO Weights between hidden and output layer.
     * @param index Index of the hidden node.
     * @return Calculated hidden layer error.
     */
    private double calcHiddenError(Double hidden, Double outputError, Double[] weightsHO, int index) {
        return (outputError * weightsHO[index]) * ReLuDerivative(hidden);
    }

    /**
     * @brief Calculates the derivative of the ReLU function.
     * @param n Input value.
     * @return ReLU derivative.
     */
    private double ReLuDerivative(double n) {
        return n > 0 ? 1 : 0;
    }

    /**
     * @brief Initializes weights randomly.
     * @return Initialized weight value.
     */
    private double randomInitilization() {
        return random.nextGaussian() * 0.07;
    }

    /**
     * @brief Tests the network with the given test data.
     * @param hotDataMatrixTest Test data matrix.
     */
    public void test(double[][] hotDataMatrixTest) {
        resetValues();
        for (double[] data : hotDataMatrixTest) {
            // Assuming the target value is the last element
            double target = data[data.length - 1];
            // Remove target value
            double[] input = Arrays.copyOf(data, data.length - 1);
            test(input, target);
        }
    }

    /**
     * @brief Tests the network with a single data instance.
     * @param dataSet Data instance.
     * @param target Target value.
     */
    public void test(double[] dataSet, Double target) {
        for (int i = 0; i < inputLayerNodes.length; i++) {
            inputLayerNodes[i] = dataSet[i];
        }
        feedforward();
        double prediction = outputLayerNode >= 0.5 ? 1.0 : 0.0;
        updateMetrics(prediction, target);
    }

    /**
     * @brief Trains the network with the given training data.
     * @param hotDataMatrixTrain Training data matrix.
     */
    public void train(double[][] hotDataMatrixTrain) {
        resetValues();
        for (double[] data : hotDataMatrixTrain) {
            // Assuming the target value is the last element
            double target = data[data.length - 1];
            // Remove target value
            double[] input = Arrays.copyOf(data, data.length - 1);
            train(input, target);
        }
    }

    /**
     * @brief Trains the network with a single data instance.
     * @param dataSet Data instance.
     * @param target Target value.
     */
    public void train(double[] dataSet, Double target) {
        for (int i = 0; i < inputLayerNodes.length; i++) {
            inputLayerNodes[i] = dataSet[i];
        }
        feedforward();
        backpropagation(target);
        double prediction = outputLayerNode >= 0.5 ? 1.0 : 0.0;
        updateMetrics(prediction, target);
    }

    /**
     * @brief Resets the performance metrics.
     */
    public void resetValues() {
        // Reset the Values
        truePositive = 0;
        trueNegative = 0;
        falsePositive = 0;
        falseNegative = 0;
    }

    /**
     * @brief Updates performance metrics based on the prediction and target.
     * @param prediction Predicted value.
     * @param target Target value.
     */
    private void updateMetrics(double prediction, double target) {
        if (prediction == 1.0 && target == 1.0) {
            truePositive++;
        } else if (prediction == 0.0 && target == 0.0) {
            trueNegative++;
        } else if (prediction == 1.0 && target == 0.0) {
            falsePositive++;
        } else if (prediction == 0.0 && target == 1.0) {
            falseNegative++;
        }
    }

    /**
     * @brief Calculates and returns the accuracy of the network.
     * @return Accuracy as a double.
     */
    public double getAccuracy() {
        return (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
    }

    /**
     * @brief Calculates and returns the specificity of the network.
     * @return Specificity as a double.
     */
    public double getSpecificity() {
        return trueNegative / (trueNegative + falsePositive);
    }

    /**
     * @brief Calculates and returns the sensitivity of the network.
     * @return Sensitivity as a double.
     */
    public double getSensitivity() {
        return truePositive / (truePositive + falseNegative);
    }

    /**
     * @brief Calculates and returns the F-measure of the network.
     * @return F-measure as a double.
     */
    public double getFMeasure() {
        double precision = truePositive / (truePositive + falsePositive);
        double recall = truePositive / (truePositive + falseNegative);
        return 2 * (precision * recall) / (precision + recall);
    }
}
