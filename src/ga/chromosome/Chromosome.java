/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.chromosome;

import java.util.Random;

/**
 * Abstract Class Chromosome
 *
 * @author dee
 */
public abstract class Chromosome implements Cloneable {

    private int size;
    protected Random random;

    /**
     * Chromosome Constructor
     *
     * @param size gen number (chromosome length)
     */
    public Chromosome(int size) {
        this.random = new Random();
        this.size = size;
    }

    /**
     * return length of chromosome, number of gen in the chromosome
     *
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * set number of gen in the chromosome
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * swap value of gen index id1 and id2
     *
     * @param id1 gen index id1
     * @param id2 gen index id2
     */
    public void swap(int id1, int id2) {
        double c = getGen(id1);
        setGen(id1, getGen(id2));
        setGen(id2, c);
    }

    /**
     * randomize gen values
     */
    public abstract void randomChromosome();

    /**
     * set value of gen index i
     *
     * @param i gen index i
     * @param value new gen value
     */
    public abstract void setGen(int i, double value);

    /**
     * get value of gen index i
     *
     * @param i gen index i
     * @return value of gen index i
     */
    public abstract double getGen(int i);

    @Override
    public abstract Chromosome clone();

}
