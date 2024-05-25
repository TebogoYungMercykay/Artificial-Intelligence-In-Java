/**
 * @file FunctionNode.java
 * 
 * @author Selepe Sello
 * @date 25 may 2024
 * @version 1.0
 * @brief Class representing a function node that performs an operation.
 */

class FunctionNode extends Node {
    char operator; ///< Operator for the function node

    /**
     * @brief Constructor for FunctionNode.
     * @param operator Operator to be used ('+', '-', '*', '/').
     */
    FunctionNode(char operator) {
        this.operator = operator;
    }

    @Override
    double eval(double[] inputs) {
        // Evaluate the left child node
        double leftVal = left.eval(inputs);
        // Evaluate the right child node
        double rightVal = right.eval(inputs);

        // Perform the operation based on the operator
        switch (operator) {
            case '+':
                return leftVal + rightVal;
            case '-':
                return leftVal - rightVal;
            case '*':
                return leftVal * rightVal;
            case '/':
                // Return 1 if the right value is 0 to avoid division by zero
                return rightVal == 0 ? 1 : leftVal / rightVal;
            default:
                // Throw an exception for an unknown operator
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    @Override
    Node copy() {
        // Create a copy of the function node with the same operator
        FunctionNode copy = new FunctionNode(operator);
        // Copy the left child node
        copy.left = left.copy();
        // Copy the right child node
        copy.right = right.copy();
        // Return the copied function node
        return copy;
    }

    @Override
    public String toString() {
        // Return a string representation of the function node
        return "(" + left + " " + operator + " " + right + ")";
    }
}