/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.operators;

import ga.chromosome.Chromosome;
import ga.Population;
import java.util.Random;
import java.util.stream.DoubleStream;

/**
 *
 * @author dee
 */
public class ParentSelection {

    public static Chromosome[] selectParent(int selectionType, Population population, double fitness[]) {
        switch (selectionType) {
            case 0:
                return rouletteWheel(population, fitness);
            case 1:
                return linearScaling(population, fitness);
            case 2:
                return sigmaScaling(population, fitness);
            case 3:
                return linearRanking(population, fitness);
            case 4:
                return nonLinearRanking(population, fitness);
            case 5:
                return tournamentSelection(population, fitness);

        }
        return null;
    }

    public static Chromosome[] rouletteWheel(Population population, double fitness[]) {
        double sum = DoubleStream.of(fitness).sum();
        Random r = new Random();
        double pbR = r.nextFloat() * sum;
        double fn = 0;
        int i = 0;
        int x = 0;
        Chromosome parent[] = new Chromosome[2];
        while (i < population.getUkPop()) {
            fn += fitness[i];
            if (fn > pbR) {
                parent[x] = population.getChromosome(i);
                x++;
                if (x == 2) {
                    return parent;
                }
            }
            i++;
        }
        if (x == 2) {
            return parent;
        } else {
            return rouletteWheel(population, fitness);
        }
    }

    public static Chromosome[] linearScaling(Population population, double fitness[]) {
        return null;
    }

    public static Chromosome[] sigmaScaling(Population population, double fitness[]) {
        return null;
    }

    public static Chromosome[] linearRanking(Population population, double fitness[]) {
        return null;
    }

    public static Chromosome[] nonLinearRanking(Population population, double fitness[]) {
        return null;
    }

    public static Chromosome[] tournamentSelection(Population population, double fitness[]) {
        return null;
    }

}
