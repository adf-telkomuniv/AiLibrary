/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import fuzzy.Rules;

/**
 *
 * @author dee
 */
public class NewClass1 {

    public static void main(String[] args) {
        Rules ruleHigh = new Rules();
        Rules ruleMiddle = new Rules();
        Rules ruleLow = new Rules();
        Rules ruleVeryLow = new Rules();

        String[] out;
        String[] cl = {"very low", "low", "middle", "high"};
        String[] yy;
        String[][] s;
        out = new String[]{cl[0], cl[1], cl[2], cl[3]};

        yy = new String[]{cl[2], cl[2], cl[1], cl[0]};
        s = new String[][]{yy, yy, yy};
        ruleVeryLow.generate(s, out.clone());

        yy = new String[]{cl[2], cl[2], cl[0], cl[1]};
        s = new String[][]{yy, yy, yy};
        ruleLow.generate(s, out.clone());

        yy = new String[]{cl[1], cl[2], cl[3], cl[2]};
        s = new String[][]{yy, yy, yy};
        ruleMiddle.generate(s, out.clone());

        yy = new String[]{cl[1], cl[1], cl[2], cl[3]};
        s = new String[][]{yy, yy, yy};
        ruleHigh.generate(s, out.clone());
//        s = new String[][]{cl, cl, cl};
//        
//        
//        out = new String[]{cl[3], cl[2], cl[0], cl[0]};
//        ruleVeryLow.generate(s, out.clone());
//        out = new String[]{cl[2], cl[3], cl[0], cl[0]};
//        ruleLow.generate(s, out.clone());
//        out = new String[]{cl[0], cl[0], cl[3], cl[2]};
//        ruleMiddle.generate(s, out.clone());
//        out = new String[]{cl[0], cl[0], cl[2], cl[3]};
//        ruleHigh.generate(s, out.clone());

        System.out.println("very low");
        ruleVeryLow.print();
        System.out.println("---------------------");
        System.out.println("low");
        ruleLow.print();
        System.out.println("---------------------");
        System.out.println("middle");
        ruleMiddle.print();
        System.out.println("---------------------");
        System.out.println("high");
        ruleHigh.print();
    }

}
