/**
 * @file TerminalNode.java
 * 
 * @author Selepe Sello
 * @date 25 may 2024
 * @version 1.0
 * @brief Class representing a terminal node that holds an index.
 */

class TerminalNode extends Node {
    int index; ///< Index of the input to be used

    /**
     * @brief Constructor for TerminalNode.
     * @param index Index of the input.
     */
    TerminalNode(int index) {
        this.index = index;
    }

    @Override
    double eval(double[] inputs) {
        // Return the input value at the given index
        return inputs[index];
    }

    @Override
    Node copy() {
        // Return a new TerminalNode with the same index
        return new TerminalNode(index);
    }

    @Override
    public String toString() {
        // Return a string representation of the terminal node
        return "x" + index;
    }
}