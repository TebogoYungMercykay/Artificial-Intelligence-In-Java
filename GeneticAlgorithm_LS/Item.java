public class Item {

    private Double weight;
    private Double value;

    public Item(Double value, Double weight) {
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
