import java.util.Arrays;
import java.util.Random;

public class ANN {

    // Variables
    private Double[] inputLayerNodes;
    private Double[] hiddenLayerNodes;
    private Double outputLayerNode;

    // Weights
    private Double[][] weightsIH;
    private Double[] weightsHO;
    private double[] hiddenBiasWeights;
    private double outputBiasWeight;

    // Random object
    private Random random;

    // Parameters
    private final double learningRate = 0.01;
    private final int hiddenLayerSize = 4;

    // Performance metrics
    private double truePositive = 0;
    private double trueNegative = 0;
    private double falsePositive = 0;
    private double falseNegative = 0;

    // Constructor
    public ANN(long seed, int inputSize) {
        this.random = new Random(seed);
        initialize(inputSize - 1);
    }

    // Initialize network
    private void initialize(int inputSize) {
        inputLayerNodes = new Double[inputSize];
        hiddenLayerNodes = new Double[hiddenLayerSize];
        hiddenBiasWeights = new double[hiddenLayerSize];
        weightsIH = new Double[inputSize][hiddenLayerSize];
        weightsHO = new Double[hiddenLayerSize];
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

    // Feedforward
    private void feedforward() {
        for (int i = 0; i < hiddenLayerNodes.length; i++) {
            hiddenLayerNodes[i] = 0.0;
            for (int j = 0; j < inputLayerNodes.length; j++) {
                hiddenLayerNodes[i] += inputLayerNodes[j] * weightsIH[j][i];
            }
            hiddenLayerNodes[i] += hiddenBiasWeights[i];
            hiddenLayerNodes[i] = ReLU(hiddenLayerNodes[i]);
        }
        outputLayerNode = 0.0;
        for (int j = 0; j < hiddenLayerNodes.length; j++) {
            outputLayerNode += hiddenLayerNodes[j] * weightsHO[j];
        }
        outputLayerNode += outputBiasWeight;
        outputLayerNode = sigmoid(outputLayerNode);
    }

    // Backpropagation
    private void backpropagation(Double target) {
        Double outputError = calcOutputError(outputLayerNode, target);
        for (int i = 0; i < weightsHO.length; i++) {
            weightsHO[i] += learningRate * outputError * hiddenLayerNodes[i];
        }
        outputBiasWeight += learningRate * outputError;
        Double[] hiddenError = new Double[hiddenLayerNodes.length];
        for (int i = 0; i < hiddenLayerNodes.length; i++) {
            hiddenError[i] = calcHiddenError(hiddenLayerNodes[i], outputError, weightsHO, i);
        }
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
    private double ReLU(double n) {
        return Math.max(0, n);
    }

    private double sigmoid(double n) {
        return 1 / (1 + Math.exp(-n));
    }

    // Error calculations
    private double calcOutputError(Double output, Double target) {
        return (target - output) * sigmoidDerivative(output);
    }

    private double sigmoidDerivative(double n) {
        return n * (1 - n);
    }

    private double calcHiddenError(Double hidden, Double outputError, Double[] weightsHO, int index) {
        return (outputError * weightsHO[index]) * ReLuDerivative(hidden);
    }

    private double ReLuDerivative(double n) {
        // Derivative of ReLU activation function
        if (n > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    // Random initialization
    private double randomInitilization() {
        return random.nextGaussian() * 0.07;
    }

    // Test the network
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

    // Test the network
    public void test(double[] dataSet, Double target) {
        for (int i = 0; i < inputLayerNodes.length; i++) {
            inputLayerNodes[i] = dataSet[i];
        }
        feedforward();
        double prediction = outputLayerNode >= 0.5 ? 1.0 : 0.0;
        updateMetrics(prediction, target);
    }

    // Train the network
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

    // Train the network
    public void train(double[] dataSet, Double target) {
        for (int i = 0; i < inputLayerNodes.length; i++) {
            inputLayerNodes[i] = dataSet[i];
        }
        feedforward();
        backpropagation(target);
        double prediction = outputLayerNode >= 0.5 ? 1.0 : 0.0;
        updateMetrics(prediction, target);
    }

    public void resetValues() {
        // Reset the Values
        truePositive = 0;
        trueNegative = 0;
        falsePositive = 0;
        falseNegative = 0;
    }

    // Update performance metrics based on prediction and target
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

    // Calculate and return accuracy
    public double getAccuracy() {
        return (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
    }

    // Calculate and return specificity
    public double getSpecificity() {
        return trueNegative / (trueNegative + falsePositive);
    }

    // Calculate and return sensitivity
    public double getSensitivity() {
        return truePositive / (truePositive + falseNegative);
    }

    // Calculate and return F-measure
    public double getFMeasure() {
        double precision = truePositive / (truePositive + falsePositive);
        double recall = truePositive / (truePositive + falseNegative);
        return 2 * (precision * recall) / (precision + recall);
    }
}
