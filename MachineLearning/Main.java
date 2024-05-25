import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * @file Main.java
 * 
 * @author Selepe Sello
 * @date 25 may 2024
 * @version 1.0
 * @brief The Main class serves as the entry point for running and testing the Artificial Neural Network (ANN) and Genetic Programming (GP) algorithms on mushroom classification data.
 */

public class Main {
    /**
     * The main method, entry point of the application.
     *
     * @param args Command line arguments (not used)
     */

    public static void main(String[] args) {
        String filePathTrain = "./mushroom_data/mushroom_train.csv";
        String filePathTest = "./mushroom_data/mushroom_test.csv";
        
        double[][] hotDataMatrixTrain = generateHotDataMatrix(filePathTrain);
        double[][] hotDataMatrixTest = generateHotDataMatrix(filePathTest);

        long seedANN = 32892796;
        long seedGP = 32892799;
          
        try {
            // Print hot data matrix
            System.out.println("----------------------------------------------------");
            System.out.println(" HOT DATA MATRIX - NORMALISED AND ONE-HOT ENCODED");
            System.out.println("----------------------------------------------------");

            int setLimit = 9;
            int maxLimit = Math.min(hotDataMatrixTrain != null ? hotDataMatrixTrain.length : Integer.MAX_VALUE, setLimit);

            int counter = 0;

            System.out.println("\nPrinting the first " + maxLimit + " rows in matrix.");
            for (double[] row : hotDataMatrixTrain) {
                if (counter < maxLimit) {
                    for (double value : row) {
                        System.out.print(value + " ");
                    }
                    System.out.println();
                }
                counter++;
            }

            System.out.println("\n----------------------------------------------------");
            System.out.println("       MACHINE LEARNING MODELS       ");
            System.out.println("            ---");
            
            // ANN section
            System.out.println("----------------------------------------------------");
            System.out.println("     _      _   ___   _   ___");
            System.out.println("    / \\    | | /   | | | /   |");
            System.out.println("   / _ \\   | |/ /| | | |/ /| |");
            System.out.println("  / ___ \\  |   / | | |   / | |");
            System.out.println(" /_/   \\_\\ |__/  |_| |__/  |_|");
            System.out.println("\n Artificial  Neural   Network");
            System.out.println("----------------------------------------------------");
            testANN(hotDataMatrixTrain, hotDataMatrixTest, seedANN);
            System.out.println("            ---");
            System.out.println();
            
            // GP section
            System.out.println("----------------------------------------------------");
            System.out.println("   ____    ____");
            System.out.println("  / ___|  |  _ \\");
            System.out.println(" | |  _   | |_) |");
            System.out.println(" | |_| |  |  __/");
            System.out.println("  \\____|  |_|");
            System.out.println("\n Genetic Programming");
            System.out.println("----------------------------------------------------");
            testGP(hotDataMatrixTrain, hotDataMatrixTest, seedGP);
            System.out.println("            ---");
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error Occurred: " + e.getMessage());
        }
    }

    /**
     * Testing ANN Algorithm with the hot encoded data matrix from a CSV file.
     *
     * @param hotDataMatrixTrain The training hot encoded data matrix
     * @param hotDataMatrixTest The testing hot encoded data matrix
     * @param seed Seed value used for the random number generator
     */
    public static void testANN(double[][] hotDataMatrixTrain, double[][] hotDataMatrixTest, long seed) {
        // Initialize ANN with input size
        ANN ann = new ANN(seed, hotDataMatrixTrain[0].length);

        // Train ANN with your data
        System.out.println("\n------ TRAINING MODEL -------");

        ann.train(hotDataMatrixTrain);

        // Get evaluation metrics
        double accuracyTrain = ann.getAccuracy();
        double specificityTrain = ann.getSpecificity();
        double sensitivityTrain = ann.getSensitivity();
        double fMeasureTrain = ann.getFMeasure();

        // Print the evaluation metrics
        System.out.println("Accuracy: " + accuracyTrain);
        System.out.println("Specificity: " + specificityTrain);
        System.out.println("Sensitivity: " + sensitivityTrain);
        System.out.println("F-measure: " + fMeasureTrain); 

        System.out.println("\n------ TESTING MODEL -------");
        ann.test(hotDataMatrixTest);

        // Get evaluation metrics
        double accuracyTest = ann.getAccuracy();
        double specificityTest = ann.getSpecificity();
        double sensitivityTest = ann.getSensitivity();
        double fMeasureTest = ann.getFMeasure();

        // Print the evaluation metrics
        System.out.println("Accuracy: " + accuracyTest);
        System.out.println("Specificity: " + specificityTest);
        System.out.println("Sensitivity: " + sensitivityTest);
        System.out.println("F-measure: " + fMeasureTest); 
    }

    /**
     * Testing GP Algorithm with the hot encoded data matrix from a CSV file.
     *
     * @param hotDataMatrixTrain The training hot encoded data matrix
     * @param hotDataMatrixTest The testing hot encoded data matrix
     * @param seed Seed value used for the random number generator
     */
    public static void testGP(double[][] hotDataMatrixTrain, double[][] hotDataMatrixTest, long seed) {
        // Extracting labels for the training dataset
        double[] labelsTraining = new double[hotDataMatrixTrain.length];
        for (int i = 0; i < hotDataMatrixTrain.length; i++) {
            labelsTraining[i] = hotDataMatrixTrain[i][hotDataMatrixTrain[i].length - 1];
        }

        // Extracting labels for the test dataset
        double[] labelsTesting = new double[hotDataMatrixTest.length];
        for (int i = 0; i < hotDataMatrixTest.length; i++) {
            labelsTesting[i] = hotDataMatrixTest[i][hotDataMatrixTest[i].length - 1];
        }

        // Create the GP instance with a random seed and the number of features
        GP gp = new GP(seed, hotDataMatrixTrain[0].length);

        // Run the GP algorithm on the training hotDataMatrixTrain
        System.out.println("\n------ TRAINING MODEL -------");
        gp.run(hotDataMatrixTrain, labelsTraining);
        Individual best = gp.getBestIndividual();
        // Evaluate the model on the test dataset
        gp.evaluateModel(best, hotDataMatrixTrain, labelsTraining);

        double accuracyTrain = gp.getAccuracy();
        double specificityTrain = gp.getSpecificity();
        double sensitivityTrain = gp.getSensitivity();
        double fMeasureTrain = gp.getFMeasure();

        // Print the evaluation metrics
        System.out.println("Accuracy: " + accuracyTrain);
        System.out.println("Specificity: " + specificityTrain);
        System.out.println("Sensitivity: " + sensitivityTrain);
        System.out.println("F-measure: " + fMeasureTrain); 

        System.out.println("\n------ TESTING MODEL -------");
        // Find the best individual in the final population
        gp.run(hotDataMatrixTest, labelsTesting);
        best = gp.getBestIndividual();
        // Evaluate the model on the test dataset
        gp.evaluateModel(best, hotDataMatrixTest, labelsTesting);

        double accuracyTest = gp.getAccuracy();
        double specificityTest = gp.getSpecificity();
        double sensitivityTest = gp.getSensitivity();
        double fMeasureTest = gp.getFMeasure();

        // Print the evaluation metrics
        System.out.println("Accuracy: " + accuracyTest);
        System.out.println("Specificity: " + specificityTest);
        System.out.println("Sensitivity: " + sensitivityTest);
        System.out.println("F-measure: " + fMeasureTest); 
    }

    /**
     * Generates a hot encoded data matrix from a CSV file.
     *
     * @param filePath The file path of the CSV file
     * @return A hot encoded data matrix
     */
    public static double[][] generateHotDataMatrix(String filePath) {
        try {
            List<MushroomData> dataList = readData(filePath);

            List<MushroomData> normalizedDataList = normalize(dataList);
            double[][] hotDataMatrix = generateHotDataMatrix(normalizedDataList);

            return hotDataMatrix;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads mushroom data from a CSV file.
     *
     * @param filePath The file path of the CSV file
     * @return A list of MushroomData objects
     * @throws FileNotFoundException if the specified file is not found
     */
    public static List<MushroomData> readData(String filePath) throws FileNotFoundException {
        List<MushroomData> data = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(",");
            data.add(new MushroomData(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7], line[8]));
        }
        scanner.close();
        return data;
    }

    /**
     * Normalizes the mushroom data.
     *
     * @param dataList The list of MushroomData objects to be normalized
     * @return A list of normalized MushroomData objects
     */
    public static List<MushroomData> normalize(List<MushroomData> dataList) {
        double minCapDiameter = Double.MAX_VALUE, maxCapDiameter = Double.MIN_VALUE;
        double minStemHeight = Double.MAX_VALUE, maxStemHeight = Double.MIN_VALUE;
        double minStemWidth = Double.MAX_VALUE, maxStemWidth = Double.MIN_VALUE;
        double minSeason = Double.MAX_VALUE, maxSeason = Double.MIN_VALUE;

        // Find min and max values
        for (MushroomData data : dataList) {
            double capDiameter = Double.parseDouble(data.getCapDiameter());
            double stemHeight = Double.parseDouble(data.getStemHeight());
            double stemWidth = Double.parseDouble(data.getStemWidth());
            double season = Double.parseDouble(data.getSeason());

            if (capDiameter < minCapDiameter) minCapDiameter = capDiameter;
            if (capDiameter > maxCapDiameter) maxCapDiameter = capDiameter;

            if (stemHeight < minStemHeight) minStemHeight = stemHeight;
            if (stemHeight > maxStemHeight) maxStemHeight = stemHeight;

            if (stemWidth < minStemWidth) minStemWidth = stemWidth;
            if (stemWidth > maxStemWidth) maxStemWidth = stemWidth;

            if (season < minSeason) minSeason = season;
            if (season > maxSeason) maxSeason = season;
        }

        List<MushroomData> normalizedDataList = new ArrayList<>();

        // Normalize values
        for (MushroomData data : dataList) {
            double capDiameter = normalizeValue(Double.parseDouble(data.getCapDiameter()), minCapDiameter, maxCapDiameter);
            double stemHeight = normalizeValue(Double.parseDouble(data.getStemHeight()), minStemHeight, maxStemHeight);
            double stemWidth = normalizeValue(Double.parseDouble(data.getStemWidth()), minStemWidth, maxStemWidth);
            double season = normalizeValue(Double.parseDouble(data.getSeason()), minSeason, maxSeason);

            normalizedDataList.add(new MushroomData(
                String.valueOf(capDiameter),
                data.getCapShape(),
                data.getGillAttachment(),
                data.getGillColor(),
                String.valueOf(stemHeight),
                String.valueOf(stemWidth),
                data.getStemColor(),
                String.valueOf(season),
                data.getClazz()
            ));
        }

        return normalizedDataList;
    }

    /**
     * Generates a hot encoded data matrix from a list of normalized MushroomData objects.
     *
     * @param normalizedDataList The list of normalized MushroomData objects
     * @return A hot encoded data matrix
     */
    public static double[][] generateHotDataMatrix(List<MushroomData> normalizedDataList) {
        // Identify unique values for each categorical column
        Set<String> capShapes = new HashSet<>();
        Set<String> gillAttachments = new HashSet<>();
        Set<String> gillColors = new HashSet<>();
        Set<String> stemColors = new HashSet<>();
        Set<String> seasons = new HashSet<>();

        for (MushroomData data : normalizedDataList) {
            capShapes.add(data.getCapShape());
            gillAttachments.add(data.getGillAttachment());
            gillColors.add(data.getGillColor());
            stemColors.add(data.getStemColor());
            seasons.add(data.getSeason());
        }

        // +4 for the numeric columns and class
        int numCols = capShapes.size() + gillAttachments.size() + gillColors.size() + stemColors.size() + seasons.size() + 4;
        int numRows = normalizedDataList.size();
        double[][] hotDataMatrix = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            MushroomData data = normalizedDataList.get(i);
            int colIndex = 0;

            // One-hot encode capShape
            for (String capShape : capShapes) {
                hotDataMatrix[i][colIndex++] = capShape.equals(data.getCapShape()) ? 1.0 : 0.0;
            }

            // One-hot encode gillAttachment
            for (String gillAttachment : gillAttachments) {
                hotDataMatrix[i][colIndex++] = gillAttachment.equals(data.getGillAttachment()) ? 1.0 : 0.0;
            }

            // One-hot encode gillColor
            for (String gillColor : gillColors) {
                hotDataMatrix[i][colIndex++] = gillColor.equals(data.getGillColor()) ? 1.0 : 0.0;
            }

            // One-hot encode stemColor
            for (String stemColor : stemColors) {
                hotDataMatrix[i][colIndex++] = stemColor.equals(data.getStemColor()) ? 1.0 : 0.0;
            }

            // One-hot encode season
            for (String season : seasons) {
                hotDataMatrix[i][colIndex++] = season.equals(data.getSeason()) ? 1.0 : 0.0;
            }

            // Add normalized numerical columns
            hotDataMatrix[i][colIndex++] = Double.parseDouble(data.getCapDiameter());
            hotDataMatrix[i][colIndex++] = Double.parseDouble(data.getStemHeight());
            hotDataMatrix[i][colIndex++] = Double.parseDouble(data.getStemWidth());

            // Add class label
            hotDataMatrix[i][colIndex] = Double.parseDouble(data.getClazz());
        }

        return hotDataMatrix;
    }

    /**
     * Normalizes a numerical value.
     *
     * @param value The value to be normalized
     * @param min   The minimum value in the range
     * @param max   The maximum value in the range
     * @return The normalized value
     */
    private static double normalizeValue(double value, double min, double max) {
        return (value - min) / (max - min);
    }
}
