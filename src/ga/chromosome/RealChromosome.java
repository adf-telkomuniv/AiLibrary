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
public class RealChromosome extends Chromosome {

    private double gen[];

    public RealChromosome(int nGen) {
        super(nGen);
        gen = new double[nGen];
        randomChromosome();
    }

    @Override
    public void randomChromosome() {
        for (int i = 0; i < getGen().length; i++) {
            getGen()[i] = random.nextDouble();
        }
    }

    public double[] getGen() {
        return gen;
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

    public void setGen(double[] gen) {
        this.gen = gen;

    }

    @Override
    public void setGen(int i, double value) {
        gen[i] = value;
    }

    @Override
    public RealChromosome clone() {
        RealChromosome c = new RealChromosome(size());
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
