import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        String filePathTrain = "./mushroom_data/mushroom_train.csv";
        String filePathTest = "./mushroom_data/mushroom_test.csv";
        
        double[][] hotDataMatrixTrain = generateHotDataMatrix(filePathTrain);
        double[][] hotDataMatrixTest = generateHotDataMatrix(filePathTest);
    
        // testANN(hotDataMatrixTrain, hotDataMatrixTest);
        testGP(hotDataMatrixTrain, hotDataMatrixTest);
    }

    public static void testGP(double[][] hotDataMatrixTrain, double[][] hotDataMatrixTest) {
        double[] labels = {
            1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0
        };
    
        GP.run(hotDataMatrixTrain, labels);
        GP.run(hotDataMatrixTest, labels);
    }

    public static void testANN(double[][] hotDataMatrixTrain, double[][] hotDataMatrixTest) {
        // Initialize a Random object
        Random random = new Random();
        // Initialize ANN with input size
        ANN ann = new ANN(hotDataMatrixTrain[0].length, random);
        // Train ANN with your data
        for (double[] data : hotDataMatrixTrain) {
            // Assuming the target value is the last element
            double target = data[data.length - 1];
            // Remove target value
            double[] input = Arrays.copyOf(data, data.length - 1);
            ann.train(input, target);
        }

        for (double[] data : hotDataMatrixTest) {
            // Assuming the target value is the last element
            double target = data[data.length - 1];
            // Remove target value
            double[] input = Arrays.copyOf(data, data.length - 1);
            ann.test(input, target);
        }

        // Get evaluation metrics
        double accuracy = ann.getAccuracy();
        double specificity = ann.getSpecificity();
        double sensitivity = ann.getSensitivity();
        double fMeasure = ann.getFMeasure();

        // Print or use the evaluation metrics as needed
        System.out.println("Accuracy: " + accuracy);
        System.out.println("Specificity: " + specificity);
        System.out.println("Sensitivity: " + sensitivity);
        System.out.println("F-measure: " + fMeasure); 
    }

    public static double[][] generateHotDataMatrix(String filePath) {
        try {
            List<MushroomData> dataList = readData(filePath);
            // Example usage: Print the first few rows of data
            // for (int i = 0; i < 5 && i < dataList.size(); i++) {
            //     System.out.println(dataList.get(i).toString());
            // }

            List<MushroomData> normalizedDataList = normalize(dataList);
            double[][] hotDataMatrix = generateHotDataMatrix(normalizedDataList);

            // Print hot data matrix
            for (double[] row : hotDataMatrix) {
                for (double value : row) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }

            return hotDataMatrix;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<MushroomData> readData(String filePath) throws FileNotFoundException {
        List<MushroomData> data = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // Skip the header line
        }
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(",");
            data.add(new MushroomData(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7], line[8]));
        }
        scanner.close();
        return data;
    }

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

        int numCols = capShapes.size() + gillAttachments.size() + gillColors.size() + stemColors.size() + seasons.size() + 4; // +4 for the numeric columns and class
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

    private static double normalizeValue(double value, double min, double max) {
        return (value - min) / (max - min);
    }
}
