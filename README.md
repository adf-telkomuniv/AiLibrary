# AiLibrary
1. Genetic Algorithm lib using Java
2. A* Algorithm lib using Java

---
## Genetic Algorithm lib using Java

### Class Setting.java
Settings for Genetic Algorithm Optimization
  
### Class OperatorOptions.java
Settings for Operator Options in Genetic Algorithm

### Interface ChromosomeEvaluator.java
Use this interface and implement method 
```java
public abstract double evaluateFitness(Chromosome c);
```
define method evaluateFitness that returns double falue as the quality of Chromosome c

### Class GeneticAlgorithm.java
Main Library Class for Genetic Algorithm System

### Using Genetic Algorithm Library
```java
	int nGen = 10;
	int maxG = 100;
	Settings settings = new Settings(nGen, maxG);
	OperatorOptions operations = new OperatorOptions();
	GeneticAlgorithm ga = new GeneticAlgorithm(settings, operations);
	Evaluator evaluator = new Evaluator();
	ga.optimize(evaluator);
	System.out.println(ga.getBestFitness());
	System.out.println(ga.getBestIndv());
```

---
## Genetic Algorithm lib using Java
### Class AStar.java
Main Library Class for A* Algorithm System <br>
generate shortest apth from node Start to Goal <br>
input N-nodes [index node = 0-N]

### Using A* Algorithm Library
#### Input : 
* int N;
 * number of nodes
* double[] heuristic[N]
 * array size of n-nodes, heuristic value of each representated node to Goal
* int[][] nodeMap = new int[2][M]
 * adjacent array with size of 2xM, represented M-edges from nodeMap[0][i] to nodeMap[1][i]
* double[] cost = new double[M]
 * cost to cross each edge[i] from nodeMap[0][i] to nodeMap[1][i]
 * 
 
### A* Algorithm Method : 
```java
	public int[] optimize(int start, int goal)
	public double getTotalCost(int[] path)
```

### Using A* Algorithm Library
```java
double[] heur = {0, 0.5, 5, 2, 4.5, 4, 5, 6, 7, 8, 10, 10.5};
int[][] nodeMap = {
	{11, 11, 11, 10, 10, 9, 9, 7, 8, 8, 6, 6, 5, 5, 2, 3, 3, 4, 1},
	{10, 8, 4, 9, 8, 7, 2, 6, 7, 5, 5, 3, 3, 4, 0, 0, 4, 1, 0}
	};
double[] cost = {5, 3, 15, 3, 3, 2, 8, 2, 2, 3, 1, 3, 2, 6, 6, 3, 2, 5, 1};
AStar astar = new AStar(heur, nodeMap, cost);
int start = 11;
int goal = 0;
int[] path = astar.optimize(start, goal);
double total = astar.getTotalCost(path);
System.out.println("total = " + total);
```
