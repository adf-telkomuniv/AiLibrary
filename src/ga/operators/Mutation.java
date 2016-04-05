/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.operators;

import ga.chromosome.IntegerChromosome;
import ga.chromosome.PermutationChromosome;
import ga.chromosome.Chromosome;
import ga.chromosome.BinaryChromosome;
import ga.chromosome.RealChromosome;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author dee
 */
public class Mutation {

    public static Chromosome binary(Chromosome c, double pMut) {
        BinaryChromosome x = (BinaryChromosome) c;
        Random random = new Random();
        for (int i = 0; i < x.size(); i++) {
            x.getGen()[i] = (byte) ((random.nextFloat() > pMut) ? x.getGen()[i] : (x.getGen()[i] == 0) ? 1 : 0);
        }
        return x;
    }

    public static Chromosome turnOver(Chromosome c, double pMut) {
        IntegerChromosome x = (IntegerChromosome) c;
        Random random = new Random();
        for (int i = 0; i < x.size(); i++) {
            x.getGen()[i] = (int) ((random.nextFloat() > pMut) ? x.getGen()[i] : (x.getMax() - x.getGen()[i] + x.getMin()));
        }
        return x;
    }

    public static Chromosome random(Chromosome c, double pMut) {
        IntegerChromosome x = (IntegerChromosome) c;
        Random random = new Random();
        for (int i = 0; i < x.size(); i++) {
            x.getGen()[i] = (int) ((random.nextFloat() > pMut) ? x.getGen()[i] : (random.nextInt(x.getMax() - x.getMin()) + x.getMin()));
        }
        return x;
    }

    public static Chromosome creep(Chromosome c, double pMut) {
        IntegerChromosome x = (IntegerChromosome) c;
        Random random = new Random();
        for (int i = 0; i < x.size(); i++) {
            x.getGen()[i] = (int) ((random.nextFloat() > pMut) ? x.getGen()[i]
                    : (x.getGen()[i] + (random.nextGaussian() * Math.abs(x.getMax() - x.getMin()))));
        }
        return x;
    }

    public static Chromosome uniform(Chromosome c, double pMut) {
        RealChromosome x = (RealChromosome) c;
        Random random = new Random();
        for (int i = 0; i < x.size(); i++) {
            x.getGen()[i] = (random.nextFloat() > pMut) ? x.getGen()[i] : random.nextDouble();
        }
        return x;
    }

    public static Chromosome gaussian(Chromosome c, double pMut) {
        RealChromosome x = (RealChromosome) c;
        Random random = new Random();
        for (int i = 0; i < x.size(); i++) {
            x.getGen()[i] = (random.nextFloat() > pMut) ? x.getGen()[i] : (x.getGen()[i] + random.nextGaussian());
        }
        return x;
    }

    public static Chromosome swap(Chromosome c, double pMut) {
        PermutationChromosome x = (PermutationChromosome) c;
        Random random = new Random();
        if (random.nextDouble() < pMut) {
            int x1 = random.nextInt(x.size());
            int x2 = random.nextInt(x.size());
            x.swap(x1, x2);
        }
        return x;
    }

    public static Chromosome insert(Chromosome c, double pMut) {
        PermutationChromosome x = (PermutationChromosome) c;
        Random random = new Random();
        if (random.nextDouble() < pMut) {
            int x1 = random.nextInt(x.size());
            int x2 = random.nextInt(x.size());
            if (x1 > x2) {
                int t = x1;
                x1 = x2;
                x2 = t;
            }
            if (x1 == x2) {
                return x;
            }
            int ch = x.getGen()[x2];
            for (int i = x2; i > x1 + 1; i--) {
                x.getGen()[i] = x.getGen()[i - 1];
            }
            x.getGen()[x1 + 1] = ch;
        }
        return x;
    }

    public static Chromosome scramble(Chromosome c, double pMut) {
        PermutationChromosome x = (PermutationChromosome) c;
        Random random = new Random();
        if (random.nextDouble() < pMut) {
            int x1 = random.nextInt(x.size());
            int x2 = random.nextInt(x.size());
            if (x1 > x2) {
                int t = x1;
                x1 = x2;
                x2 = t;
            }
            if (x1 == x2) {
                return x;
            }
            for (int i = 0; i < 4; i++) {
                int r1 = random.nextInt(x2 - x1) + x1;
                int r2 = random.nextInt(x2 - x1) + x1;
                x.swap(r1, r2);
            }
        }
        return x;
    }

    public static Chromosome inversion(Chromosome c, double pMut) {
        PermutationChromosome x = (PermutationChromosome) c;
        Random random = new Random();
        if (random.nextDouble() < pMut) {
            int x1 = random.nextInt(x.size());
            int x2 = random.nextInt(x.size());
            if (x1 > x2) {
                int t = x1;
                x1 = x2;
                x2 = t;
            }
            if (x1 == x2) {
                return x;
            }
            int j = 0;
            for (int i = x1; i < x1 + ((x2 - x1 + 1) / 2); i++) {
                x.swap(i, (x2 - j++));
            }
        }
        return x;
    }

    public static Chromosome nScramble(Chromosome c, double pMut) {
        PermutationChromosome x = (PermutationChromosome) c;
        Random random = new Random();
        int[] mut = new int[x.size()];
        int n = 0;
        for (int i = 0; i < x.size(); i++) {
            if (random.nextFloat() > pMut) {
                mut[n++] = i;
            }
        }

        ArrayList<Integer> l = new ArrayList();
        for (int i = 0; i < n; i++) {
            l.add(mut[i]);
        }
        java.util.Collections.shuffle(l);
        for (int i = 0; i < n; i++) {
            mut[i] = l.get(i);
        }
        if (n > 1) {
            for (int i = 0; i < n - 1; i++) {
                x.swap(mut[i], mut[i + 1]);
            }
        }
        return x;
    }
}
