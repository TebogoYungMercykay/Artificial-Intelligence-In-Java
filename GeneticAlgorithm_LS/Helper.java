import java.util.HashMap;
import java.io.*;

public class Helper {

    /**
     * 
     * @param folderName
     * @return a HashMap of Knapsacks
     */
    public static HashMap<String, Knapsack> readKnapsackData(String folderName) {

        HashMap<String, Knapsack> knapsacks = new HashMap<String, Knapsack>();
        File folder = new File("./Code" + File.separator + folderName);
        if (!folder.exists()) {
            folder = new File("./" + folderName);
        }

        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                Knapsack knapsack = null;
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    // First line has two numbers, the first is the number of items, the second is
                    // the capacity of the knapsack
                    String line = br.readLine();
                    int numItems = Integer.parseInt(line.split(" ")[0]);
                    int capacity = Integer.parseInt(line.split(" ")[1]);
                    knapsack = new Knapsack(capacity, numItems);
                    while ((line = br.readLine()) != null) {
                        String[] lineSplit = line.split(" ");
                        Item item = new Item(Double.parseDouble(lineSplit[0]), Double.parseDouble(lineSplit[1]));
                        knapsack.addItem(item);
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (knapsack != null) {
                    knapsacks.put(file.getName(), knapsack);
                }
            }
        }

        return knapsacks;
    }

    /**
     * @brief print a solution
     * 
     * @param solution
     */
    public void printSolution(Boolean[] solution) {
        System.out.print("[");
        for (int i = 0; i < solution.length; i++) {
            if (solution[i] == null) {
                System.out.print("_");
            } else if (solution[i]) {
                System.out.print(" 1 ");
            } else {
                System.out.print(" 0 ");
            }
            if (i != solution.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

}
