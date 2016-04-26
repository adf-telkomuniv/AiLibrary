/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

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

    public static void asserts(boolean condition, String message) {
        if (!condition) {
            message = (message == null ? "Assertion failed" : message);
            throw new IllegalStateException(message);
        }
    }

}
