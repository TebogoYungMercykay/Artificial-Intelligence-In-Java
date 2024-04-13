import java.util.ArrayList;

public class Knapsack {

    private int capacity;
    private ArrayList<Item> items;

    public Knapsack(int capacity, int numItems) {
        this.capacity = capacity;
        items = new ArrayList<Item>();
    }

    /**
     * @brief Get the capacity of the knapsack
     * 
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @brief Get the items in the knapsack
     * 
     * @return items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * @brief Add an item to the knapsack
     * 
     * @param item
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * @brief Remove an item from the knapsack
     * 
     * @param item
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * @brief Get the total weight of the knapsack given a solution
     * 
     * @return totalWeight
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
     * @brief Get the total value of the knapsack given a solution
     * 
     * @return totalValue
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
     * @brief Get the total weight of the items in the knapsack
     * @return
     */
    public double getTotalWeight() {
        double totalWeight = 0;
        for (int i = 0; i < items.size(); i++) {
            totalWeight += items.get(i).getWeight();
        }
        return totalWeight;
    }

    /**
     * @brief Get the total value of the items in the knapsack
     * @return
     */
    public double getTotalValue() {
        double totalValue = 0;
        for (int i = 0; i < items.size(); i++) {
            totalValue += items.get(i).getValue();
        }
        return totalValue;
    }
}
