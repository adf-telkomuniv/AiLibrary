/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backpro;

import java.util.Random;

/**
 *
 * @author dee
 */
public class BPModel {

    private double input[][];
    private double layer[][][];
    private double output[];
    private double target[];
    private double lR;
    private int maxEpoch;
    private double minMAE;
    private double[] numNeuron;

    public BPModel(double[] numNeuron) {
        this.numNeuron = numNeuron;
        lR = 0.1;
        maxEpoch = 500;
        minMAE = 0.00001;
    }

    public BPModel(double[] numNeuron, double lR) {
        this(numNeuron);
        this.setlR(lR);
    }

    public BPModel(double[] numNeuron, int maxEpoch) {
        this(numNeuron);
        this.setMaxEpoch(maxEpoch);
    }

    public BPModel(double[] numNeuron, double lR, int maxEpoch) {
        this(numNeuron);
        this.setMaxEpoch(maxEpoch);
        this.setlR(lR);
    }

    public void initializeLayer() {
        Random r = new Random();
        layer = new double[numNeuron.length + 1][][];
        for (int i = 0; i < layer.length; i++) {
            layer[i] = new double [][]            
        }

    }

    public void train(double[][] input, double[] target) {
        output = new double[target.length];
        this.input = input;
        this.target = target;
        initializeLayer();
        
    }

    public double getMaxEpoch() {
        return maxEpoch;
    }

    public void setMaxEpoch(int maxEpoch) {
        this.maxEpoch = maxEpoch;
    }

    public double[] getOutput() {
        return output;
    }

    public double getlR() {
        return lR;
    }

    public void setlR(double lR) {
        this.lR = lR;
    }

    public double getMinMAE() {
        return minMAE;
    }

    public void setMinMAE(double minMAE) {
        this.minMAE = minMAE;
    }

}
