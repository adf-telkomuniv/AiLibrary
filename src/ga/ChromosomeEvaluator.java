/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga;

import ga.chromosome.Chromosome;

/**
 *
 * @author dee
 */
public interface ChromosomeEvaluator {

    public abstract double evaluateFitness(Chromosome c);

}
