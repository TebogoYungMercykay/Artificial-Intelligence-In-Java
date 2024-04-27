# Artificial Intelligence (COS314) - Assignment 2 Documentation

## My Details
- Names: Selepe Sello
- Email: u20748052@tuks.co.za
- Date: 27 April 2024
- Topic: Knapsack Problem (Optimisation Challenge)

## Configuration Descriptions

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

    | Parameter              | Genetic Algorithm (GA) | GA + ACO (LS)          |
    |------------------------|------------------------|------------------------|
    | Population Multiplier  | 8                      | 8                      |
    | Crossover Rate         | 0.3                    | 0.3                    |
    | Mutation Rate          | 0.4                    | 0.4                    |
    | Max Generations        | 500                    | 500                    |
    | Stopping Iterations    | 250                    | 100                    |
    | Penalty Factor         | 10                     | N/A                    |
    | Tournament Portion     | 0.2                    | N/A                    |
    | Initial Bit Probability | 0.1                   | N/A                    |
    | Max Iterations (ACO)   | N/A                    | 200                    |
    | Alpha (ACO)            | N/A                    | 0.5                    |
    | Beta (ACO)             | N/A                    | 2                      |
    | Evaporation Rate (ACO) | N/A                    | 0.95                   |
    | Initial Pheromone (ACO)| N/A                    | 0.5                    |
    | Update Strength (ACO)  | N/A                    | 0.5                    |
    | Local Search Method (ACO) | N/A                 | 0 (None)               |

- #### Experimental Procedure:
    - **Used Provided Problem Instances**: Utilized the provided Knapsack problem instances with varying parameters.
    - **Run GA**: Execute the Genetic Algorithm (GA) on each instance, recording best solutions and convergence stats.
    - **Run GA + ACO**: Execute the hybrid algorithm (GA + ACO) on each instance, recording best solutions and convergence stats.
    - **Compare Performance**: Assess solution quality and convergence speed of both algorithms.
    - **Analyze Statistically**: Perform statistical tests like z-tests at a 5% significance level to evaluate performance differences.
    - **Discuss & Conclude**: Interpret results, perform a critical analysis, discuss the advantages and disadvantages of each approach, and suggest directions for future research.

## 5. Comparison of GA and GA + LS on 10 Knapsack Problem Instances

| Problem Instance     | Algorithm | Seed Value | Best Solution | Known Optimum | Runtime (seconds) |
|----------------------|-----------|------------|---------------|---------------|-------------------|
| f9_l-d_kp_5_80       | GA        | 24         | 130.00        | 130.00        | 0.00              |
|                      | GA - ILS  | 24         | 130.00        | 130.00        | 0.01              |
| f1_l-d_kp_10_269     | GA        | 24         | 295.00        | 295.00        | 0.00              |
|                      | GA - ILS  | 24         | 295.00        | 295.00        | 0.01              |
| f3_l-d_kp_4_20       | GA        | 24         | 35.00         | 35.00         | 0.00              |
|                      | GA - ILS  | 24         | 35.00         | 35.00         | 0.00              |
| knapPI_1_100_1000_1  | GA        | 24         | 9147.00       | 9147.00       | 7.00              |
|                      | GA - ILS  | 24         | 9147.00       | 9147.00       | 0.22              |
| f6_l-d_kp_10_60      | GA        | 24         | 52.00         | 52.00         | 0.00              |
|                      | GA - ILS  | 24         | 52.00         | 52.00         | 0.00              |
| f5_l-d_kp_15_375     | GA        | 24         | 481.07        | 481.07        | 0.00              |
|                      | GA - ILS  | 24         | 481.07        | 481.07        | 0.01              |
| f8_l-d_kp_23_10000   | GA        | 24         | 9767.00       | 9767.00       | 0.00              |
|                      | GA - ILS  | 24         | 9750.00       | 9767.00       | 0.01              |
| f10_l-d_kp_20_879    | GA        | 24         | 1025.00       | 1025.00       | 0.00              |
|                      | GA - ILS  | 24         | 1025.00       | 1025.00       | 0.01              |
| f4_l-d_kp_4_11       | GA        | 24         | 23.00         | 23.00         | 0.00              |
|                      | GA - ILS  | 24         | 23.00         | 23.00         | 0.00              |
| f7_l-d_kp_7_50       | GA        | 24         | 105.00        | 107.00        | 0.00              |
|                      | GA - ILS  | 24         | 107.00        | 107.00        | 0.00              |
| f2_l-d_kp_20_878     | GA        | 24         | 1024.00       | 1024.00       | 0.00              |
|                      | GA - ILS  | 24         | 1024.00       | 1024.00       | 0.01              |

## Documentation for Constants:

### Ant Colony Optimization Parameters:
- `MAX_ITERATIONS`: Maximum number of iterations.
- `STOPPING_ITERATIONS`: Number of iterations after which to stop if no improvement is observed.
- `ALPHA`: Alpha parameter for pheromone influence.
- `BETA`: Beta parameter for heuristic information influence.
- `EVAPORATION_RATE`: Rate at which pheromone evaporates.
- `INITIAL_PHEROMONE`: Initial pheromone level.
- `UPDATE_STRENGTH`: Strength of pheromone update.
- `LS_METHOD`: Local search method (0 = none, 1 = replace the worst item, 2 = assess all single item flips and choose the best).

### Genetic Algorithm Parameters:
- `POPULATION_MULTIPLIER`: Population size multiplier.
- `CROSSOVER_RATE`: Rate of crossover during reproduction.
- `MUTATION_RATE`: Rate of mutation during reproduction.
- `MAX_GENERATIONS`: Maximum number of generations.
- `STOPPING_ITERATIONS`: Number of iterations after which to stop if no improvement is observed.
- `PENALTY_FACTOR`: Penalty factor for infeasible solutions.
- `TOURNAMENT_PORTION`: Proportion of the population to select during tournament selection.
- `INITIAL_BIT_PROBABILITY`: Initial probability for a bit to be set in the initial population.


## The Known Optimums:

| Filename            | Optimum  |
| ------------------- | -------- |
| f1_l-d_kp_10_269    | 295      |
| f2_l-d_kp_20_878    | 1024     |
| f3_l-d_kp_4_20      | 35       |
| f4_l-d_kp_4_11      | 23       |
| f5_l-d_kp_15_375    | 481.0694 |
| f6_l-d_kp_10_60     | 52       |
| f7_l-d_kp_7_50      | 107      |
| knapPI_1_100_1000_1 | 9147     |
| f8_l-d_kp_23_10000  | 9767     |
| f9_l-d_kp_5_80      | 130      |
| f10_l-d_kp_20_879   | 1025     |


---

<p align="center"> The End, Thank You! </P>

---

