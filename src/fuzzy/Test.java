/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.Arrays;

/**
 *
 * @author dee
 */
public class Test {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(test(i) + " " + test2(i));

        }
    }

    public static double test(double inp) {
        double a = 3.0;
        double c = 7.0;
        double b = (a + c) / 2;
        if (inp < b && inp >= a) {
            return 2 * Math.pow((inp - a) / (c - a), 2);
        }
        if (inp >= b && inp <= c) {
            return 1 - 2 * Math.pow((inp - c) / (c - a), 2);
        }
        return 0;
    }

    public static double test2(double input) {
        double a = 3.0;
        double c = 7.0;
        double b = (a + c) / 2;
        if (input < b && input >= a) {
            return 1-2 * Math.pow((input - a) / (c - a), 2);
        }
        if (input >= b && input <= c) {
            return 2 * Math.pow((input - c) / (c - a), 2);
        }
        return 0;
    }

}
