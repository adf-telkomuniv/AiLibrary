/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import ga.ChromosomeEvaluator;
import ga.chromosome.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author dee
 */
public class Evaluator implements ChromosomeEvaluator {

    public double[][] readFile() {
        double[][] x = new double[2][2];
        String fileName = "test.tsp";
        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            list = stream.collect(Collectors.toList());
            x = new double[list.size()][2];
            for (int i = 0; i < x.length; i++) {
                x[i][0] = Double.parseDouble(list.get(i).split(" ")[2]);
                x[i][1] = Double.parseDouble(list.get(i).split(" ")[3]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return x;
    }

    public double getDistance(double a[], double b[]) {
        double x = Math.abs(a[0] - b[0]);
        double y = Math.abs(a[1] - b[1]);
        return Math.sqrt(x * x + y * y);
    }

    public double evaluate(Chromosome chrsm) {
        double x[][] = readFile();
        int cost[] = {0, 19, 21, 6, 19, 7, 12, 16, 6, 16, 8, 14, 21, 16, 3, 22, 18, 19, 1, 24, 8, 12, 4, 8, 24, 24, 2, 20, 15, 2, 14, 9};
        PermutationChromosome c = (PermutationChromosome) chrsm;
        int demand = 0;
        int path[] = c.getGen();
        double total = 0;
        double a[] = x[0];
        double b[] = x[path[0]];
        double q = getDistance(a, b);
        demand += cost[path[0]];
        total += q;
        for (int i = 0; i < path.length - 1; i++) {
            if (demand + cost[path[i + 1]] > 100) {
                demand = cost[path[i + 1]];
                a = x[path[i]];
                b = x[0];
                q = getDistance(a, b);
                total += q;
                a = x[0];
                b = x[path[i + 1]];
                q = getDistance(a, b);
                total += q;
            } else {
                demand += cost[path[i + 1]];
                a = x[path[i]];
                b = x[path[i + 1]];
                q = getDistance(a, b);
                total += q;
            }
        }
        a = x[path[path.length - 1]];
        b = x[0];
        q = getDistance(a, b);
        total += q;
        return total;
    }

    public double evaluate2(Chromosome chrsm) {
        double x[][] = readFile();
        int cost[] = {0, 19, 21, 6, 19, 7, 12, 16, 6, 16, 8, 14, 21, 16, 3, 22, 18, 19, 1, 24, 8, 12, 4, 8, 24, 24, 2, 20, 15, 2, 14, 9};
        PermutationChromosome c = (PermutationChromosome) chrsm;
        int demand = 0;
        int path[] = c.getGen();
        double total = 0;
        double a[] = x[0];
        double b[] = x[path[0]];
//        System.out.print("0-" + path[0] + " ");
        System.out.print("0 " + path[0] + " ");
        double q = getDistance(a, b);
        demand += cost[path[0]];
        total += q;
        for (int i = 0; i < path.length - 1; i++) {
            if (demand + cost[path[i + 1]] > 100) {
                demand = cost[path[i + 1]];
                a = x[path[i]];
                b = x[0];
                q = getDistance(a, b);
                total += q;
                System.out.print("0 ");
                a = x[0];
                b = x[path[i + 1]];
                q = getDistance(a, b);
                total += q;
                System.out.print(path[i + 1] + " ");
            } else {
                demand += cost[path[i + 1]];
                a = x[path[i]];
                b = x[path[i + 1]];
                q = getDistance(a, b);
                total += q;
                System.out.print(path[i + 1] + " ");
            }
        }
        a = x[path[path.length - 1]];
        b = x[0];
        q = getDistance(a, b);
        total += q;
        System.out.print("0 ");
        System.out.println("");
        return total;
    }

    @Override
    public double evaluateFitness(Chromosome chrmsm) {
        double total = evaluate(chrmsm);

        double fitness = 1 / (total);// + 0.01);

        return fitness;
    }

}
