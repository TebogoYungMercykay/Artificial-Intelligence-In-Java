class TerminalNode extends Node {
    int index;

    TerminalNode(int index) {
        this.index = index;
    }

    @Override
    double eval(double[] inputs) {
        return inputs[index];
    }

    @Override
    Node copy() {
        return new TerminalNode(index);
    }

    @Override
    public String toString() {
        return "x" + index;
    }
}