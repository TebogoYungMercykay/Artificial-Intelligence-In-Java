/**
 * @file ConstantNode.java
 * 
 * @author Selepe Sello
 * @date 25 may 2024
 * @version 1.0
 * @brief Class representing a constant node that holds a value.
 */

class ConstantNode extends Node {
    double value; ///< Constant value of the node

    /**
     * @brief Constructor for ConstantNode.
     * @param value Value to be held by the node.
     */
    ConstantNode(double value) {
        this.value = value;
    }

    @Override
    double eval(double[] inputs) {
        // Return the constant value
        return value;
    }

    @Override
    Node copy() {
        // Return a new ConstantNode with the same value
        return new ConstantNode(value);
    }

    @Override
    public String toString() {
        // Return a string representation of the constant node
        return String.valueOf(value);
    }
}