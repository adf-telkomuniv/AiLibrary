/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backpro;

import Jama.Matrix;
import misc.FileIO;
import sun.misc.Signal;

/**
 *
 * @author dee
 */
public class Driver {

    public static void main(String[] args) {

        try {
            int[] numNeuron = {7,7};
            BPModel2 bp = new BPModel2(numNeuron, 5000);

            double[][] input = FileIO.readFileDouble("train.txt");
            double[][] target = FileIO.readFileDouble("test2.txt");

//        double[][] input = {
//            {1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1},
//            {1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1},
//            {1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1},
//            {1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
//            {1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1},
//            {1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1},
//            {1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
//            {1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1}
//        };
//            double[][] target = {{1}, {0}, {1}, {0}, {1}, {0}, {1}, {0}};
            bp.train(input, target, input, target);

            double[] test;
            double [][] output = target.clone();
            for (int i = 0; i < input.length; i++) {
                test = bp.test(input[i]);
                output[i] = test;
                for (int j = 0; j < test.length; j++) {
                    System.out.print(test[j] + " ");
                }
                System.out.println("");
            }
            
            System.out.println(bp.accuracy(output, target)/output.length*100+"%");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
