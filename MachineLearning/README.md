# Machine Learning

## Introduction

### The Main Class

The `Main` class acts as the testing environment to evaluate the performance of the Artificial Neural Network (ANN) and Genetic Programming (GP) algorithms on mushroom classification data. This README provides an overview of the testing process, including data preprocessing, algorithm functionality, and key methods.

### Data Preprocessing

Before testing the algorithms, the mushroom data undergoes preprocessing in several steps:

1. **Reading Data**: The mushroom data is read from CSV files.
2. **Normalization**: Numeric features are normalized to ensure consistency across the dataset.
3. **One-Hot Encoding**: Categorical features are one-hot encoded for compatibility with machine learning algorithms.

This ensures that the data is appropriately formatted and ready for testing with the ANN and GP algorithms.

## Algorithms (Technical Specification)

### ANN Algorithm

- The Artificial Neural Network (ANN) is a supervised learning algorithm that mimics the structure and function of the human brain.
- #### Overview:
    1. **Initialization**: The neural network is initialized with random weights and biases.
    2. **Forward Propagation**: Input data is propagated forward through the network, layer by layer, to generate predictions.
    3. **Backpropagation**: Errors are calculated between predicted and actual values, and the gradients of the error function with respect to the weights and biases are computed.
    4. **Weight Update**: The weights and biases are adjusted using gradient descent to minimize the error.

- #### Key Methods:

    - `generateHotDataMatrix`: Preprocesses the data by reading from CSV files, normalizing numeric features, and one-hot encoding categorical features.
    - `testANN`: Trains and evaluates the ANN algorithm on the preprocessed data.
    - `ANN.train`: Trains the ANN model on the training data using backpropagation.
    - `ANN.test`: Evaluates the ANN model on the test data.

### GP Programming

- Genetic Programming (GP) is an evolutionary algorithm used for symbolic regression and classification tasks.

- #### Overview:

    1. **Initialization**: A population of randomly generated individuals (trees) is created.
    2. **Selection**: Individuals are selected for reproduction based on their fitness.
    3. **Crossover**: Selected individuals undergo crossover to create offspring.
    4. **Mutation**: Offspring may undergo mutation, introducing random changes in the tree structure.
    5. **Evaluation**: The fitness of each individual is evaluated based on its ability to solve the problem.
    6. **Termination**: The process repeats for a fixed number of generations or until a termination criterion is met.

- #### Key Methods:

    - `generateRandomTree`: Generates a random tree structure for initializing individuals in the population.
    - `tournamentSelection`: Selects individuals for reproduction based on tournament selection.
    - `crossover`: Performs crossover between two individuals to create offspring.
    - `mutate`: Mutates an individual by introducing random changes in its tree structure.
    - `run`: Executes the GP algorithm on the preprocessed data.

## Presentation of Results

| Algorithm | Runtime (ms) [Type] | Accuracy | Specificity | Sensitivity | F-measure |
|-----------|----------------------|----------|-------------|-------------|------------|
| ANN       | 18 (Training)        | 57.73%   | 3.12%       | 87.44%      | 72.83%     |
|           | 5 (Testing)          | 58.87%   | 0.0         | 100.0%      | 74.11%     |
| GP        | 2332 (Training)      | 49.70%   | 46.88%      | 51.23%      | 56.89%     |
|           | 625 (Testing)        | 75.79%   | 51.21%      | 92.96%      | 81.89%     |

## Critical Comparative Analysis

Based on the performance results:

- **ANN (Artificial Neural Network)**:
  - Achieved an accuracy of 57.73% during training and 58.87% during testing.
  - Sensitivity during testing was notably high at 100.0%, indicating its effectiveness in identifying positive cases (edible mushrooms) correctly.
  - However, the specificity during both training and testing was low, suggesting a high rate of false positives.

- **GP (Genetic Programming)**:
  - Demonstrated an accuracy of 49.70% during training and a significant improvement to 75.79% during testing.
  - Notably, the sensitivity during testing was substantially higher compared to training, indicating an improvement in correctly identifying positive cases.
  - Additionally, the F-measure during testing was considerably higher compared to ANN, suggesting a better balance between precision and recall.

**Conclusion**:
- While the ANN showed consistent performance across training and testing, its low specificity indicates a significant number of false positives.
- The GP algorithm demonstrated a remarkable improvement in accuracy during testing compared to training, with notably higher sensitivity and F-measure, indicating its potential for better generalization and performance on unseen data.
- Overall, the GP algorithm outperformed the ANN in terms of testing accuracy, sensitivity, and F-measure, suggesting it as a more effective model for mushroom classification tasks based on the provided dataset.

## Running the Code

#### Requirements before running codes:

- Install an `IDE` that `compiles` and `runs` Java codes. Recommendation `VS Code`
- How to setup `WSL` Ubuntu terminal shell and run it from `Visual Studio Code:`
  visit: https://www.youtube.com/watch?v=fp45HpZuhS8&t=112s
- How to Install `Java JDK 17` on `Windows 11:` https://www.youtube.com/watch?v=ykAhL1IoQUM&t=136s

####  To run the code:

- Compile all `.java` files in your preferred IDE or using command-line tools.
- Execute the `Main.java` class.
- View the output in the console.
- Makefile I used:
```bash
default:
   javac *.java

run:
   java Main

clean:
   rm -f *.class
   reset
   clear

tar:
   tar -cvz *.java -f MachineLearning.tar.gz

untar:
   tar -zxvf *.tar.gz
```

## Documentation

- I used Doxygen Comments for Documentation,
- To generate documentation run command: ```doxygen Doxyfile ```

---

<p align="center"> The End, Thank You! </P>

---
