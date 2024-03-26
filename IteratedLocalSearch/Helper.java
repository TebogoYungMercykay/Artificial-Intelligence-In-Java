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
}
