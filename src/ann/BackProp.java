/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

import Jama.Matrix;
import java.util.Random;

/**
 *
 * @author dee
 */
public class BackProp {

    private Matrix[] weight;
    private Matrix[] weightFlexible;
//    private Matrix[] weightFix;
    private Matrix[] aN;
    private Matrix[] dN;
    private Matrix threshold;
    private double lR;
    private int maxEpoch;
    private double minMAE;
    private int[] hiddenLayer;
    private int[] layer;
    private int numDim;
    private int numWeight;
    private int numOutput;

    public BackProp(int[] hiddenLayer) {
        this.hiddenLayer = hiddenLayer;
        lR = 0.1;
        maxEpoch = 500;
        minMAE = 0.00001;
    }

    public BackProp(int[] hiddenLayer, double lR) {
        this(hiddenLayer);
        this.setlR(lR);
    }

    public BackProp(int[] hiddenLayer, int maxEpoch) {
        this(hiddenLayer);
        this.setMaxEpoch(maxEpoch);
    }

    public BackProp(int[] hiddenLayer, double lR, int maxEpoch) {
        this(hiddenLayer);
        this.setMaxEpoch(maxEpoch);
        this.setlR(lR);
    }

    public double[] test(double[] input) {
        return test(input, 1);
    }

    public double[] test(double[] input, int opt) {
        Matrix cp = new Matrix(input, 1);
        for (int l = 0; l < numWeight; l++) {
            Matrix x = new Matrix(1, layer[l + 1]);
            for (int i = 0; i < layer[l + 1]; i++) {
                Matrix wL = weight[l];
                Matrix v;
                v = cp.times(wL.getMatrix(0, wL.getRowDimension() - 1, i, i));
                x.set(0, i, 1 / (1 + Math.exp(v.get(0, 0))));
            }
            cp = x;
        }

        if (opt == 0) {
            return cp.getColumnPackedCopy();
        } else {
            double[] out = cp.getColumnPackedCopy();
            for (int i = 0; i < out.length; i++) {
                if (out[i] >= threshold.get(0, i)) {
                    out[i] = 1;
                } else {
                    out[i] = 0;
                }
            }
            return out;
        }
    }

    public double[] testFlexible(double[] input) {
        return testFlexible(input, 1);
    }

    public double[] testFlexible(double[] input, int opt) {
        Matrix cp = new Matrix(input, 1);
        for (int l = 0; l < numWeight; l++) {
            Matrix x = new Matrix(1, layer[l + 1]);
            for (int i = 0; i < layer[l + 1]; i++) {
                Matrix wL = weightFlexible[l];
                Matrix v;
                v = cp.times(wL.getMatrix(0, wL.getRowDimension() - 1, i, i));
                x.set(0, i, 1 / (1 + Math.exp(v.get(0, 0))));
            }
            cp = x;
        }

        if (opt == 0) {
            return cp.getColumnPackedCopy();
        } else {
            double[] out = cp.getColumnPackedCopy();
            for (int i = 0; i < out.length; i++) {
                if (out[i] >= threshold.get(0, i)) {
                    out[i] = 1;
                } else {
                    out[i] = 0;
                }
            }
            return out;
        }
    }

    public double accuracy(double[][] output, double[][] target) {
        int x = 0;
        for (int i = 0; i < target.length; i++) {
            boolean dt = true;
            for (int j = 0; j < target[i].length; j++) {
                if (target[i][j] != output[i][j]) {
                    dt = false;
                    break;
                }
            }
            if (dt) {
                x++;
            }
        }
        return x / output.length;
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
        threshold = new Matrix(1, numOutput);

        initializeWeight();

        int epoch = 0;
        double epochTrainMAE = 1000000;
        double totalMAE = 1000000;
        double flexibleMAE;
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
                        v = cpTrain.times(wL.getMatrix(0, wL.getRowDimension() - 1, i, i));
                        aN[l].set(0, i, 1 / (1 + Math.exp(-v.get(0, 0))));
                    }
                    cpTrain = aN[l];
                }
                errorData = ctTrain.minus(cpTrain);
                cpTrain = new Matrix(input[p], 1);
                for (int i = 0; i < errorData.getColumnDimension(); i++) {
                    epochTrainMAE += Math.abs(errorData.get(0, i));
                }

                //backward computation
                for (int l = numWeight; l > 0; l--) {
                    if (l == numWeight) {
                        for (int k = 0; k < layer[l]; k++) {
                            double d = aN[l - 1].get(0, k);
                            d = d * (1 - d) * errorData.get(0, k);
                            dN[l - 1].set(0, k, d);
                        }
                    } else {
                        for (int k = 0; k < layer[l]; k++) {
                            Matrix ones = new Matrix(aN[l - 1].getRowDimension(),
                                    aN[l - 1].getColumnDimension(), 1);
                            ones = ones.minus(aN[l - 1]);

                            Matrix x = aN[l - 1].times(ones.transpose());
                            x = x.times(dN[l]);
                            Matrix y = weight[l].getMatrix(k, k, 0,
                                    weight[l].getColumnDimension() - 1);
                            x = x.times(y.transpose());
                            double d = x.get(0, 0);
                            dN[l - 1].set(0, k, d);
                        }
                    }
                    Matrix dW = new Matrix(layer[l - 1], layer[l]);
                    for (int i = 0; i < layer[l - 1]; i++) {
                        for (int j = 0; j < layer[l]; j++) {
                            double d;
                            if (l > 1) {
                                d = lR * dN[l - 1].get(0, j) * aN[l - 2].get(0, i);
                            } else {
                                d = lR * dN[l - 1].get(0, j) * cpTrain.get(0, i);
                            }
                            dW.set(i, j, d);
                        }
                        weight[l - 1] = weight[l - 1].plus(dW);
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
                flexibleMAE = newMAE;
                weightFlexible = weight.clone();
                System.out.println("Flexible MAE = " + flexibleMAE);
            }

            totalMAE = trainMAE[epoch];
            System.out.println("epochMAE = " + epochTrainMAE + ", MAE = " + totalMAE);
            epoch++;
        }
        for (int i = 0; i < input.length; i++) {
            Matrix x = new Matrix(test(input[0], 0), 1);
            threshold = threshold.plus(x);
        }

        for (int i = 0; i < numOutput; i++) {
            threshold.set(0, i, threshold.get(0, i) / input.length);
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

    public final void setMaxEpoch(int maxEpoch) {
        this.maxEpoch = maxEpoch;
    }

    public double getlR() {
        return lR;
    }

    public final void setlR(double lR) {
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
