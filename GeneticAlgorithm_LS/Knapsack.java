import java.util.ArrayList;

/**
 * The Knapsack class represents a knapsack problem.
 */
public class Knapsack {

    private int capacity;
    private ArrayList<KnapsackItem> items;

    /**
     * Constructs a new Knapsack with the specified capacity and number of items.
     *
     * @param capacity the capacity of the knapsack
     * @param numItems the number of items
     */
    public Knapsack(int capacity, int numItems) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    /**
     * Returns the capacity of the knapsack.
     *
     * @return the capacity of the knapsack
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the items in the knapsack.
     *
     * @return the items in the knapsack
     */
    public ArrayList<KnapsackItem> getItems() {
        return items;
    }

    /**
     * Adds an item to the knapsack.
     *
     * @param item the item to add
     */
    public void addItem(KnapsackItem item) {
        items.add(item);
    }

    /**
     * Removes an item from the knapsack.
     *
     * @param item the item to remove
     */
    public void removeItem(KnapsackItem item) {
        items.remove(item);
    }

    /**
     * Returns the total weight of the knapsack given a solution.
     *
     * @param solution the solution
     * @return the total weight of the knapsack
     */
    public double getWeight(Boolean[] solution) {
        double totalWeight = 0;
        for (int i = 0; i < items.size(); i++) {
            if (solution[i]) {
                totalWeight += items.get(i).getWeight();
            }
        }
        return totalWeight;
    }

    /**
     * Returns the total value of the knapsack given a solution.
     *
     * @param solution the solution
     * @return the total value of the knapsack
     */
    public double getValue(Boolean[] solution) {
        double totalValue = 0;
        for (int i = 0; i < items.size(); i++) {
            if (solution[i]) {
                totalValue += items.get(i).getValue();
            }
        }
        return totalValue;
    }

    /**
     * Returns the total weight of the items in the knapsack.
     *
     * @return the total weight of the items in the knapsack
     */
    public double getTotalWeight() {
        double totalWeight = 0;
        for (int i = 0; i < items.size(); i++) {
            totalWeight += items.get(i).getWeight();
        }
        return totalWeight;
    }

    /**
     * Returns the total value of the items in the knapsack.
     *
     * @return the total value of the items in the knapsack
     */
    public double getTotalValue() {
        double totalValue = 0;
        for (int i = 0; i < items.size(); i++) {
            totalValue += items.get(i).getValue();
        }
        return totalValue;
    }
}
