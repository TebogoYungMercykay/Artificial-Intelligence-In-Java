public class KnapsackItem {

    private Double weight;
    private Double value;

    public KnapsackItem(Double value, Double weight) {
        this.weight = weight;
        this.value = value;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getValue() {
        return value;
    }

    public String toString() {
        return "Weight: " + weight + " Value: " + value;
    }

}
