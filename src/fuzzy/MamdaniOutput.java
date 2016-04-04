/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author dee
 */
public class MamdaniOutput extends Input implements OutputModel {

    private int numPoint;

    public MamdaniOutput(int numLinguistic, int numPoint) {
        super(numLinguistic);
        this.numPoint = numPoint;
    }

    @Override
    public double defuzzy(FuzzyValue[] fuzzyOutput) {
        Map<Integer, Double> g = new HashMap();
        for (int i = 0; i < membership.length; i++) {
            for (int j = 0; j < fuzzyOutput.length; j++) {
                if (membership[i].getLinguistic().equals(fuzzyOutput[j].getLinguistic())) {
                    g.put(i, fuzzyOutput[j].getFuzzyValue());
                    break;
                }
            }
        }

        Integer[] gp = g.keySet().toArray(new Integer[0]);
        double min = membership[gp[0]].getPosition()[0];
        double max = membership[gp[gp.length - 1]].getPosition()[3];
        for (int i = 0; i < gp.length; i++) {
            min = Math.min(min, membership[gp[i]].getPosition()[0]);
            max = Math.max(max, membership[gp[i]].getPosition()[3]);
        }
        double dx = (max - min) / numPoint;
        double dvd = 0;
        for (int i = 0; i < gp.length; i++) {
            dvd += (fuzzyOutput[i].getFuzzyValue() * numPoint / gp.length);
        }
        double result = 0;
//        double st = 0;
//        double en = numPoint / gp.length;
        double pos = min;

        for (int i = 0; i < numPoint; i++) {
//            System.out.print(" " + pos);
            for (int j = 0; j < gp.length; j++) {
                if (membership[gp[j]].isInside(pos)) {
                    result += pos * g.get(gp[j]);
//                    System.out.println(", - " + g.get(gp[j]));
                    break;
                }
            }
            pos += dx;
        }

//        for (double j = st; j < en; j++) {
//            for (int i = 0; i < gp.length; i++) {
//                if (membership[gp[i]].isInside(pos)) {
//
//                }
//                result += pos * fuzzyOutput[i].getFuzzyValue();
//                System.out.print(" " + pos);
//                pos += dx;
//            }
//            System.out.println(", - " + fuzzyOutput[i].getFuzzyValue());
//            st = en;
//            en += numPoint / gp.length;
//        }
//        Random r = new Random();
//        double[] rnd = new double[numPoint];
//        System.out.println("rdn = "+rnd.length);
//        for (int i = 0; i < rnd.length; i++) {
//            rnd[i] = min + (max - min) * r.nextDouble();
//        }
//        double[] inference = new double[gp.length];
//        double dvd = 0;
//        for (int i = 0; i < rnd.length; i++) {
//            for (int j = 0; j < gp.length; j++) {
//                if (membership[gp[j]].isInside(rnd[i])) {
//                    dvd += fuzzyOutput[j].getFuzzyValue();
//                    System.out.print(" " + fuzzyOutput[j].getFuzzyValue());
//                    inference[j] += rnd[i]*fuzzyOutput[j].getFuzzyValue();
//                    break;
//                }
//            }
//        }
//        System.out.println("");
//        double result = 0;
//        for (int i = 0; i < inference.length; i++) {
//            result += inference[i] * fuzzyOutput[i].getFuzzyValue();
//        }
//        System.out.println("result = " + result + ", dvd = " + dvd);
        return result / dvd;
    }

}
