# AiLibrary
1. Genetic Algorithm lib using Java
2. A Star Algorithm lib using Java

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
Main Library Class for Genetic Algorithm System <br>

### Using Genetic Algorithm Library
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
