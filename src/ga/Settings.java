/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga;

/**
 * Settings for Genetic Algorithm Optimization
 *
 * @author dee
 */
public class Settings {

    private int nGen;                   // number of Gen in a Chromosome
    private int popSize;                // number of Chromosome in a Generation (Population Size)
    private int maxG;                   // maximum Generation (iteration)
    private double pbRec;               // Crossover probability
    private double pbMut;               // Mutation probability
    private double thresholdFitness;    // Fitness threshold to stop the iteration
    private int minValue;               // minimum Value in each gen in a Chromosome
    private int maxValue;               // maximum Value in each gen in a Chromosome

    public Settings(int nGen, int popSize) {
        this.nGen = nGen;
        this.popSize = popSize;
        maxG = 100;
        pbRec = 0.8;
        pbMut = 0.01;
        thresholdFitness = 0.1;
    }

    public Settings(int nGen, int popSize, int maxG) {
        this(nGen, popSize);
        this.maxG = maxG;
    }

    public Settings(int nGen, int popSize, double pbRec) {
        this(nGen, popSize);
        this.pbRec = pbRec;
    }

    public Settings(int nGen, int popSize, double pbRec, double pbMut) {
        this(nGen, popSize);
        this.pbRec = pbRec;
        this.pbMut = pbMut;
    }

    public Settings(int nGen, int popSize, int maxG, double pbRec) {
        this(nGen, popSize, maxG);
        this.pbRec = pbRec;
    }

    public Settings(int nGen, int popSize, int maxG, double pbRec, double pbMut) {
        this(nGen, popSize, maxG);
        this.pbRec = pbRec;
        this.pbMut = pbMut;
    }

    public Settings(int nGen, int popSize, int maxG, int maxValue) {
        this(nGen, popSize, maxG);
        this.maxValue = maxValue;
    }

    public Settings(int nGen, int popSize, int maxG, int minValue, int maxValue) {
        this(nGen, popSize, maxG, maxValue);
        this.minValue = minValue;
    }

    public Settings(int nGen, int popSize, int maxG, int minValue, int maxValue, double pbRec) {
        this(nGen, popSize, maxG, minValue, maxValue);
        this.pbRec = pbRec;
    }

    public Settings(int nGen, int popSize, int maxG, int minValue, int maxValue, double pbRec, double pbMut) {
        this(nGen, popSize, maxG, minValue, maxValue, pbRec);
        this.pbMut = pbMut;
    }

    public Settings(int nGen, int popSize, int maxG, int minValue, int maxValue, double pbRec, double pbMut, double maxF) {
        this(nGen, popSize, maxG, minValue, maxValue, pbRec, pbMut);
        this.thresholdFitness = maxF;
    }

    public int getnGen() {
        return nGen;
    }

    public void setnGen(int nGen) {
        this.nGen = nGen;
    }

    public int getPopSize() {
        return popSize;
    }

    public void setPopSize(int popSize) {
        this.popSize = popSize;
    }

    public int getMaxG() {
        return maxG;
    }

    public void setMaxG(int maxG) {
        this.maxG = maxG;
    }

    public double getPbRec() {
        return pbRec;
    }

    public void setPbRec(double pbRec) {
        this.pbRec = pbRec;
    }

    public double getPbMut() {
        return pbMut;
    }

    public void setPbMut(double pbMut) {
        this.pbMut = pbMut;
    }

    public double getThresholdFitness() {
        return thresholdFitness;
    }

    public void setThresholdFitness(double thresholdFitness) {
        this.thresholdFitness = thresholdFitness;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

}
