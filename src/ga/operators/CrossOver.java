/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.operators;

import ga.chromosome.Chromosome;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author dee
 */
public class CrossOver {

    public static Chromosome[] crossOver(int crossOverType, Chromosome p1, Chromosome p2, Chromosome pt) {
        switch (crossOverType) {
            case 0:
                return singlePoint(p1, p2);
            case 1:
                return nPoint(p1, p2, 3);
            case 2:
                return uniform(p1, p2, pt);
            case 3:
                return singleArithmetic(p1, p2);
            case 4:
                return simpleArighmetic(p1, p2);
            case 5:
                return wholeArithmetic(p1, p2);
            case 6:
                return order(p1, p2);
            case 7:
                return partiallyMapped(p1, p2);
            case 8:
                return cycle(p1, p2);
            case 9:
                return edge(p1, p2);
        }
        return null;
    }

    private static Chromosome[] createChilds(Chromosome p1, Chromosome p2) {
        Chromosome[] child = new Chromosome[2];
        child[0] = p1.clone();
        child[1] = p2.clone();
        return child;
    }

    // 0
    public static Chromosome[] singlePoint(Chromosome p1, Chromosome p2) {
        Random random = new Random();
        int point = random.nextInt(p1.size() - 1);
        Chromosome[] child = createChilds(p1, p2);
        for (int i = point; i < p1.size(); i++) {
            child[0].setGen(i, p2.getGen(i));
            child[1].setGen(i, p1.getGen(i));
        }
        return child;
    }

    //1
    public static Chromosome[] nPoint(Chromosome p1, Chromosome p2, int n) {
        Random random = new Random();
        int[] point = new int[n];
        for (int i = 0; i < point.length; i++) {
            point[i] = random.nextInt(p1.size() - 1);
        }
        Arrays.sort(point);
        Chromosome[] child = createChilds(p1, p2);
        int p = 0;
        boolean swap = false;
        for (int i = point[0]; i < p1.size(); i++) {
            if (p < n && i == point[p]) {
                p++;
                swap = !swap;
            }
            if (swap) {
                child[0].setGen(i, p2.getGen(i));
                child[1].setGen(i, p1.getGen(i));
            }
        }
        return child;
    }

    // 2
    public static Chromosome[] uniform(Chromosome p1, Chromosome p2, Chromosome pattern) {
        return null;
    }

    // 3
    public static Chromosome[] singleArithmetic(Chromosome p1, Chromosome p2) {
        Chromosome[] child = createChilds(p1, p2);
        Random random = new Random();
        double alpha = random.nextDouble();
        int gen = random.nextInt(p1.size());
        child[0].setGen(gen, alpha * p2.getGen(gen) + (1 - alpha) * p1.getGen(gen));
        child[1].setGen(gen, alpha * p1.getGen(gen) + (1 - alpha) * p2.getGen(gen));
        return child;
    }

    // 4
    public static Chromosome[] simpleArighmetic(Chromosome p1, Chromosome p2) {
        Chromosome[] child = createChilds(p1, p2);
        Random random = new Random();
        double alpha = random.nextDouble();
        int gen = random.nextInt(p1.size());
        for (int i = gen; i < p1.size(); i++) {
            child[0].setGen(i, alpha * p2.getGen(i) + (1 - alpha) * p1.getGen(i));
            child[1].setGen(i, alpha * p1.getGen(i) + (1 - alpha) * p2.getGen(i));
        }
        return child;
    }

    // 5
    public static Chromosome[] wholeArithmetic(Chromosome p1, Chromosome p2) {
        Chromosome[] child = createChilds(p1, p2);
        Random random = new Random();
        double alpha = random.nextDouble();
        for (int i = 0; i < p1.size(); i++) {
            child[0].setGen(i, alpha * p2.getGen(i) + (1 - alpha) * p1.getGen(i));
            child[1].setGen(i, alpha * p1.getGen(i) + (1 - alpha) * p2.getGen(i));
        }
        return child;
    }

    public static Chromosome[] order(Chromosome p1, Chromosome p2) {
        Random random = new Random();
        int[] point = {random.nextInt(p1.size() - 1), random.nextInt(p1.size() - 1)};
        Chromosome[] child = createChilds(p1, p2);
        Arrays.sort(point);
        java.util.HashSet<Double> l1 = new java.util.HashSet();
        java.util.HashSet<Double> l2 = new java.util.HashSet();
        for (int i = point[0]; i < point[1]; i++) {
            l1.add(p1.getGen(i));
            l2.add(p2.getGen(i));
        }
        int i1 = 0, i2 = 0;
        for (int i = 0; i < p1.size(); i++) {
            if (l1.add(p2.getGen(i))) {
                if (i1 == point[0]) {
                    i1 = point[1];
                }
                child[0].setGen(i1, p2.getGen(i));
                i1++;
            }
            if (l2.add(p1.getGen(i))) {
                if (i2 == point[0]) {
                    i2 = point[1];
                }
                child[1].setGen(i2, p1.getGen(i));
                i2++;
            }
        }
        return child;
    }

    public static Chromosome[] partiallyMapped(Chromosome p1, Chromosome p2) {
        Random random = new Random();
        int[] point = {random.nextInt(p1.size() - 1), random.nextInt(p1.size() - 1)};
        Chromosome[] child = createChilds(p1, p2);
        Arrays.sort(point);
        java.util.HashSet<Integer> l1 = new java.util.HashSet();
        java.util.HashSet<Integer> l2 = new java.util.HashSet();
        for (int i = point[0]; i < point[1]; i++) {
            int j = 0;
            boolean found = false;
            while (j < p2.size() && !found) {
                if (p2.getGen(j) == p1.getGen(i)) {
                    found = true;
                } else {
                    j++;
                }
            }
            child[1].swap(i, j);
            l2.add(i);
            l2.add(j);

            j = 0;
            found = false;
            while (j < p1.size() && !found) {
                if (p1.getGen(j) == p2.getGen(i)) {
                    found = true;
                } else {
                    j++;
                }
            }
            child[0].swap(i, j);
            l1.add(i);
            l1.add(j);
        }
        for (int i = 0; i < p1.size(); i++) {
            if (l1.add(i)) {
                child[0].setGen(i, p1.getGen(i));
            }
            if (l2.add(i)) {
                child[1].setGen(i, p2.getGen(i));
            }
        }
        return child;
    }

    public static Chromosome[] cycle(Chromosome p1, Chromosome p2) {        
        return null;
    }

    public static Chromosome[] edge(Chromosome p1, Chromosome p2) {
        return null;
    }

}
