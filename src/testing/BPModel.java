/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import Jama.Matrix;
import java.util.Random;

/**
 *
 * @author dee
 */
public class BPModel {

//    private double input[][];
    private double layer[][][];
    private double layerFlexible[][][];
    private double layerFix[][][];
    private double aN[][];
    private double dN[][];
//    private double target[][];
    private double lR;
    private int maxEpoch;
    private double minMAE;
    private int[] numNeuron;
    private int numLayer;
    private int numDim;

    public BPModel(int[] numNeuron) {
        this.numNeuron = numNeuron;
        this.numLayer = numNeuron.length + 1;
        lR = 0.01;
        maxEpoch = 500;
        minMAE = 0.00001;
    }

    public BPModel(int[] numNeuron, double lR) {
        this(numNeuron);
        this.setlR(lR);
    }

    public BPModel(int[] numNeuron, int maxEpoch) {
        this(numNeuron);
        this.setMaxEpoch(maxEpoch);
    }

    public BPModel(int[] numNeuron, double lR, int maxEpoch) {
        this(numNeuron);
        this.setMaxEpoch(maxEpoch);
        this.setlR(lR);
    }

    public void initializeLayer(int numOutput) {
        Random r = new Random();
        aN = new double[numLayer][];
        dN = new double[numLayer][];
        for (int i = 0; i < numLayer - 1; i++) {
            aN[i] = new double[numNeuron[i]];
            dN[i] = new double[numNeuron[i]];
        }
        aN[numLayer - 1] = new double[numOutput];
        dN[numLayer - 1] = new double[numOutput];

        layer = new double[numLayer][][];
        layer[0] = new double[numNeuron[0]][numDim];
        for (int i = 1; i < numLayer - 1; i++) {
            layer[i] = new double[numNeuron[i]][numNeuron[i - 1]];
        }
        layer[numLayer - 1] = new double[numOutput][numNeuron[numLayer - 2]];

        for (double[][] l : layer) {
            for (double[] i : l) {
                for (int j = 0; j < i.length; j++) {
                    i[j] = (r.nextDouble() * 2 - 1) / 10;
                }
            }
        }
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
//        this.input = input;
//        this.target = target;
        initializeLayer(target[0].length);

        int epoch = 0;
        double epochTrainMAE = 1000000;
        double totalMAE = 1000000;
        double[] trainMAE = new double[maxEpoch];
        double[] valMAE = new double[maxEpoch];

        double[] errorData;// = new double[numNeuron[numNeuron.length - 1]];
        while ((epoch < maxEpoch) && (epochTrainMAE > minMAE)) {
            System.out.println("epoch = " + epoch);
            epochTrainMAE = 0;

            // forward computation
            for (int p = 0; p < input.length; p++) {
//                System.out.println("p = " + p);
                Matrix cpTrain = new Matrix(input[p], 1);
                Matrix ctTrain = new Matrix(target[p], 1);

                for (int l = 0; l < numLayer; l++) {
                    for (int i = 0; i < aN[l].length; i++) {
                        Matrix w1 = new Matrix(layer[l][i], 1);
                        Matrix v;
                        if (l == 0) {
                            v = cpTrain.times(w1.transpose());
                        } else {
                            v = (new Matrix(aN[l - 1], 1)).times(w1.transpose());
                        }
                        double tv = v.getArray()[0][0];
                        aN[l][i] = 1 / (1 + Math.exp(-tv));
                    }
                    cpTrain = new Matrix(aN[l], 1);
                }

                errorData = ctTrain.minus(cpTrain).getArray()[0];
                for (int i = 0; i < errorData.length; i++) {
                    epochTrainMAE += Math.abs(errorData[i]);
                }
//                System.out.println("epoch train mae = " + epochTrainMAE);

                //backward computation
//            double[][] dT = new double[numLayer][];
                for (int l = numLayer - 1; l >= 0; l--) {
//                dT[l] = new double[dN[l].length];
                    if (l == numLayer - 1) {
                        for (int i = 0; i < dN[l].length; i++) {
                            dN[l][i] = aN[l][i] * (1 - aN[l][i]) * errorData[i];
                        }
                    } else {
                        for (int i = 0; i < layer[l + 1].length; i++) {
                            Matrix aNt = new Matrix(aN[l], 1);
                            Matrix ones = new Matrix(1, aN[l].length, 1);
                            Matrix ly0 = new Matrix(layer[l + 1][i], 1);
                            Matrix dT0 = new Matrix(dN[l], 1);
                            ones = ones.minus(aNt).transpose();
                            Matrix dT1 = aNt.times(ones);
                            dT1 = dT1.times(dT0);

                            dT1 = dT1.times(ly0.transpose());
                            dN[l][i] = dT1.getArray()[0][0];
                        }
                    }

                    for (int j = 0; j < layer[l].length; j++) {
                        double[][] dW = new double[layer[l][j].length][layer[l].length];
                        for (int i = 0; i < layer[l][j].length; i++) {
                            if (l > 0) {
//                                System.out.println(i + " " + j);
//                                System.out.println(dN[l][j]);
//                                System.out.println(layer[l][j][i]);
                                layer[l][j][i] += lR * dN[l][j] * aN[l - 1][i];
                            } else {
                                layer[l][j][i] += lR * dN[l][j] * input[p][i];
                            }
                        }
                    }

//                    int num = (l > 0 ? layer[l].length : numDim);
////                    int num2 = (l == numLayer - 1 ? target[0].length : numNeuron[l]);
////                    System.out.println("num= " + num);
//                    for (int i = 0; i < num; i++) {
////                        double[][] dW = new double[num][numNeuron[l]];
//                        for (int j = 0; j < layer[l][i].length; j++) {
//                            if (l > 0) {
//                                System.out.println(i + " " + j);
//                                System.out.println(dN[l][j]);
//                                System.out.println(layer[l][i][j]);
//                                layer[l][i][j] += lR * dN[l][j] * aN[l - 1][i];
//                            } else {
//                                layer[l][i][j] += lR * dN[l][j] * input[p][i];
//                            }
//                        }
//                    }
                }
            }
            trainMAE[epoch] = epochTrainMAE / input.length;

            // validation
            double epochValMAE = 0;
            for (int p = 0; p < inputVal.length; p++) {
                Matrix cpVal = new Matrix(inputVal[p], 1);
                Matrix ctVal = new Matrix(targetVal[p], 1);

                for (int l = 0; l < numLayer; l++) {
                    for (int i = 0; i < aN[l].length; i++) {
                        Matrix w1 = new Matrix(layer[l][i], 1);
                        Matrix v;
                        if (l == 0) {
                            v = cpVal.times(w1.transpose());
                        } else {
                            v = (new Matrix(aN[l - 1], 1)).times(w1.transpose());
                        }
                        double tv = v.getArray()[0][0];
                        aN[l][i] = 1 / (1 + Math.exp(-tv));
                    }
                    cpVal = new Matrix(aN[l], 1);
                }

                errorData = ctVal.minus(cpVal).getArray()[0];
                for (int i = 0; i < errorData.length; i++) {
                    epochValMAE += Math.abs(errorData[i]);
                }
            }
            valMAE[epoch] = epochValMAE / inputVal.length;
            double newMAE = (trainMAE[epoch] * input.length + valMAE[epoch] * inputVal.length)
                    / (input.length + inputVal.length);
            if (newMAE < totalMAE) {
                totalMAE = newMAE;
                layerFlexible = layer.clone();
            } else {
                layerFix = layer.clone();
            }
            System.out.println("MAE = " + totalMAE);
            epoch++;
        }
    }

    public double[] test(double[] input) {
        Matrix cp = new Matrix(input, 1);
        for (int l = 0; l < numLayer; l++) {
            for (int i = 0; i < aN[l].length; i++) {
                Matrix w1 = new Matrix(layer[l][i], 1);
                Matrix v;
                if (l == 0) {
                    v = cp.times(w1.transpose());
                } else {
                    v = (new Matrix(aN[l - 1], 1)).times(w1.transpose());
                }
                double tv = v.getArray()[0][0];
                aN[l][i] = 1 / (1 + Math.exp(-tv));
            }
            cp = new Matrix(aN[l], 1);
        }
        return cp.getArray()[0];
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

    public int[] getNumNeuron() {
        return numNeuron;
    }

    public int getNumLayer() {
        return numLayer;
    }

    public int getNumDim() {
        return numDim;
    }

}
