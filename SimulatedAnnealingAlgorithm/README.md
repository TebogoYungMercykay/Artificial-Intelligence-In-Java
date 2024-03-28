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
--------------------------------------------------------------------------------------
 Campus		| Hatfield     	| Hillcrest	| Groenkloof    | Prinsof 	| Mamelodi
------------|---------------|-----------|---------------|-----------|-----------------
 Hatfield	| 0				| 15		| 20			| 22		| 30
 Hillcrest	| 15			| 0			| 10			| 12		| 25
 Groenkloof	| 20			| 10		| 0				| 8			| 22
 Prinsof	| 22			| 12		| 8				| 0			| 18
 Mamelodi	| 30			| 25		| 22			| 18		| 0
--------------------------------------------------------------------------------------


## TABLE 2: Results (0.00 is Placeholder for Values)
----------------------------------------------------
 Problem Set			| ILS     	| SA	
------------------------|-----------|---------------	
 Best Solution(route)	| 0.00		| 0.00		
 Objective Function Val	| 0.00		| 0.00		
 Runtime				| 0.00		| 0.00		
 Av Obj Function		| 0.00		| 0.00	
----------------------------------------------------

# Hint:
You can use Iterated Local Search by first generating an initial solution, for example, using random selection. You can then apply a local search to the initial solution e.g swapping two consecutive campuses on a solution and then apply a perturbation operator to the solution, for example, by randomly swapping any two campuses. After the perturbation, you can use local search to optimize the solution. The process of perturbation and local search is repeated for a fixed number of iterations or until a stopping criterion is met such as no improvement after a set number of consecutive iterations.

# Solution

## Simulated Annealing (SA) Algorithm Documentation (1).

#### Overview:
The Simulated Annealing (SA) algorithm is a probabilistic optimization technique inspired by the annealing process in metallurgy. It is used to find approximate solutions to combinatorial optimization problems by allowing the algorithm to escape local optima through controlled randomness.

#### Algorithm Description:
1. **Initialization**:
   - Start by generating an initial solution. Similar to ILS, this could be done randomly or using a heuristic approach.
   
2. **Annealing Schedule**:
   - Define an annealing schedule that governs the exploration-exploitation trade-off. The temperature parameter decreases over time, controlling the likelihood of accepting worse solutions.
   
3. **Neighbor Generation**:
   - Define a method for generating neighboring solutions. This could involve making small perturbations to the current solution.
   
4. **Acceptance Probability**:
   - Define a probability function to accept or reject a new solution based on its objective function value and the current temperature. Solutions with worse objective function values may be accepted probabilistically to escape local optima.
   
5. **Cooling Schedule**:
   - Update the temperature according to a cooling schedule, gradually reducing the exploration rate over time.

#### Java Implementation:
- The Java implementation follows the steps outlined above, with classes and methods representing the solution space, generating neighbors, calculating acceptance probabilities, and managing the annealing process.

#### Usage:
- Instantiate the `SimulatedAnnealing` class and call the `run()` method to execute the algorithm.
- Adjust parameters such as the initial temperature, cooling rate, and termination criterion to achieve the desired balance between exploration and exploitation.

---

## Simulated Annealing (SA) Algorithm Documentation (2).

### Overview
Simulated Annealing (SA) is a probabilistic technique for approximating the global optimum of a given function. It is often used when the search space is discrete (e.g., all tours that visit a given set of cities). For problems where finding an absolute optimal solution is not feasible or necessary, simulated annealing may be a good alternative.

### Algorithm Description
1. **Initialization**: The algorithm starts by generating an initial solution. This could be done randomly or using a heuristic such as the nearest neighbor heuristic.

2. **Annealing Schedule**: The algorithm defines an annealing schedule, which is a rule that determines how the temperature decreases over time. The temperature controls the trade-off between exploration (accepting worse solutions) and exploitation (focusing on improving the current solution).

3. **Neighbor Generation**: The algorithm generates neighboring solutions from the current solution. In the context of the TSP, this could involve swapping two cities in the sequence.

4. **Acceptance Probability**: The algorithm calculates the acceptance probability of a new solution. If the new solution is better than the current solution, it is always accepted. If it is worse, it is accepted with a probability that depends on the difference in quality between the new solution and the current solution and the current temperature.

5. **Cooling Schedule**: The algorithm decreases the temperature according to the cooling schedule, reducing the likelihood of accepting worse solutions over time.

### Java Implementation
The Java implementation of the SA algorithm for the TSP follows the steps outlined above. It uses an array to represent the sequence of cities (i.e., the solution), and it includes methods for generating the initial solution, generating neighboring solutions, calculating the acceptance probability, and updating the temperature.

### Usage
To use the SA algorithm for the TSP, you need to instantiate the `SimulatedAnnealingTSP` class with the distance matrix of the problem and call the `solve` method. You can also adjust the parameters of the algorithm, such as the initial temperature, the cooling rate, and the termination criterion, to fine-tune its performance.

---
---

<p align="center"> The End, Thank You! </P>
---