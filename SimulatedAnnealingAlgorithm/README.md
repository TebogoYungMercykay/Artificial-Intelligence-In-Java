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
--------------------------------------------------------------------------------------------------
 Campus		| Hatfield     	| Hillcrest	| Groenkloof    | Prinsof 	| Mamelodi
----------------|---------------|---------------|---------------|---------------|-----------------
 Hatfield	| 0		| 15		| 20		| 22		| 30
 Hillcrest	| 15		| 0		| 10		| 12		| 25
 Groenkloof	| 20		| 10		| 0		| 8		| 22
 Prinsof	| 22		| 12		| 8		| 0		| 18
 Mamelodi	| 30		| 25		| 22		| 18		| 0
--------------------------------------------------------------------------------------------------


## TABLE 2: Results (0.00 is Placeholder for Values)
--------------------------------------------------------
 Problem Set		| ILS     	| SA	
------------------------|---------------|---------------	
 Best Solution(route)	| 0.00		| 0.00		
 Objective Function Val	| 0.00		| 0.00		
 Runtime		| 0.00		| 0.00		
 Av Obj Function	| 0.00		| 0.00	
--------------------------------------------------------

# Hint:
You can use Iterated Local Search by first generating an initial solution, for example, using random selection. You can then apply a local search to the initial solution e.g swapping two consecutive campuses on a solution and then apply a perturbation operator to the solution, for example, by randomly swapping any two campuses. After the perturbation, you can use local search to optimize the solution. The process of perturbation and local search is repeated for a fixed number of iterations or until a stopping criterion is met such as no improvement after a set number of consecutive iterations.