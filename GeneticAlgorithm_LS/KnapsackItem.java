/**
 * @file KnapsackItem.java
 * 
 * @author Selepe Sello
 * @date 26 April 2024
 * @version 1.0
 * @brief Class representing the Items (W_i, V_i) in a Knapsack.
 */

public class KnapsackItem {

    private Double weight;
    private Double value;

    /**
     * Constructs a KnapsackItem object with the specified value and weight.
     *
     * @param value the value of the item
     * @param weight the weight of the item
     */
    public KnapsackItem(Double value, Double weight) {
        this.weight = weight;
        this.value = value;
    }

    /**
     * Returns the weight of the item.
     *
     * @return the weight of the item
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * Returns the value of the item.
     *
     * @return the value of the item
     */
    public Double getValue() {
        return value;
    }

    /**
     * Returns a string representation of the KnapsackItem object.
     *
     * @return a string representation of the KnapsackItem object
     */
    public String toString() {
        return "Weight: " + weight + " Value: " + value;
    }
}
