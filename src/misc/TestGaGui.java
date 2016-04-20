/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import ga.GaSettings;
import ga.GeneticAlgorithm;
import ga.OperatorOptions;

/**
 *
 * @author dee
 */
public class TestGaGui {

    public static void main(String[] args) {
//        AiGaGui a = new AiGaGui();
        try {
//            GaSettings settings = new GaSettings(24, 100, 300, 10, 90);
//            OperatorOptions operations = new OperatorOptions(1, 1, 1);
            GaSettings settings = new GaSettings(24, 100, 300);
            OperatorOptions operations = new OperatorOptions(2, 3, 4);
            GeneticAlgorithm ga = new GeneticAlgorithm(settings, operations);
            settings.setThresholdFitness(1000);
            GAFuzzyEvaluator ev = new GAFuzzyEvaluator();
//            GA ev = new GA();
            ga.optimize(ev);
            System.out.println(ga.getBestFitness());
            System.out.println(ga.getBestIndv());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
