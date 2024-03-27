import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

// Class Containing Helper Functions - Doxygen Comments for Documentation

public class Helper {

    /**
    * Reads in a file and returns an ArrayList of Integers
    * 
    * @param dirName
    * @return fileContents
    */

    public static HashMap<String, ArrayList<Integer>> readFiles(String dirName) {

        File dir = new File("1BPP_Instances/" + dirName);
        File[] files = dir.listFiles();

        HashMap<String, ArrayList<Integer>> fileContents = new HashMap<>();
        ArrayList<Integer> currentFile = null;

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                fileContents.put(fileName, new ArrayList<Integer>());
                currentFile = fileContents.get(fileName);
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine()) != null) {
                        currentFile.add(Integer.parseInt(line));
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return fileContents;
    }

    /**
    * Reads the file containing optimal solutions and returns a HashMap of the
    * filename and the optimal number of bins
    * 
    * @param filepath
    * @return optimal
    */

    public static HashMap<String, Integer> readOptima(String filepath) {

        HashMap<String, Integer> optimal = new HashMap<String, Integer>();

        File file = new File(filepath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                optimal.put(split[0], Integer.parseInt(split[1]));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return optimal;

    }

    /**
    * Prints the contents of a values ArrayList
    * 
    * @param values
    */

    public static void printValues(ArrayList<Integer> values) {
        System.out.println("Bin [");
        for (int i = 0; i < values.size(); i++) {
            System.out.print(values.get(i));
            if (i != values.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }

    /**
    * Calculates the Sum of teh Bin Values
    * 
    * @param bin
    */

    public static Integer sumBin(ArrayList<Integer> bin) {
        Integer sum = 0;
        for (int i = 0; i < bin.size(); i++) {
            sum += bin.get(i);
        }

        return sum;
    }

    /**
    * Prints the contents of an instance
    * 
    * @param instance
    * @return
    */

    public static Integer printInstance(ArrayList<ArrayList<Integer>> instance) {
        Integer sum = 0;
        for (int i = 0; i < instance.size(); i++) {
            System.out.print("Bin " + i + " [");
            for (int j = 0; j < instance.get(i).size(); j++) {
                System.out.print(instance.get(i).get(j));
                if (j != instance.get(i).size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
            sum += sumBin(instance.get(i));
        }

        return sum;
    }

    /**
    * Creates a deep copy of an instance
    * 
    * @param instance
    * @return
    */

    public ArrayList<ArrayList<Integer>> copyInstance(ArrayList<ArrayList<Integer>> instance) {
        ArrayList<ArrayList<Integer>> copy = new ArrayList<>();
        for (int i = 0; i < instance.size(); i++) {
            ArrayList<Integer> bin = new ArrayList<>();
            for (int j = 0; j < instance.get(i).size(); j++) {
                bin.add(instance.get(i).get(j));
            }
            copy.add(bin);
        }

        return copy;
    }

    /**
    * Organises the fileContents into a HashMap of file names and their respective
    * bins
    * 
    * @param fileContents
    * @param cap
    * @return
    */

    public static HashMap<String, ArrayList<ArrayList<Integer>>> organiseInstances(HashMap<String, ArrayList<Integer>> fileContents) {

        HashMap<String, ArrayList<ArrayList<Integer>>> instances = new HashMap<>();

        for (String fileName : fileContents.keySet()) {

            ArrayList<Integer> values = fileContents.get(fileName);
            Integer cap = values.remove(0);

            instances.put(fileName, bestFitInitial(values, cap));
        }

        return instances;
    }

    /**
    * Removes the iterations from the fileContents and returns a HashMap of file
    * 
    * @param fileContents
    * @return
    */

    public static HashMap<String, Integer> getIterations(HashMap<String, ArrayList<Integer>> fileContents) {
        HashMap<String, Integer> iterations = new HashMap<>();
        for (String fileName : fileContents.keySet()) {
            iterations.put(fileName, fileContents.get(fileName).remove(0));
        }
    
        return iterations;
    }

    /**
    * Removes the cap from the fileContents and returns a HashMap of file names and
    * capacities
    */

    public static HashMap<String, Integer> getCaps(HashMap<String, ArrayList<Integer>> fileContents) {
        HashMap<String, Integer> caps = new HashMap<>();
        for (String fileName : fileContents.keySet()) {
            caps.put(fileName, fileContents.get(fileName).remove(0));
        }

        return caps;
    }

    /**
    * Determines the fitness of a given instance
    * 
    * @param instance
    * @return
    */

    public Double Fitness(ArrayList<ArrayList<Integer>> testedInstance) {
        Double fitness = 0.0;
        for (int i = 0; i < testedInstance.size(); i++) {
            fitness += Math.pow(sumBin(testedInstance.get(i)), 2);
        }

        return (1 - fitness / testedInstance.size());
    }

    //************ Sorting Methods ************//

    /**
    * Sorts the values ArrayList into capped bins using First Fit Decreasing
    * 
    * @param values
    * @param cap
    * @return ArrayList<ArrayList<Integer>> bins
    */

    public static ArrayList<ArrayList<Integer>> firstFitDecreasing(ArrayList<Integer> values, Integer cap) {

        ArrayList<ArrayList<Integer>> bins = new ArrayList<>();
        // Add values to the current bin. If it is full, create a new bin and continue
        // until all values have been added
        
        for (int i = 0; i < values.size(); i++) {
            Integer value = values.get(i);

            // If the current bin is full, create a new bin
            if (bins.size() == 0 || sumBin(bins.get(bins.size() - 1)) + value > cap) {
                ArrayList<Integer> bin = new ArrayList<>();
                bin.add(value);
                bins.add(bin);
            } else {
                // Add the value to the current bin
                bins.get(bins.size() - 1).add(value);
            }
        }

        return bins;
    }

    /**
    * Sorts the values ArrayList into capped bins using Best Fit
    * 
    * @param values
    * @param cap
    * @return ArrayList<ArrayList<Integer>> bins
    */

    public static ArrayList<ArrayList<Integer>> bestFitInitial(ArrayList<Integer> values, Integer cap) {

        ArrayList<ArrayList<Integer>> bins = new ArrayList<>();
        // Search through the bins to find the best fit for the current value, if no bin
        // exists create a new one

        for (int i = 0; i < values.size(); i++) {
            Integer value = values.get(i);
            Integer bestFit = 0;
            Integer bestFitIndex = 0;

            // Search through the bins to find the best fit for the current value
            for (int j = 0; j < bins.size(); j++) {
                Integer binSum = sumBin(bins.get(j));
                if (binSum + value <= cap && binSum > bestFit) {
                    bestFit = binSum;
                    bestFitIndex = j;
                }
            }

            // If no bin exists, create a new one
            if (bestFit == 0) {
                ArrayList<Integer> bin = new ArrayList<>();
                bin.add(value);
                bins.add(bin);
            } else {
                // Add the value to the best fit bin
                bins.get(bestFitIndex).add(value);
            }
        }

        return bins;
    }
}
