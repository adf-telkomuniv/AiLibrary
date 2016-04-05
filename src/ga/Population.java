/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga;

import ga.chromosome.PermutationChromosome;
import ga.chromosome.RealChromosome;
import ga.chromosome.BinaryChromosome;
import ga.chromosome.Chromosome;
import ga.chromosome.IntegerChromosome;
import ga.operators.Mutation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dee
 */
public class Population {

    private List<Chromosome> population;

    public Population() {
        population = new ArrayList();
    }

    public Chromosome getChromosome(int i) {
        return population.get(i);
    }

    public void addChromosome(Chromosome c) {
        population.add(c);
    }

    public void initializePopulation(int chromosomeType, Settings settings) {
        switch (chromosomeType) {
            case 0:
                for (int i = 0; i < settings.getPopSize(); i++) {
                    population.add(new BinaryChromosome(settings.getnGen()));
                }
                break;
            case 1:
                for (int i = 0; i < settings.getPopSize(); i++) {
                    population.add(new IntegerChromosome(settings.getnGen(), settings.getMinValue(), settings.getMaxValue()));
                }
                break;
            case 2:
                for (int i = 0; i < settings.getPopSize(); i++) {
                    population.add(new RealChromosome(settings.getnGen()));
                }
                break;
            case 3:
                for (int i = 0; i < settings.getPopSize(); i++) {
                    population.add(new PermutationChromosome(settings.getnGen()));
                }
                break;
        }
    }

    public void mutatePopulation(int mutationType, double pbMut, int start) {
        for (int i = start; i < population.size(); i++) {
            Chromosome c = population.get(i);
            switch (mutationType) {
                case 0:
                    c = Mutation.binary(c, pbMut);
                    break;
                case 1:
                    c = Mutation.turnOver(c, pbMut);
                    break;
                case 2:
                    c = Mutation.random(c, pbMut);
                    break;
                case 3:
                    c = Mutation.creep(c, pbMut);
                    break;
                case 4:
                    c = Mutation.uniform(c, pbMut);
                    break;
                case 5:
                    c = Mutation.gaussian(c, pbMut);
                    break;
                case 6:
                    c = Mutation.swap(c, pbMut);
                    break;
                case 7:
                    c = Mutation.insert(c, pbMut);
                    break;
                case 8:
                    c = Mutation.scramble(c, pbMut);
                    break;
                case 9:
                    c = Mutation.inversion(c, pbMut);
                    break;
                case 10:
                    c = Mutation.nScramble(c, pbMut);
                    break;
            }
        }
    }

    public void printPop() {
        population.forEach(System.out::println);
    }

    public int getUkPop() {
        return population.size();
    }

}
