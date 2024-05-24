class Individual {
    Node root;
    double fitness;

    Individual(Node root) {
        this.root = root;
    }

    Individual copy() {
        return new Individual(root.copy());
    }

    void evaluate(double[][] dataset, double[] labels) {
        double sumError = 0.0;
        for (int i = 0; i < dataset.length; i++) {
            double prediction = root.eval(dataset[i]);
            double error = prediction - labels[i];
            sumError += error * error;
        }
        // Negative MSE
        fitness = -sumError / dataset.length;
    }

    double predict(double[] inputs) {
        return root.eval(inputs) > 0.5 ? 1.0 : 0.0;
    }

    @Override
    public String toString() {
        return "Individual{" + "root=" + root + ", fitness=" + fitness + '}';
    }
}