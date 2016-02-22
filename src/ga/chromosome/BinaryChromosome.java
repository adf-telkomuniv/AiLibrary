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
public class BinaryChromosome extends Chromosome {

    private byte gen[];

    public BinaryChromosome(int size) {
        super(size);
        gen = new byte[size];
        randomChromosome();
    }

    /**
     * randomize gen values of 1 and 0
     */
    @Override
    public void randomChromosome() {
        for (int i = 0; i < getGen().length; i++) {
            getGen()[i] = (byte) ((random.nextBoolean()) ? 1 : 0);
        }
    }

    /**
     * get array of byte gen
     *
     * @return array of byte
     */
    public byte[] getGen() {
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

    public void setGen(byte[] gen) {
        this.gen = gen;
    }

    @Override
    public void setGen(int i, double value) {
        gen[i] = (byte) value;
    }

    @Override
    public BinaryChromosome clone() {
        BinaryChromosome c = new BinaryChromosome(size());
//        c.setGen(this.getGen().clone());;
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
