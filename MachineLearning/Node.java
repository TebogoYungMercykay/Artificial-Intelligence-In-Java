/**
 * @file Node.java
 * 
 * @author Selepe Sello
 * @date 25 may 2024
 * @version 1.0
 * @brief Abstract class representing a node in a genetic programming tree.
 */

abstract class Node {
    Node left; ///< Left child node
    Node right; ///< Right child node

    /**
     * @brief Evaluates the node with given inputs.
     * @param inputs Array of input values.
     * @return Result of the evaluation.
     */
    abstract double eval(double[] inputs);

    /**
     * @brief Creates a copy of the node.
     * @return A new Node object that is a copy of this node.
     */
    abstract Node copy();
}