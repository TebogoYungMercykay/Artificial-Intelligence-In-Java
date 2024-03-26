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
    * Prints the contents of a values ArrayList
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
}
