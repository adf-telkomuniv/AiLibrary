/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author dee
 */
public class Utils {

    private static Random r = new Random();

    public static double gaussRandom() {
        return r.nextGaussian();
    }

    public static int randi(int a, int b) {
        return r.nextInt(b - a) + a;
    }

    public static double randf(double a, double b) {
        return r.nextDouble() * (b - a) + a;
    }

    public static double randn(double mu, double std) {
        return mu + gaussRandom() * std;
    }

    public boolean arrContains(double[] arr, double x) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                return true;
            }
        }
        return false;
    }

    public boolean listContains(List<Double> lst, double x) {
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i) == x) {
                return true;
            }
        }
        return false;
    }

    public double[] arrUnique(double[] arr) {
        List<Double> temp = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
            if (!listContains(temp, arr[i])) {
                temp.add(arr[i]);
            }
        }
        double[] re = new double[temp.size()];
        for (int i = 0; i < re.length; i++) {
            re[i] = temp.get(i);
        }
        return re;
    }

    public Options maxmin(double[] w) {
        Options re = null;
        if (w.length == 0) {
            return re;
        }
        double maxv = w[0];
        double minv = w[0];
        int maxi = 0;
        int mini = 0;
        for (int i = 0; i < w.length; i++) {
            if (w[i] > maxv) {
                maxv = w[i];
                maxi = i;
            }
            if (w[i] < minv) {
                minv = w[i];
                mini = i;
            }
        }
        re = new Options();
        re.put("maxi", maxi);
        re.put("maxv", maxv);
        re.put("mini", mini);
        re.put("minv", minv);
        re.put("dv", maxv - minv);
        return re;
    }

    public double[] randperm(int n) {
        int j = 0;
        double temp;
        double[] result = new double[n];
        for (int k = 0; k < result.length; k++) {
            result[k] = k;
        }
        for (int i = n; i > 0; i--) {
            j = (int) Math.floor(Math.random() * (i + 1));
            temp = result[i];
            result[i] = result[j];
            result[j] = temp;
        }
        return result;
    }

    public double weightedSample(double[] lst, double[] probs) {
        double p = randf(0, 1.0);
        double cumprob = 0.0;
        for (int k = 0; k < lst.length; k++) {
            cumprob += probs[k];
            if (p < cumprob) {
                return lst[k];
            }
        }
        return lst[lst.length - 1];
    }

    public static void asserts(boolean condition, String message) {
        if (!condition) {
            message = (message == null ? "Assertion failed" : message);
            throw new IllegalStateException(message);
        }
    }

}
