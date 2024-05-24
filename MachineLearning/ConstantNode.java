class ConstantNode extends Node {
    double value;

    ConstantNode(double value) {
        this.value = value;
    }

    @Override
    double eval(double[] inputs) {
        return value;
    }

    @Override
    Node copy() {
        return new ConstantNode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}