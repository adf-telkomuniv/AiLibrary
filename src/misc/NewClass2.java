/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import ann.BackProp;

/**
 *
 * @author dee
 */
public class NewClass2 {
    public static void main(String[] args) {
//        double a = 2558;
//        double b = 2665 ;
//        double c = a / b * 100;
//        System.out.println("c = "+c);
//        
        try {
            int[] hiddenNeuron = {7};
            BackProp model = new BackProp(hiddenNeuron, 50);
            model.setlR(0.01);

            double[][] input = FileIO.readFileDouble("trainA.txt");
            double[][] target = FileIO.readFileDouble("testA.txt");
            model.train(input, target, input, target);

            double[] test;
            double[][] output = target.clone();
            for (int i = 0; i < input.length; i++) {
                //
                test = model.test(input[i]);
                output[i] = test;
//                System.out.print(i+" - ");
//                for (int j = 0; j < test.length; j++) {
//                    System.out.print(test[j] + " ");
//                }
//                System.out.println(target[i][0]+"");
            }

            System.out.println(model.accuracy(output, target)  + "%");
            System.out.println("==================================");
            for (int i = 0; i < input.length; i++) {
                test = model.testFlexible(input[i]);
                output[i] = test;
//                for (int j = 0; j < test.length; j++) {
//                    System.out.print(test[j] + " ");
//                }
//                System.out.println("");
            }
//
            System.out.println(model.accuracy(output, target) * 100 + "%");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
