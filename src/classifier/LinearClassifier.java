/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author dee
 */
public class LinearClassifier {

    // data classifier properties
    private double[][] data = {
        {0.5, 0.4},
        {0.8, 0.3},
        {0.3, 0.8},
        {-0.4, 0.3},
        {-0.3, 0.7},
        {-0.7, 0.2},
        {0.7, -0.4},
        {0.5, -0.6},
        {-0.4, -0.5},};
    private int[] labels = {
        0, 0, 0, 1, 1, 1, 2, 2, 2
    };
    private double[][] scores;
    private double[] loss;
    private int numClass = 3;

    // visualization properties
    private int height = 400;
    private int width = 400;
    private int ss = 200;
    private Color[] classColor = {
        Color.blue, Color.green, Color.red
    };

    // classifier process
    private double[] weight = {1.0, 2.0, 2, -4, 3, -1};
    private double[] bias = {0.0, 0.5, -0.5};
    private final double regC = 0.1;
    private double[] gradW = new double[weight.length];
    private double[] gradB = new double[bias.length];

    public double[][] getData() {
        return data;
    }

    public double getData(int i, int j) {
        return data[i][j];
    }

    public void setData(double[][] data) {
        this.data = data;
        labels = null;
        numClass = -1;
    }

    public void setData(int i, int j, double data) {
        this.data[i][j] = data;
    }

    public int[] getLabels() {
        return labels;
    }

    public int getLabels(int i) {
        return labels[i];
    }

    public void setLabels(int[] labels) {
        if (labels.length != data.length) {
            throw new IllegalStateException("wrong number of labels");
        }
        this.labels = labels;
    }

    public int getNumClass() {
        return numClass;
    }

    public void setNumClass(int numClass) {
        this.numClass = numClass;
        classColor = new Color[numClass];
        Random rand = new Random();
        for (Color c : classColor) {
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            c = new Color(r, g, b);
        }
    }

    public void randomBias() {
        bias = new double[numClass];
        gradB = new double[bias.length];
        Random r = new Random();
        double max = 1.5, min = -1.5;
        for (int i = 0; i < bias.length; i++) {
            bias[i] = r.nextDouble() * (max - min) + min;
        }
    }

    public void randomWeight() {
        weight = new double[data[0].length * numClass];
        gradW = new double[weight.length];
        Random r = new Random();
        double max = 10, min = -10;
        for (int i = 0; i < weight.length; i++) {
            weight[i] = r.nextDouble() * (max - min) + min;
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getSs() {
        return ss;
    }

    public void setSs(int ss) {
        this.ss = ss;
    }

    public Color[] getClassColor() {
        return classColor;
    }

    public Color getDataColor(int i) {
        return classColor[i];
    }

    public void setClassColor(Color[] classColor) {
        this.classColor = classColor;
    }

    public double getWeight(int i) {
        return weight[i];
    }

    public double[] getWeight() {
        return weight;
    }

    public void setWeight(double[] weight) {
        this.weight = weight;
    }

    public void setWeight(int i, double W) {
        this.weight[i] = W;
    }

    public double[] getBias() {
        return bias;
    }

    public double getBias(int i) {
        return bias[i];
    }

    public void setBias(double[] bias) {
        this.bias = bias;
    }

    public void setBias(int i, double b) {
        this.bias[i] = b;
    }

    public double[][] getScores() {
        return scores;
    }

    public void setScores(double[][] scores) {
        this.scores = scores;
    }

    public double getScores(int i, int j) {
        return scores[i][j];
    }

    public void setScores(int i, int j, double s) {
        scores[i][j] = s;
    }

    public double[] getLoss() {
        return loss;
    }

    public void setLoss(double[] loss) {
        this.loss = loss;
    }

    public double getLoss(int i) {
        return loss[i];
    }

    public void setLoss(double loss, int i) {
        this.loss[i] = loss;
    }

    public double calculateScore(int pil) {
        int numData = data.length;
        scores = new double[numData][numClass];
        loss = new double[numData];
        double totalLoss = 0;
        double costLoss = 0;
        double regLoss = 0;

        for (int i = 0; i < numData; i++) {
            int lb = labels[i];
            double[] s = new double[numClass];
            double maxj = -1;
            double maxjScore = -999999;
            for (int j = 0; j < numClass; j++) {
                s[j] = weight[j * 2] * data[i][0] + weight[j * 2 + 1] * data[i][1] + bias[j];
                if (s[j] > maxjScore && j != lb) {
                    maxj = j;
                    maxjScore = s[j];
                }
            }
            scores[i] = s;
//            int pil = 0;
            double[] p = new double[numClass];
            if (pil == 0) { // softmax
                double esum = 0;
                for (int j = 0; j < p.length; j++) {
                    p[j] = Math.exp(s[j]);
                    esum += p[j];
                }
                for (int j = 0; j < p.length; j++) {
                    p[j] /= esum;
                }

            }
            double lossi = 0;
            for (int j = 0; j < numClass; j++) {
                if (pil == 0) {//   softmax
                    if (lb == j) {
                        lossi += -Math.log(p[j]);
                        gradW[j * 2] += (p[j] - 1) * data[i][0];
                        gradW[j * 2 + 1] += (p[j] - 1) * data[i][1];
                        gradB[j] += (p[j] - 1);
                    } else {
                        gradW[j * 2] += (p[j]) * data[i][0];
                        gradW[j * 2 + 1] += (p[j]) * data[i][1];
                        gradB[j] += (p[j]);
                    }
                }

                if (pil == 1) { // weston watkins
                    if (lb == j) {
                        continue;
                    }
                    double lossij = Math.max(0, s[j] - s[lb] + 1);
                    if (lossij > 0) {
                        lossi += lossij;
                        gradW[j * 2] += data[i][0];
                        gradW[j * 2 + 1] += data[i][1];
                        gradB[j] += 1;
                        gradW[lb * 2] -= data[i][0];
                        gradW[lb * 2 + 1] -= data[i][1];
                        gradB[lb] -= 1;
                    }
                }

                if (pil == 2) { // ova
                    double yij = (lb == j ? 1 : -1);
                    double lossij = Math.max(0, 1 - yij * s[j]);
                    if (lossij > 0) {
                        lossi += lossij;
                        gradW[j * 2] += -yij * data[i][0];
                        gradW[j * 2 + 1] += -yij * data[i][1];
                        gradB[j] += -yij;
                    }
                }

                if (pil == 3) {    // structured svm
                    if (j == maxj) {
                        double lossij = s[j] - s[lb] + 1;
                        if (lossij > 0) {
                            lossij += lossij;
                            gradW[j * 2] += data[i][0];
                            gradW[j * 2 + 1] += data[i][1];
                            gradB[j] += 1;
                            gradW[lb * 2] -= data[i][0];
                            gradW[lb * 2 + 1] -= data[i][1];
                            gradB[lb] -= 1;
                        }
                    }
                }
            }
            loss[i] = lossi;
            costLoss += lossi;
        }
        costLoss /= numData;
        for (int i = 0; i < gradW.length; i++) {
            gradW[i] /= numData;
        }
        for (int i = 0; i < gradB.length; i++) {
            gradB[i] /= numData;
        }

        regLoss = 0;
        for (int i = 0; i < weight.length; i++) {
            regLoss += regC * weight[i] * weight[i];
            gradW[i] += 0.5 * regC * weight[i];
        }
        totalLoss = costLoss + regLoss;
        return totalLoss;
    }

    public double lostFast() {
        double loss = 0;
        int numData = data.length;
        for (int i = 0; i < numData; i++) {
            double[] s = new double[numClass];
            for (int j = 0; j < s.length; j++) {
                s[j] = weight[j * 2] * data[i][0] + weight[j * 2 + 1] * data[i][1] + bias[j];
            }
            int lb = labels[i];
            double lossi = 0;
            for (int j = 0; j < numClass; j++) {
                if (lb == j) {
                    continue;
                }
                double lossij = Math.max(0.0, s[j] - s[lb] + 1);
                if (lossij > 0) {
                    lossi += lossij;
                }
            }
            loss += lossi;
        }
        for (int i = 0; i < weight.length; i++) {
            loss += regC * weight[i] * weight[i];
        }
        return loss;
    }

    public void step(double learningRate) {
        for (int i = 0; i < weight.length; i++) {
            weight[i] += -learningRate * gradW[i];
        }
        for (int i = 0; i < bias.length; i++) {
            bias[i] += -learningRate * gradB[i];
        }
    }

    public int getNumData() {
        return data.length;
    }
}
