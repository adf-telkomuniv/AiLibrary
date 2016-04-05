/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import fuzzy.Fuzzy;
import fuzzy.FuzzyValue;
import fuzzy.Input;
import fuzzy.MamdaniOutput;
import fuzzy.Membership;
import fuzzy.Rule;
import fuzzy.Rules;
import fuzzy.SugenoOutput;
import java.util.Arrays;

/**
 *
 * @author dee
 */
public class TestFuzzy {

//    public static void mins(int[] inp, int x, int[] inp2) {
//        if (inp2[x] > 0) {
//            inp2[x]--;
//        } else {
//            inp2[x] = inp[x];
//            if (x > 0) {
//                mins(inp, x - 1, inp2);
//            }
//        }
//    }
    public static void main(String[] args) {

        Fuzzy f = new Fuzzy();
        Input suhu = new Input(5);
        suhu.setMembership(0, new Membership("Cold", new double[]{-100, -10, 0, 3}));
        suhu.setMembership(1, new Membership("Cool", new double[]{0, 3, 12, 15}));
        suhu.setMembership(2, new Membership("Normal", new double[]{12, 15, 24, 27}));
        suhu.setMembership(3, new Membership("Warm", new double[]{24, 27, 36, 39}));
        suhu.setMembership(4, new Membership("Hot", new double[]{36, 39, 50, 100}));
        f.addInput(suhu);

        Input kelembaban = new Input(3);
        kelembaban.setMembership(0, new Membership("Dry", new double[]{-100, 0, 10, 20}));
        kelembaban.setMembership(1, new Membership("Moist", new double[]{10, 20, 40, 50}));
        kelembaban.setMembership(2, new Membership("Wet", new double[]{40, 50, 70, 100}));
        f.addInput(kelembaban);

        Rules rules = new Rules();
        String[][] s = new String[2][];
        s[0] = new String[]{"Cold", "Cool", "Normal", "Warm", "Hot"};
        s[1] = new String[]{"Dry", "Moist", "Wet"};
        String[] out = new String[]{"Short", "Medium", "Long"};
        rules.generate(s, out);
        Rule r = rules.getRule(9);
        r.setOutputLing("Long");
        rules.setRule(9, r);
        System.out.println(rules);
        System.out.println("===============");
        f.setRules(rules);

        MamdaniOutput out1 = new MamdaniOutput(3, 10);
        out1.setMembership(0, new Membership("Short", new double[]{0, 0, 20, 28}));
        out1.setMembership(1, new Membership("Medium", new double[]{20, 28, 40, 48}));
        out1.setMembership(2, new Membership("Long", new double[]{40, 48, 90, 90}));
        f.setOutput(out1);
//
        double[] inp = new double[]{37, 12};
        FuzzyValue[][] fuzzyInput = f.fuzzyfy(inp);
        for (int i = 0; i < fuzzyInput.length; i++) {
            for (int j = 0; j < fuzzyInput[i].length; j++) {
                FuzzyValue ff = fuzzyInput[i][j];
                System.out.print(ff.getLinguistic() + " " + ff.getFuzzyValue() + " , ");
            }
            System.out.println("");
        }

        FuzzyValue[] fuzzyOutput = f.inference(fuzzyInput, rules);
        System.out.println("--------------");
        for (int i = 0; i < fuzzyOutput.length; i++) {
            System.out.println(fuzzyOutput[i].getLinguistic() + " " + fuzzyOutput[i].getFuzzyValue());
        }
        System.out.println("----------");
        double x = f.defuzzify(fuzzyOutput, out1);
        System.out.println("x = " + x);
        System.out.println("==============");
        double x2 = f.processFuzzy(inp);
        System.out.println("x = " + x2);

        SugenoOutput out2 = new SugenoOutput(new double[]{20, 40, 60},
                new String[]{"Short", "Medium", "Long"});
        f.setOutput(out2);
        x2 = f.processFuzzy(inp);
        System.out.println("x = " + x2);

//        System.out.println("aaa");
//        String ss = r.toString();
//        System.out.println(ss);
//        r.print();
//        int[] inp = new int[]{3, 3, 4, 3};
//        int max = 1;
//        for (int i = 0; i < inp.length; i++) {
//            max *= inp[i];
//        }
//        for (int i = 0; i < inp.length; i++) {
//            inp[i]--;
//        }
//        int[] inp2 = inp.clone();
//        System.out.println("m=" + max);
//        for (int i = 0; i < max; i++) {
//            System.out.print(i + "-");
//            for (int j = 0; j < inp2.length; j++) {
//                System.out.print(inp2[j] + " ");
//            }
//            System.out.println("");
//            mins(inp, inp2.length - 1, inp2);
//        }
//        for (int i = 0; i < 10; i++) {
//            System.out.println(test(i) + " " + test2(i));
//
//        }
    }

//    public static double test(double inp) {
//        double a = 3.0;
//        double c = 7.0;
//        double b = (a + c) / 2;
//        if (inp < b && inp >= a) {
//            return 2 * Math.pow((inp - a) / (c - a), 2);
//        }
//        if (inp >= b && inp <= c) {
//            return 1 - 2 * Math.pow((inp - c) / (c - a), 2);
//        }
//        return 0;
//    }
//
//    public static double test2(double input) {
//        double a = 3.0;
//        double c = 7.0;
//        double b = (a + c) / 2;
//        if (input < b && input >= a) {
//            return 1 - 2 * Math.pow((input - a) / (c - a), 2);
//        }
//        if (input >= b && input <= c) {
//            return 2 * Math.pow((input - c) / (c - a), 2);
//        }
//        return 0;
//    }
}
