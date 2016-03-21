/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backpro;

import Jama.Matrix;
import java.util.Random;

/**
 *
 * @author dee
 */
public class BPModel2 {

    private Matrix[] weight;
    private Matrix[] weightFlexible;
    private Matrix[] weightFix;
    private Matrix[] aN;
    private Matrix[] dN;
    private double lR;
    private int maxEpoch;
    private double minMAE;
    private int[] hiddenLayer;
    private int[] layer;
    private int numDim;
    private int numWeight;
    private int numOutput;

    public BPModel2(int[] hiddenLayer) {
        this.hiddenLayer = hiddenLayer;
        lR = 0.01;
        maxEpoch = 500;
        minMAE = 0.00001;
    }

    public BPModel2(int[] hiddenLayer, double lR) {
        this(hiddenLayer);
        this.setlR(lR);
    }

    public BPModel2(int[] hiddenLayer, int maxEpoch) {
        this(hiddenLayer);
        this.setMaxEpoch(maxEpoch);
    }

    public BPModel2(int[] hiddenLayer, double lR, int maxEpoch) {
        this(hiddenLayer);
        this.setMaxEpoch(maxEpoch);
        this.setlR(lR);
    }

    public double[] test(double[] input) {
        Matrix cp = new Matrix(input, 1);
        for (int l = 0; l < numWeight; l++) {
            for (int i = 0; i < layer[l + 1]; i++) {
                Matrix wL = weight[l];
                Matrix v;
                v = cp.times(wL.getMatrix(0, wL.getRowDimension(), i, i));
                aN[l].set(1, i, 1 / (1 + Math.exp(v.get(0, 0))));
            }
            cp = aN[l];
        }
        return cp.getColumnPackedCopy();
    }

    public void train(double[][] input, double[][] target, double[][] inputVal, double[][] targetVal) {
        if (input.length != target.length) {
            throw new IllegalStateException("number of target must be same with training");
        }

        if (inputVal == null) {
            inputVal = input.clone();
            targetVal = target.clone();
        }

        if (inputVal.length != targetVal.length) {
            throw new IllegalStateException("number of target validation must be same with input validation");
        }

        if (inputVal[0].length != input[0].length) {
            throw new IllegalStateException("number of training and validation attributes must be same");
        }
        numDim = input[0].length;
        numOutput = target[0].length;

        initializeWeight();

        int epoch = 0;
        double epochTrainMAE = 1000000;
        double totalMAE = 1000000;
        double[] trainMAE = new double[maxEpoch];
        double[] valMAE = new double[maxEpoch];
        Matrix errorData;

        while ((epoch < maxEpoch) && (epochTrainMAE > minMAE)) {

            System.out.println("epoch = " + epoch);
            epochTrainMAE = 0;

            for (int p = 0; p < input.length; p++) {
                Matrix cpTrain = new Matrix(input[p], 1);
                Matrix ctTrain = new Matrix(target[p], 1);

                //fordward computation
                for (int l = 0; l < numWeight; l++) {
                    for (int i = 0; i < layer[l + 1]; i++) {
                        Matrix wL = weight[l];
                        Matrix v;
//                        if (l == 0) {
//                        System.out.println(wL.getRowDimension()+" "+wL.getColumnDimension()+" "+i);
                        v = cpTrain.times(wL.getMatrix(0, wL.getRowDimension() - 1, i, i));
//                        } else {
//                            v = aN[l - 1].times(wL.getMatrix(1, wL.getColumnDimension(), i, i));
//                        }

                        aN[l].set(0, i, 1 / (1 + Math.exp(v.get(0, 0))));
                    }
                    cpTrain = aN[l];
                }
                errorData = ctTrain.minus(cpTrain);
                for (int i = 0; i < errorData.getColumnDimension(); i++) {
                    epochTrainMAE += Math.abs(errorData.get(0, i));
                }

                //backward computation
                for (int l = numWeight; l > 0; l--) {
                    System.out.println("l = "+l+" : "+layer[l]+" "+layer[l-1]);
                    for(int k = 0; k < layer[l]; k++){
                        
                    }
                }
            }
            trainMAE[epoch] = epochTrainMAE / input.length;

            //validate
            double epochValMAE = 0;
            for (int p = 0; p < inputVal.length; p++) {
                Matrix cpVal = new Matrix(inputVal[p], 1);
                Matrix ctVal = new Matrix(targetVal[p], 1);

                //fordward computation
                for (int l = 0; l < numWeight; l++) {
                    for (int i = 0; i < layer[l + 1]; i++) {
                        Matrix wL = weight[l];
                        Matrix v;
                        v = cpVal.times(wL.getMatrix(0, wL.getRowDimension() - 1, i, i));
                        aN[l].set(0, i, 1 / (1 + Math.exp(v.get(0, 0))));
                    }
                    cpVal = aN[l];
                }
                errorData = ctVal.minus(cpVal);
                for (int i = 0; i < errorData.getColumnDimension(); i++) {
                    epochValMAE += Math.abs(errorData.get(0, i));
                }
            }

            valMAE[epoch] = epochValMAE / inputVal.length;
            double newMAE = (trainMAE[epoch] * input.length + valMAE[epoch] * inputVal.length)
                    / (input.length + inputVal.length);
            if (newMAE < totalMAE) {
                totalMAE = newMAE;
                weightFlexible = weight.clone();
            } else {
                weightFix = weight.clone();
            }

            System.out.println("MAE = " + totalMAE);
            epoch++;
        }
    }

    public void initializeWeight() {
        layer = new int[hiddenLayer.length + 2];
        layer[0] = numDim;
        layer[layer.length - 1] = numOutput;
        for (int i = 0; i < hiddenLayer.length; i++) {
            layer[i + 1] = hiddenLayer[i];
        }

        numWeight = layer.length - 1;
        aN = new Matrix[numWeight];
        dN = new Matrix[numWeight];

        weight = new Matrix[numWeight];
        Random r = new Random();
        for (int l = 0; l < numWeight; l++) {
            aN[l] = new Matrix(1, layer[l + 1]);
            dN[l] = new Matrix(1, layer[l + 1]);
            weight[l] = new Matrix(layer[l], layer[l + 1]);
            for (int i = 0; i < layer[l]; i++) {
                for (int j = 0; j < layer[l + 1]; j++) {
                    weight[l].set(i, j, (r.nextDouble() * 2 - 1) / 10);
                }
            }
        }
    }

    public double getMaxEpoch() {
        return maxEpoch;
    }

    public void setMaxEpoch(int maxEpoch) {
        this.maxEpoch = maxEpoch;
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

    public int[] getHiddenLayer() {
        return hiddenLayer;
    }

    public int getNumWeight() {
        return numWeight;
    }

    public int getNumDim() {
        return numDim;
    }
}
