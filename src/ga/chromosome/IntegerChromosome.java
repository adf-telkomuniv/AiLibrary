/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.chromosome;

/**
 *
 * @author dee
 */
public class IntegerChromosome extends Chromosome {

    private int gen[];
    private int min = 0;
    private int max;

    public IntegerChromosome(int nGen, int max) {
        super(nGen);
        this.max = max;
        gen = new int[nGen];
        randomChromosome();
    }

    public IntegerChromosome(int nGen, int min, int max) {
        super(nGen);
        this.max = max;
        this.min = min;
        gen = new int[nGen];
        randomChromosome();
    }

    @Override
    public void randomChromosome() {
        for (int i = 0; i < getGen().length; i++) {
            getGen()[i] = random.nextInt(max - min) + min;
        }
    }

    public int[] getGen() {
        return gen;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public String toString() {
        String s = "[";
        for (int i = 0; i < getGen().length; i++) {
            s += getGen()[i] + ", ";
        }
        s += "]";
        return s;
    }

    public void setGen(int[] gen) {
        this.gen = gen;
    }

    @Override
    public void setGen(int i, double value) {
        gen[i] = (int) value;
    }

    @Override
    public IntegerChromosome clone() {
        IntegerChromosome c = new IntegerChromosome(size(), min, max);
//        c.setGen(this.getGen().clone());
        for (int i = 0; i < gen.length; i++) {
            c.setGen(i, gen[i]);
        }
        return c;
    }

    @Override
    public double getGen(int i) {
        return gen[i];
    }

}
