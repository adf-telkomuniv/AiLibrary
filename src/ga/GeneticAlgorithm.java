/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga;

import ga.chromosome.Chromosome;
import ga.operators.CrossOver;
import ga.operators.ParentSelection;
import java.util.Random;

/**
 *
 * @author dee
 */
public class GeneticAlgorithm {

    private GaSettings settings;
    private OperatorOptions operator;
    private Population population;
    private double bestFitness;
    private Chromosome bestIndv;
    private double maxFGen[];

    public GeneticAlgorithm(GaSettings settings, OperatorOptions operator) {
        this.settings = settings;
        this.operator = operator;
        this.population = new Population();
    }

    public void optimize(ChromosomeEvaluator evaluator) {
        double fitness;
        double fitnessList[] = new double[settings.getPopSize()];
        population.initializePopulation(operator.getChromosomeType(), settings);
        maxFGen = new double[settings.getMaxG()];
        Population pool;
        Chromosome indv;
        for (int gen = 0; gen < settings.getMaxG(); gen++) {
            System.out.println("generation = " + gen);
            /**
             * fitness evaluation
             */
            bestIndv = population.getChromosome(0);
            System.out.println(bestIndv);
            bestFitness = evaluator.evaluateFitness(bestIndv);
            fitnessList[0] = bestFitness;
            for (int i = 1; i < settings.getPopSize(); i++) {
                indv = population.getChromosome(i);
                fitness = evaluator.evaluateFitness(indv);
                fitnessList[0] = fitness;
                if (fitness > bestFitness) {
                    bestFitness = fitness;
                    bestIndv = indv.clone();
                }
            }
            maxFGen[gen] = bestFitness;
            pool = new Population();

            /**
             * elitism
             */
            int start = 0;
            if (operator.getSurvivorSelection() == 1) {
                start = 1;
                pool.addChromosome(bestIndv);
                if (settings.getPopSize() % 2 == 0) {
                    start = 2;
                    pool.addChromosome(bestIndv);
                }
            }
            

            /**
             * parent selection and recombination
             */
            for (int j = start; j < settings.getPopSize(); j += 2) {
                Chromosome p[] = ParentSelection.selectParent(operator.getParentSelection(), population, fitnessList);
                Random r = new Random();
                if (r.nextFloat() < settings.getPbRec()) {
                    Chromosome a[] = CrossOver.crossOver(operator.getCrossOverType(), p[0], p[1], operator.getPattern());
                    pool.addChromosome(a[0]);
                    pool.addChromosome(a[1]);
                } else {
                    pool.addChromosome(p[0]);
                    pool.addChromosome(p[1]);
                }
            }

            /**
             * mutation
             */
            pool.mutatePopulation(operator.getMutationType(), settings.getPbMut(), start);
            
            
            /**
             * survivor selection
             */
            if (operator.getSurvivorSelection() < 2) {
                population = pool;
            } else {
                population = pool;
                //
            }

            if (bestFitness >= settings.getThresholdFitness()) {
                break;
            }
            System.out.println("best = " + bestFitness + " - " + bestIndv);
            population.setChromosome(0, bestIndv);
        }
    }

    public GaSettings getSettings() {
        return settings;
    }

    public void setSettings(GaSettings settings) {
        this.settings = settings;
    }

    public OperatorOptions getChromosomeOpt() {
        return operator;
    }

    public void setChromosomeOpt(OperatorOptions chromosomeOpt) {
        this.operator = chromosomeOpt;
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public Chromosome getBestIndv() {
        return bestIndv;
    }

}
