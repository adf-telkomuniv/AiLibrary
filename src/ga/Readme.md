# Genetic Algorithm lib using Java

## Class Setting.java
Settings for Genetic Algorithm Optimization

### Parameters
* number of Gen in a Chromosome *required*
  * integer 1-n
* number of Chromosome in a Generation (Population Size) *required*
  * integer 1-n
* maximum Generation (iteration)
  * integer 1-n (default maxG = 100)
* Crossover probability
  * double 0-1 (recommended 0.6-0.8, default = 0.8)
* Mutation probability 
  * double 0-1 (recommended 0.01-0.1, default = 0.01)
* Fitness threshold to stop the iteration
  * double 0-1 (default = 0.1)
* minimum Value in each gen in a Chromosome
  * integer (default = 0)
* maximum Value in each gen in a Chromosome
  * integer
  
## Class OperatorOptions.java
Settings for Operator Options in Genetic Algorithm

### Parameters
* parentSelection
  * integer 0-5 (default = 0)
  * 0 : Roulette Wheel
  * 1 : Linear Scaling
  * 2 : Sigma Scaling
  * 3 : Linear Ranking
  * 4 : Non Linear Ranking
  * 5 : Tournament Selection
* survivorSelection
  * integer 0-2 (default = 0)
  * 0 : Generational
  * 1 : Generational with Elitism
  * 2 : 
* chromosomeType
  * integer 0-3 (default = 0)
  * 0 : Binary
  * 1 : Integer
  * 2 : Real
  * 3 : Permutation
* crossoverType
  * integer 0-9 (default = 0)
  * 0 : Single Point Crossover (Binary, Integer, and Real Chromosome)
  * 1 : nPoint Crossover (Binary, Integer, and Real Chromosome)
  * 2 : Uniform Crossover (Binary, Integer, and Real Chromosome)
  * 3 : Single Arithmetic Crossover (Real Chromosome)
  * 4 : Simple Arithmetic Crossover (Real Chromosome)
  * 5 : Whole Arithmetic Crossover (Real Chromosome)
  * 6 : Order Crossover (Permutation Chromosome)
  * 7 : Partially Mapped Crossover (Permutation Chromosome)
  * 8 : Cycle Crossover (Permutation Chromosome)
  * 9 : Edge Crossover (Permutation Chromosome)
* mutationType
  * integer 0-10 (default = 0)
  * 0 : Binary Mutation (Binary Chromosome)
  * 1 : Turn Over Mutation (Binary and Integer Chromosome)
  * 2 : Random Mutation (Integer and Real Chromosome)
  * 3 : Creep Mutation (Integer and Real Chromosome)
  * 4 : Uniform Mutation (Integer and Real Chromosome)
  * 5 : Gaussian Mutation (Real Chromosome)
  * 6 : Swap Mutation (Permutation Chromosome)
  * 7 : Insert Mutation (Permutation Chromosome)
  * 8 : Scramble Mutation (Permutation Chromosome)
  * 9 : Inversion Mutation (Permutation Chromosome)
  * 10 : nScramble Mutation (Permutation Chromosome)
* pattern

## Interface ChromosomeEvaluator.java
Use this interface and implement method 
```java
public abstract double evaluateFitness(Chromosome c);
```
define method evaluateFitness that returns double falue as the quality of Chromosome c <br>
Chromosome c may be Binary, Integer, Real, or Permutation

## Class GeneticAlgorithm.java
Main Library Class for Genetic Algorithm System <br>

## Using Genetic Algorithm Library
use : 
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
