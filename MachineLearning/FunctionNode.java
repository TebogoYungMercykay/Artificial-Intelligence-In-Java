class FunctionNode extends Node {
    char operator;

    FunctionNode(char operator) {
        this.operator = operator;
    }

    @Override
    double eval(double[] inputs) {
        double leftVal = left.eval(inputs);
        double rightVal = right.eval(inputs);

        switch (operator) {
            case '+':
                return leftVal + rightVal;
            case '-':
                return leftVal - rightVal;
            case '*':
                return leftVal * rightVal;
            case '/':
                return rightVal == 0 ? 1 : leftVal / rightVal;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    @Override
    Node copy() {
        FunctionNode copy = new FunctionNode(operator);
        copy.left = left.copy();
        copy.right = right.copy();
        return copy;
    }

    @Override
    public String toString() {
        return "(" + left + " " + operator + " " + right + ")";
    }
}