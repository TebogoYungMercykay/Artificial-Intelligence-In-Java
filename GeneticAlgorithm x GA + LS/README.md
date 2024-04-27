# Artificial Intelligence (COS314) - Assignment 2 Documentation

- **Names**: Selepe Sello
- **Email**: uXXXXXXXX@tuks.co.za
- **Date**: 27 April 2024
- **Challenge**: Knapsack Problem

## Introduction: Knapsack Problem

The Knapsack Problem is a well-known problem in computer science and combinatorial optimization. The problem involves choosing a set of items, each with a specific weight and value, to maximize the total value while ensuring the total weight does not exceed a given limit (the capacity of the knapsack).

The problem can be mathematically defined as follows:

Given:

- A set of items, denoted as I = {1, 2, ..., n}
- Each item, denoted as i, has a weight, denoted as w_i, and a value, denoted as v_i
- A knapsack with a maximum weight capacity, denoted as W

The goal is to:

- Find a subset, denoted as S, of items that maximizes the total value, denoted as sum(v_i for i in S)
- Ensure the total weight, denoted as sum(w_i for i in S), does not exceed the knapsack's capacity, W

## Configuration Descriptions

The initial configuration values for both the Genetic Algorithm (GA) and the Genetic Algorithm + Ant Colony Optimization (GA + ACO) were obtained from relevant literature and used as starting points for parameter tuning. The final values used were determined through a process of experimentation and tuning to optimize the performance of the algorithms on the Knapsack problem.

The results of running the Genetic Algorithm (GA) and the Genetic Algorithm + Ant Colony Optimization (GA + ACO) on the Knapsack problem instances can be found in a text file named `output.txt`. Alternatively, you can obtain the results by running the algorithms yourself. To do this, execute the main method in the provided Java code. This will run the GA and the GA + ACO on each problem instance and write the results to `output.txt`.

### 1. Genetic Algorithm (GA)

- #### Overview:

  The Genetic Algorithm (GA) configuration is a method for solving optimization problems, specifically tailored here for the Knapsack problem. It utilizes evolutionary principles inspired by natural selection to evolve a population of potential solutions over generations.
- #### Parameters:

  - **Population Multiplier**: Determines the size of the population by multiplying the number of items in the knapsack.
  - **Crossover Rate**: Probability of crossover between two parent chromosomes during reproduction.
  - **Mutation Rate**: Probability of a bit flip mutation occurring on a chromosome.
  - **Max Generations**: Maximum number of generations the GA will run for.
  - **Stopping Iterations**: Number of generations with no improvement in average fitness before stopping.
  - **Penalty Factor**: Factor by which exceeding the knapsack's capacity is penalized in the fitness function.
  - **Tournament Portion**: Proportion of the population competing in each tournament during selection.
  - **Initial Bit Probability**: Initial probability that a bit in a chromosome will be set to true.
- #### Usage:

  The GA aims to find the optimal combination of items to maximize the total value within the knapsack's capacity, iteratively improving solutions through selection, crossover, and mutation.

### 2. Genetic Algorithm + Local Search (GA + ACO)

- #### Overview:

  The Genetic Algorithm + Ant Colony Optimization (GA + ACO) configuration enhances the traditional GA with an additional local search method, ACO. This hybrid approach aims to further refine solutions found by the GA, leveraging ACO's ability to exploit promising areas of the solution space.
- #### Additional Parameters (ACO):

  - **Max Iterations**: Maximum number of iterations for the ACO algorithm.
  - **Stopping Iterations**: Number of iterations with no improvement in average fitness before stopping.
  - **Alpha**: Importance of pheromone in decision-making.
  - **Beta**: Importance of heuristic information in decision-making.
  - **Evaporation Rate**: Rate at which pheromone evaporates from paths.
  - **Initial Pheromone**: Initial amount of pheromone on paths.
  - **Update Strength**: Strength of pheromone update.
  - **Local Search Method**: Specifies the method used in local search (e.g., replacing worst item or assessing single item flips).
- #### Usage:

  The GA generates an initial population of solutions, which are then refined using ACO as a local search method. ACO employs artificial ants to explore the solution space guided by pheromone trails and heuristic information, leading to improved solutions by exploiting promising regions of the space.

## 3. Local Search Description and Justification:

### Ant Colony Optimization (ACO)

- #### Overview:

  ACO is a metaheuristic inspired by the foraging behavior of ants. It utilizes artificial ants to explore solution spaces, guided by pheromone trails and heuristic information. ACO maintains diversity and incorporates memory of past solutions to efficiently navigate complex problem spaces.
- #### Justification:


  - **Diversity of Solutions**: ACO maintains a diverse population, enhancing exploration of the solution space.
  - **Memory of Past Solutions**: Pheromone trails act as a memory, guiding the search toward promising regions.
  - **Robustness**: ACO is robust across problem domains, capable of finding quality solutions for various problems.
  - **Complementarity with GA**: ACO complements GA by refining solutions found by GA, effectively balancing exploration and exploitation.

  By integrating ACO as a local search method, the GA + ACO hybrid algorithm effectively explores and exploits the solution space, resulting in high-quality solutions for the Knapsack problem.

## 4. Experimental Setup

- #### Overview:

  The experimental setup involves configuring and running both the Genetic Algorithm (GA) and the Genetic Algorithm + Ant Colony Optimization (GA + ACO) hybrid algorithm to solve instances of the Knapsack problem. The goal is to compare the performance of the two approaches in terms of solution quality and convergence speed.
- #### Parameters:

  | Parameter                 | Genetic Algorithm (GA) | GA + ACO (LS) |
  | ------------------------- | ---------------------- | ------------- |
  | Population Multiplier     | 8                      | 8             |
  | Crossover Rate            | 0.3                    | 0.3           |
  | Mutation Rate             | 0.4                    | 0.4           |
  | Max Generations           | 500                    | 500           |
  | Stopping Iterations       | 250                    | 100           |
  | Penalty Factor            | 10                     | N/A           |
  | Tournament Portion        | 0.2                    | N/A           |
  | Initial Bit Probability   | 0.1                    | N/A           |
  | Max Iterations (ACO)      | N/A                    | 200           |
  | Alpha (ACO)               | N/A                    | 0.5           |
  | Beta (ACO)                | N/A                    | 2             |
  | Evaporation Rate (ACO)    | N/A                    | 0.95          |
  | Initial Pheromone (ACO)   | N/A                    | 0.5           |
  | Update Strength (ACO)     | N/A                    | 0.5           |
  | Local Search Method (ACO) | N/A                    | 0 (None)      |
- #### Experimental Procedure:

  - **Used Provided Problem Instances**: Utilized the provided Knapsack problem instances with varying parameters.
  - **Run GA**: Execute the Genetic Algorithm (GA) on each instance, recording best solutions and convergence stats.
  - **Run GA + ACO**: Execute the hybrid algorithm (GA + ACO) on each instance, recording best solutions and convergence stats.
  - **Compare Performance**: Assess solution quality and convergence speed of both algorithms.
  - **Analyze Statistically**: Perform statistical tests like z-tests at a 5% significance level to evaluate performance differences.
  - **Discuss & Conclude**: Interpret results, perform a critical analysis, discuss the advantages and disadvantages of each approach, and suggest directions for future research.

## 5. Comparison of GA and GA + LS on 10 Knapsack Problem Instances

| Problem Instance    | Algorithm | Seed Value | Best Solution | Known Optimum | Runtime (seconds) | Majority Optimal |
| ------------------- | --------- | ---------- | ------------- | ------------- | ----------------- | ---------------- |
| f9_l-d_kp_5_80      | GA        | 24         | 130.00        | 130.00        | 0.00              | Yes              |
|                     | GA - ILS  | 24         | 130.00        | 130.00        | 0.01              | Yes              |
| f1_l-d_kp_10_269    | GA        | 24         | 295.00        | 295.00        | 0.00              | Yes              |
|                     | GA - ILS  | 24         | 295.00        | 295.00        | 0.01              | Yes              |
| f3_l-d_kp_4_20      | GA        | 24         | 35.00         | 35.00         | 0.00              | Yes              |
|                     | GA - ILS  | 24         | 35.00         | 35.00         | 0.00              | Yes              |
| knapPI_1_100_1000_1 | GA        | 24         | 9147.00       | 9147.00       | 7.00              | Yes              |
|                     | GA - ILS  | 24         | 9147.00       | 9147.00       | 0.22              | Yes              |
| f6_l-d_kp_10_60     | GA        | 24         | 52.00         | 52.00         | 0.00              | Yes              |
|                     | GA - ILS  | 24         | 52.00         | 52.00         | 0.00              | Yes              |
| f5_l-d_kp_15_375    | GA        | 24         | 481.07        | 481.07        | 0.00              | Yes              |
|                     | GA - ILS  | 24         | 481.07        | 481.07        | 0.01              | Yes              |
| f8_l-d_kp_23_10000  | GA        | 24         | 9767.00       | 9767.00       | 0.00              | Yes              |
|                     | GA - ILS  | 24         | 9750.00       | 9767.00       | 0.01              | No               |
| f10_l-d_kp_20_879   | GA        | 24         | 1025.00       | 1025.00       | 0.00              | Yes              |
|                     | GA - ILS  | 24         | 1025.00       | 1025.00       | 0.01              | Yes              |
| f4_l-d_kp_4_11      | GA        | 24         | 23.00         | 23.00         | 0.00              | Yes              |
|                     | GA - ILS  | 24         | 23.00         | 23.00         | 0.00              | Yes              |
| f7_l-d_kp_7_50      | GA        | 24         | 105.00        | 107.00        | 0.00              | No               |
|                     | GA - ILS  | 24         | 107.00        | 107.00        | 0.00              | Yes              |
| f2_l-d_kp_20_878    | GA        | 24         | 1024.00       | 1024.00       | 0.00              | Yes              |
|                     | GA - ILS  | 24         | 1024.00       | 1024.00       | 0.01              | Yes              |

## 6. Statistical Analysis (One-Tailed Z-Test (5% level)):

- #### Hypotheses:


  - **Null Hypothesis (H0)**: Mean best solution found by GA is greater than or equal to Mean best solution found by GA + ACO.
  - **Alternative Hypothesis (H1)**: Mean best solution found by GA is less than Mean best solution found by GA + ACO.
- #### Significance Level (α):


  - 5% level
  - 0.05
- #### Calculation of Sample Statistics:


  - ##### `For GA`:

    - Mean best solution (x̄1): 1682.870
    - Standard deviation (s1): ≈ 3592.91
    - Sample size (n1): 11
  - ##### `For GA + ACO`:

    - Mean best solution (x̄2): 1681.370
    - Standard deviation (s2): ≈ 3592.71
    - Sample size (n2): 11
- #### Calculation of Z-Score:


  - ##### `Formula`:

    ```
    z = (x̄1 - x̄2) / sqrt((s1^2/n1) + (s2^2/n2))
    ```
  - ##### `Calculation`:

    ```
    z ≈ 1.500 / sqrt((12897223.28/11) + (12895912.25/11))

    z ≈ 1.500 / sqrt(1172474.844 + 1172255.659)

    z ≈ 1.500 / sqrt(2344730.503)

    z ≈ 1.500 / 1531.326

    So, z ≈ 0.000979
    ```
  - #### Critical Z-Value:

    For `α = 0.05`, the critical z-value is approximately `1.645`.

  In conclusion, Since the calculated `z-score (0.000979)` is less than the critical `z-value (1.645)`, we fail to reject the null hypothesis. Therefore, we do not have sufficient evidence to conclude that the mean best solution found by GA is significantly less than the mean best solution found by GA + ACO.

## 7. Critical Analysis of the Results:

The Genetic Algorithm (GA) and the Genetic Algorithm + Ant Colony Optimization (GA + ACO) are both powerful approaches for solving the Knapsack problem, each with its own strengths and weaknesses. Let's critically analyze these two algorithms based on various factors:

- **Solution Quality**:

  Both GA and GA + ACO exhibit high solution quality, as evidenced by their ability to find solutions close to the known optima for the majority of problem instances. This indicates that both algorithms are effective in exploring the solution space and identifying near-optimal solutions.
- **Convergence Speed**:

  The runtime analysis shows that both algorithms are efficient, with most instances solved within seconds. This suggests that they converge relatively quickly to solutions, making them suitable for real-world applications where computational efficiency is crucial.
- **Algorithm Complexity**:

  GA is a relatively simpler algorithm compared to GA + ACO, which incorporates additional complexity due to the integration of Ant Colony Optimization (ACO). While ACO enhances the exploration and exploitation capabilities of the algorithm, it also introduces additional parameters and computational overhead.
- **Robustness**:

  Both algorithms demonstrate robustness across different problem instances, with the majority of solutions being optimal or near-optimal. This indicates that they are capable of handling diverse problem scenarios and adapting to varying problem characteristics.
- **Parameter Sensitivity**:

  GA typically requires tuning of parameters such as population size, crossover rate, and mutation rate to achieve optimal performance. Similarly, GA + ACO involves tuning parameters specific to ACO, such as alpha, beta, and evaporation rate. Parameter tuning can be time-consuming and may require extensive experimentation to achieve optimal results.
- **Complementary Nature**:

  GA and ACO are complementary in nature, with GA providing a global search capability while ACO offers a local search strategy. The integration of ACO within GA enhances the algorithm's ability to exploit promising regions of the solution space, leading to improved solutions.
- **Scalability**:

  Both algorithms demonstrate scalability, with the ability to handle larger problem instances efficiently. However, as problem size increases, the computational complexity of both algorithms may also increase, requiring careful consideration of computational resources.

In conclusion, both GA and GA + ACO offer effective solutions for the Knapsack problem, with each algorithm having its own set of advantages and trade-offs. While GA is simpler and easier to implement, GA + ACO provides enhanced solution quality through the integration of ACO's local search capabilities. The choice between the two algorithms depends on the specific requirements of the problem, including solution quality, runtime efficiency, and available computational resources. Further research and experimentation may be needed to explore the full potential of these algorithms and optimize their performance for specific problem domains.

## 8. Running the Code

#### Requirements before running codes:

- Install an `IDE` that `compiles` and `runs` Java codes. Recommendation `VS Code`
- How to setup `WSL` Ubuntu terminal shell and run it from `Visual Studio Code:`
  visit: https://www.youtube.com/watch?v=fp45HpZuhS8&t=112s
- How to Install `Java JDK 17` on `Windows 11:` https://www.youtube.com/watch?v=ykAhL1IoQUM&t=136s

#### To run the code:

- Compile all `.java` files in your preferred IDE or using command-line tools.
- Execute the `Main.java` class.
- View the output in the console.
- Makefile I Used:
  - Compiling and Running Code:
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
      tar -cvz *.java -f assignment2.tar.gz

    untar:
      tar -zxvf *.tar.gz
    ```
  - Running the .jar File
    ```bash
    default:
      java -jar assignment2.jar

    clean:
      rm -f *.class
      reset
      clear

    tar:
      tar -cvz *.java -f assignment2.tar.gz

    untar:
      tar -zxvf *.tar.gz
    ```

## 9. Documentation Information

- I used Doxygen Comments for Documentation,
- To generate full documentation run command: ```doxygen Doxyfile ```

---

<p align="center"> The End, Thank You! </P>

---
