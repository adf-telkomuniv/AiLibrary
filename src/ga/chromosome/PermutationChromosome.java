/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.chromosome;

//import java.util.ArrayList;
/**
 *
 * @author dee
 */
public class PermutationChromosome extends Chromosome {

    private int gen[];

    public PermutationChromosome(int nGen) {
        super(nGen);
        gen = new int[nGen];
        randomChromosome();
    }

    @Override
    public Chromosome clone() {
        PermutationChromosome c = new PermutationChromosome(size());
        for (int i = 0; i < gen.length; i++) {
            c.setGen(i, gen[i]);            
        }
        return c;
    }

    @Override
    public void randomChromosome() {
        for (int i = 0; i < getGen().length; i++) {
            getGen()[i] = i + 1;
        }
        for (int i = 0; i < gen.length; i++) {
            gen = swap(gen, random.nextInt(size()), random.nextInt(size()));
        }
    }

    public int[] swap(int[] c, int a, int b) {
        int x = c[a];
        c[a] = c[b];
        c[b] = x;
        return c;
    }

    public void swap(int a, int b) {
        int c = this.getGen()[a];
        this.getGen()[a] = this.getGen()[b];
        this.getGen()[b] = c;

    }

    public int[] getGen() {
        return gen;
    }

    @Override
    public void setGen(int i, double value) {
        gen[i] = (int) value;
    }

    @Override
    public double getGen(int i) {
        return gen[i];
    }

    public void setGen(int[] gen) {
        this.gen = gen;
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
}
