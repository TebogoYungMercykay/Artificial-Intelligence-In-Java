# Comparative Analysis of the Effectiveness of Two Algorithms

This submission contains implementations of two optimization algorithms, Iterated Local Search (ILS), and Simulated Annealing (SA), designed to solve the Campus Tour Optimization problem. Both algorithms aim to find the shortest route that visits each campus exactly once and returns to the starting point, minimizing the total distance traveled by a student to get her clearance form signed by module coordinators across various campuses.

## Algorithms (Technical Specification)

### Iterated Local Search (ILS)

- **Purpose**: The ILS algorithm iteratively explores the solution(route) space by generating initial solutions(routes), performing local search to improve them, and perturbing the solutions(routes) to escape local optima.
- **Main Components**:
  - `IteratedLocalSearch.java`: Represents the implementation of the ILS algorithm.
  - `Solution.java`: Defines the structure of a solution(route) and provides methods for calculating distances, performing 2-opt swaps, and evaluating solution(route) quality.
  - `SolutionDetails.java`: Manages multiple solutions(routes), tracks the best solution(route) found, and calculates statistics such as average distance and total runtime.
- **Usage**: The `IteratedLocalSearch` class orchestrates the execution of the algorithm and outputs the best solution(route) found along with relevant statistics.

### Simulated Annealing (SA)

- **Purpose**: SA mimics the process of annealing in metallurgy to find optimal solutions(routes) by probabilistically accepting worse solutions(routes) initially and gradually decreasing the probability as the algorithm progresses.
- **Main Components**:
  - `SimulatedAnnealing.java`: Implements the SA algorithm.
  - `Solution.java`: Shared with ILS, providing the structure for solutions(routes) and methods for distance calculation and solution(route) evaluation.
  - `SolutionDetails.java`: Shared with ILS, manages solutions(routes) and tracks the best solution(route) found.
- **Usage**: The `SimulatedAnnealing` class drives the SA algorithm execution, generating initial solutions(routes), perturbing them, and cooling the system temperature.

## Presentation of Results

The testing main used to produce this output is in the `Main.java` class.

```
--------------------------------------------------------------------
| Problem Set            | ILS                | SA                 |
|------------------------|--------------------|--------------------|
| Best Solution(route)   | [0, 1, 2, 3, 4, 0] | [0, 4, 3, 2, 1, 0] |
| Objective Function Val | 81                 | 81                 |
| Runtime(s)             | 0.00               | 0.00               |
| Runtime(ms)            | 1.00               | 1.00               |
| Average Obj Function   | 96.57              | 91.74              |
| Discovered of Routes   | 130                | 140                |
--------------------------------------------------------------------
```

## Graphical Plot Representation

![Graphical Plot Representation Image](Graphical_Plot_Representation.png)

## Critical Comparative Analysis 

1. **Best Solution(route)**:
   - Both ILS and SA algorithms found solutions(routes) with the same objective function value (81), indicating that they achieved the same optimal solution(route) quality.
   - However, the routes themselves differ: ILS produced the route [0, 1, 2, 3, 4, 0], while SA produced [0, 4, 3, 2, 1, 0].

2. **Runtime**:
   - Both ILS and SA algorithms executed in 0.001 seconds (1 millisecond), suggesting that they had comparable runtime performance.
   - Although the runtime is the same, it's important to note that this may vary depending on factors such as computational resources and problem complexity.

3. **Average Objective Function**:
   - The average objective function value for ILS was 96.57, while for SA it was 91.74.
   - This indicates that, on average, SA produced solutions(routes) closer to the optimal value compared to ILS.

**Conclusion**:
- Both ILS and SA algorithms successfully found the optimal solution(route) for the Campus Tour Optimization problem.
- While ILS produced a slightly higher average objective function value, SA demonstrated a marginally better performance in terms of the average objective function value.
- Considering the comparable runtime and the same optimal solution(route) quality, the choice between ILS and SA may depend on factors such as specific requirements, preference for solution quality, and computational resources.

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
- Makefile I Used:
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
   tar -cvz *.java -f Search.tar.gz

untar:
   tar -zxvf *.tar.gz
```

## Documentation

- I used Doxygen Comments for Documentation,
- To generate documentation run command: ```doxygen Doxyfile ```

---

<p align="center"> The End, Thank You! </P>

---
