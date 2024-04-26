# The Known Optimums:

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

# Problem Instances Comparison

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

---

<p align="center"> The End, Thank You! </P>

---
