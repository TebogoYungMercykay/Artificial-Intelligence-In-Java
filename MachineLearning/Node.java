abstract class Node {
    Node left, right;

    abstract double eval(double[] inputs);

    abstract Node copy();
}