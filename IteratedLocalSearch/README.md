# Instructions

- Only Java or C++ may be used to complete this assignment.
- The use of libraries is strictly prohibited

# Description

A student needs to visit all the UP campuses where she had modules to get her clearance form signed by the module coordinators. She wants to minimize the total distance travelled. The task seeks to find the most efficient route for her, by considering all possible routes and finding the one with the shortest distance. Given a list of the campuses and the distances between each pair of them, what is the shortest possible route that visits each campus exactly once and returns to the starting point (Hatfield)? In this assignment you are required to carry out comparative analysis of the effectiveness of two algorithms, Iterated Local Search (ILS) and Simulated Annealing(SA) to solve the problem. You are given the distance cost matrix in Table 1. Perform the following tasks.

- Develop a program that uses the Iterated Local Search to find the best route. (9 marks)
- Develop a program that uses the Simulated Annealing Algorithm to find the best route. (9 marks)
- Submit a documented report (PDF) that contains the following:
  - A technical specification of each of the 2 programs in the form of an experimental set-up including a detailed description of the algorithm configuration.(2 marks)
  - Presentation of results as specified in Table 2 (4 marks)
  - A graphical plot representation of the objective value (y-axis) vs n degree iterations(x-axis) for both algorithms of the average value and best value on the same graph.(3 marks)
  - Discussion and conclusion (critical comparative analysis of the results i.e of the two algorithms on the problem). (3 marks)

## TABLE 1: Cost Matrix Between Campuses.

---

| Campus     | Hatfield | Hillcrest | Groenkloof | Prinsof | Mamelodi |
| ---------- | -------- | --------- | ---------- | ------- | -------- |
| Hatfield   | 0        | 15        | 20         | 22      | 30       |
| Hillcrest  | 15       | 0         | 10         | 12      | 25       |
| Groenkloof | 20       | 10        | 0          | 8       | 22       |
| Prinsof    | 22       | 12        | 8          | 0       | 18       |
| Mamelodi   | 30       | 25        | 22         | 18      | 0        |

---

## TABLE 2: Results (0.00 is Placeholder for Values)

---

| Problem Set            | ILS  | SA   |
| ---------------------- | ---- | ---- |
| Best Solution(route)   | 0.00 | 0.00 |
| Objective Function Val | 0.00 | 0.00 |
| Runtime                | 0.00 | 0.00 |
| Av Obj Function        | 0.00 | 0.00 |

---

# Hint:

You can use Iterated Local Search by first generating an initial solution, for example, using random selection. You can then apply a local search to the initial solution e.g swapping two consecutive campuses on a solution and then apply a perturbation operator to the solution, for example, by randomly swapping any two campuses. After the perturbation, you can use local search to optimize the solution. The process of perturbation and local search is repeated for a fixed number of iterations or until a stopping criterion is met such as no improvement after a set number of consecutive iterations.

---

# Solution

## Iterated Local Search (ILS) Algorithm Documentation (1).

#### Overview:

The Iterated Local Search (ILS) algorithm is a metaheuristic optimization algorithm used to find approximate solutions to combinatorial optimization problems. It iteratively improves upon an initial solution by applying local search techniques combined with perturbations to escape local optima.

#### Algorithm Description:

1. **Initialization**:

   - Start by generating an initial solution. This could be done randomly or using a heuristic such as nearest neighbor.
2. **Local Search**:

   - Apply a local search algorithm to improve the initial solution. Common approaches include 2-opt, where pairs of edges are swapped to reduce the total distance.
3. **Perturbation**:

   - Apply a perturbation operator to the solution to introduce diversification. This could involve making random changes to the current solution.
4. **Acceptance Criterion**:

   - Determine whether to accept the new solution or revert to the previous one. This could be based on whether the new solution improves the objective function value or a probability distribution.
5. **Termination Criterion**:

   - Repeat steps 2-4 for a fixed number of iterations or until a termination criterion is met, such as no improvement after a certain number of iterations.

#### Java Implementation:

- The Java implementation follows the steps outlined above, utilizing classes and methods to represent the solution space, perform local search, apply perturbations, and manage the main loop of the algorithm.

#### Usage:

- Instantiate the `IteratedLocalSearch` class and call the `run()` method to execute the algorithm.
- Optionally, adjust parameters such as the number of iterations and the termination criterion to fine-tune performance.

---

## Iterated Local Search (ILS) Algorithm Documentation (2).

### Overview

Iterated Local Search (ILS) is a metaheuristic and an optimization algorithm used for solving combinatorial optimization problems. It works by iteratively improving an initial solution using local search and perturbations to escape local optima.

### Algorithm Description

1. **Initialization**: The algorithm starts by generating an initial solution. This could be done randomly or using a heuristic such as the nearest neighbor heuristic.
2. **Local Search**: The algorithm applies a local search to the initial solution. In the context of the TSP, a common local search strategy is the 2-opt heuristic, where two cities are swapped in the sequence if it results in a shorter total distance.
3. **Perturbation**: The algorithm applies a perturbation to the solution to introduce diversification and escape local optima. This could involve reversing a sequence of cities in the solution.
4. **Acceptance Criterion**: The algorithm determines whether to accept the new solution or revert to the previous one. This could be based on whether the new solution improves the objective function value (i.e., the total distance in the TSP).
5. **Termination Criterion**: The algorithm repeats steps 2-4 until a termination criterion is met. This could be a fixed number of iterations without improvement or a maximum number of iterations.

### Java Implementation

The Java implementation of the ILS algorithm for the TSP follows the steps outlined above. It uses an array to represent the sequence of cities (i.e., the solution), and it includes methods for generating the initial solution, applying the local search and the perturbation, and checking the termination criterion.

### Usage

To use the ILS algorithm for the TSP, you need to instantiate the `IteratedLocalSearchTSP` class with the distance matrix of the problem and call the `solve` method. You can also adjust the parameters of the algorithm, such as the number of iterations and the termination criterion, to fine-tune its performance.

---

---

<p align="center"> The End, Thank You! </P>

---
