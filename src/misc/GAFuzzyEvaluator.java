package misc;

import fuzzy.Fuzzy;
import fuzzy.Input;
import fuzzy.MamdaniOutput;
import fuzzy.Rules;
import ga.ChromosomeEvaluator;
import ga.chromosome.Chromosome;
import ga.chromosome.IntegerChromosome;
import ga.chromosome.RealChromosome;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dee
 */
public class GAFuzzyEvaluator implements ChromosomeEvaluator {

    private double[][] data;
    String[] out;
    String[] cl = {"very low", "low", "middle", "high"};
    String[][] s = new String[3][];

    public GAFuzzyEvaluator() {
        data = readFile();
        s[0] = cl;
        s[1] = cl;
        s[2] = cl;
    }

    public double[][] readFile() {
        double[][] x = new double[2][2];
//        String fileName = "data\\dataFuzzy.txt";
        String fileName = "C:\\Users\\dee\\Documents\\GitHub\\TestLibAI\\data\\dataFuzzy.txt";
        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            list = stream.collect(Collectors.toList());
            x = new double[list.size()][list.get(0).split(" ").length];
            for (int i = 0; i < x.length; i++) {
                String[] s = list.get(i).split(" ");
                for (int j = 0; j < x[i].length; j++) {
                    x[i][j] = Double.parseDouble(s[j]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return x;
    }

    @Override
    public double evaluateFitness(Chromosome chrmsm) {
        MamdaniOutput classVeryLow = new MamdaniOutput(20);
        MamdaniOutput classLow = new MamdaniOutput(20);
        MamdaniOutput classMiddle = new MamdaniOutput(20);
        MamdaniOutput classHigh = new MamdaniOutput(20);
        Rules ruleHigh = new Rules();
        Rules ruleMiddle = new Rules();
        Rules ruleLow = new Rules();
        Rules ruleVeryLow = new Rules();

        double fitness = 0;
        RealChromosome c = (RealChromosome) chrmsm;
        double[] ch = c.getGen();
//        for (int i = 0; i < ch.length; i++) {
//            ch[i] = (ch[i] < 0.1 ? ch[i] + 0.1 : ch[i]);//*0.7+0.15;
//            ch[i] = (ch[i] > 0.9 ? ch[i] - 0.1 : ch[i]);//*0.7+0.15;
//        }

//        IntegerChromosome c = (IntegerChromosome) chrmsm;
        int numData = data.length;
//        int[] chx = c.getGen();
//        double[] ch = new double[chx.length];
//        for (int i = 0; i < ch.length; i++) {
//            ch[i] = ch[i] / 100;
//        }
        double[][] point = new double[16][4];

        int l = 0;
        point[0] = new double[]{0, 0, ch[l], ch[l + 1]};
        point[1] = new double[]{ch[l++], ch[l++], ch[l], ch[l + 1]};
        point[2] = new double[]{ch[l++], ch[l++], ch[l], ch[l + 1]};
        point[3] = new double[]{ch[l++], ch[l++], 1, 1};

        point[4] = new double[]{0, 0, ch[l], ch[l + 1]};
        point[5] = new double[]{ch[l++], ch[l++], ch[l], ch[l + 1]};
        point[6] = new double[]{ch[l++], ch[l++], ch[l], ch[l + 1]};
        point[7] = new double[]{ch[l++], ch[l++], 1, 1};

        point[8] = new double[]{0, 0, ch[l], ch[l + 1]};
        point[9] = new double[]{ch[l++], ch[l++], ch[l], ch[l + 1]};
        point[10] = new double[]{ch[l++], ch[l++], ch[l], ch[l + 1]};
        point[11] = new double[]{ch[l++], ch[l++], 1, 1};

        point[12] = new double[]{0, 0, ch[l], ch[l + 1]};
        point[13] = new double[]{ch[l++], ch[l++], ch[l], ch[l + 1]};
        point[14] = new double[]{ch[l++], ch[l++], ch[l], ch[l + 1]};
        point[15] = new double[]{ch[l++], ch[l++], 1, 1};

        for (int j = 0; j < 9; j++) {
            Arrays.sort(point[j]);
        }

        l = 0;
        Input in1 = new Input();
        in1.addMembership("very low", point[l++]);
        in1.addMembership("low", point[l++]);
        in1.addMembership("middle", point[l++]);
        in1.addMembership("high", point[l++]);

        Input in2 = new Input();
        in2.addMembership("very low", point[l++]);
        in2.addMembership("low", point[l++]);
        in2.addMembership("middle", point[l++]);
        in2.addMembership("high", point[l++]);

        Input in3 = new Input();
        in3.addMembership("very low", point[l++]);
        in3.addMembership("low", point[l++]);
        in3.addMembership("middle", point[l++]);
        in3.addMembership("high", point[l++]);

        classVeryLow.addMembership(cl[0], point[12]);
        classVeryLow.addMembership(cl[1], point[13]);
        classVeryLow.addMembership(cl[2], point[14]);
        classVeryLow.addMembership(cl[3], point[15]);

        classLow.addMembership(cl[0], point[12]);
        classLow.addMembership(cl[1], point[13]);
        classLow.addMembership(cl[2], point[14]);
        classLow.addMembership(cl[3], point[15]);

        classMiddle.addMembership(cl[0], point[12]);
        classMiddle.addMembership(cl[1], point[13]);
        classMiddle.addMembership(cl[2], point[14]);
        classMiddle.addMembership(cl[3], point[15]);

        classHigh.addMembership(cl[0], point[12]);
        classHigh.addMembership(cl[1], point[13]);
        classHigh.addMembership(cl[2], point[14]);
        classHigh.addMembership(cl[3], point[15]);

        String[] yy;
        String[][] s;
//        out = new String[]{cl[0], cl[1], cl[2], cl[3]};
//
//        yy = new String[]{cl[3], cl[2], cl[1], cl[0]};
//        s = new String[][]{yy, yy, yy};
//        ruleVeryLow.generate(s, out.clone());
//
//        yy = new String[]{cl[3], cl[2], cl[0], cl[1]};
//        s = new String[][]{yy, yy, yy};
//        ruleLow.generate(s, out.clone());
//
//        yy = new String[]{cl[0], cl[1], cl[3], cl[2]};
//        s = new String[][]{yy, yy, yy};
//        ruleMiddle.generate(s, out.clone());
//
//        yy = new String[]{cl[0], cl[1], cl[2], cl[3]};
//        s = new String[][]{yy, yy, yy};
//        ruleHigh.generate(s, out.clone());

        s = new String[][]{cl, cl, cl};

        out = new String[]{cl[3], cl[3], cl[1], cl[0]};
        ruleVeryLow.generate(s, out.clone());
        out = new String[]{cl[1], cl[3], cl[3], cl[0]};
        ruleLow.generate(s, out.clone());
        out = new String[]{cl[0], cl[3], cl[3], cl[1]};
        ruleMiddle.generate(s, out.clone());
        out = new String[]{cl[0], cl[1], cl[3], cl[3]};
        ruleHigh.generate(s, out.clone());

        Fuzzy f = new Fuzzy();
        f.addInput(in1);
        f.addInput(in2);
        f.addInput(in3);
        for (int i = 0; i < numData; i++) {

            double[] output = new double[4];
            double[] input = {data[i][0], data[i][1], data[i][2]};
            double target = data[i][3];

            f.setOutput(classVeryLow);
            f.setRules(ruleVeryLow);
            output[0] = f.processFuzzy(input);
            f.setOutput(classLow);
            f.setRules(ruleLow);
            output[1] = f.processFuzzy(input);
            f.setOutput(classMiddle);
            f.setRules(ruleMiddle);
            output[2] = f.processFuzzy(input);
            f.setOutput(classHigh);
            f.setRules(ruleHigh);
            output[3] = f.processFuzzy(input);

            int mx = 0;
            for (int j = 0; j < output.length; j++) {
                if (output[mx] > output[j]) {
                    mx = j;
                }
            }
            if (mx == target) {
                fitness++;
            }
        }
        return fitness;
    }
}
