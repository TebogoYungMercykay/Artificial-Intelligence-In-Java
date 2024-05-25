/**
 * @file Individual.java
 * 
 * @author Selepe Sello
 * @date 25 may 2024
 * @version 1.0
 * @brief Class representing an individual in the genetic programming population.
 */

class Individual {
    Node root; ///< Root node of the individual's genetic program
    double fitness; ///< Fitness of the individual

    /**
     * @brief Constructor for Individual.
     * @param root Root node of the genetic program.
     */
    Individual(Node root) {
        this.root = root;
    }

    /**
     * @brief Creates a copy of the individual.
     * @return A new Individual object that is a copy of this individual.
     */
    Individual copy() {
        // Return a new Individual with a copied root node
        return new Individual(root.copy());
    }

    /**
     * @brief Evaluates the individual's fitness on a given dataset.
     * @param dataset Array of input data samples.
     * @param labels Array of expected output values.
     */
    void evaluate(double[][] dataset, double[] labels) {
        // Initialize the sum of squared errors
        double sumError = 0.0;
        // Loop through each data sample
        for (int i = 0; i < dataset.length; i++) {
            // Evaluate the root node with the current data sample
            double prediction = root.eval(dataset[i]);
            // Calculate the error
            double error = prediction - labels[i];
            // Add the squared error to the sum
            sumError += error * error;
        }
        // Calculate and store the negative mean squared error as fitness
        fitness = -sumError / dataset.length;
    }

    /**
     * @brief Predicts the output for a given input sample.
     * @param inputs Array of input values.
     * @return Predicted output (1.0 or 0.0).
     */
    double predict(double[] inputs) {
        // Evaluate the root node and return 1.0 if the result is greater than 0.5, otherwise 0.0
        return root.eval(inputs) > 0.5 ? 1.0 : 0.0;
    }

    @Override
    public String toString() {
        // Return a string representation of the individual
        return "Individual{" + "root=" + root + ", fitness=" + fitness + '}';
    }
}