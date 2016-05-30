/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn.testing;

import cnn.*;
import java.util.Random;

/**
 *
 * @author dee
 */
public class Testing {

    public double[][] data;
    public double[][] labels;

    public static void main(String[] args) {
        Testing t = new Testing();
        t.test2();
    }

    public void test2() {
        Options[] defs = new Options[3];
        defs[0] = new Options();
        defs[0].put("type", "input");
        defs[0].put("out_sx", 1);
        defs[0].put("out_sy", 1);
        defs[0].put("out_depth", 2);

        defs[1] = new Options();
        defs[1].put("type", "fc");
        defs[1].put("num_neurons", 20);
        defs[1].put("activation", "relu");

        defs[2] = new Options();
        defs[2].put("type", "softmax");
        defs[2].put("num_neurons", 10);

        System.out.println(defs.length);
        Net net = new Net();
        net.makeLayers(defs);

        double[] sx = {0.3, -0.5};
        Vol x = new Vol(sx, 0);
        Vol prob = net.forward(x);

        System.out.println("probability x is class 0 = " + prob.w[0]);

        Options opt = new Options();
        opt.put("learning_rate", 0.01);
        opt.put("l2_decay", 0.001);

        Trainers trainer = new Trainers(net, opt);
        double[] y = {0};
        trainer.train(x, y);
        Vol prob2 = net.forward(x);
        System.out.println("probability x is class 0 = " + prob2.w[0]);

    }

    public void test1() {
        Options[] defs = new Options[4];
        defs[0].put("type", "input");
        defs[0].put("out_sx", 1);
        defs[0].put("out_sy", 1);
        defs[0].put("out_depth", 1);

        defs[1].put("type", "fc");
        defs[1].put("num_neurons", 20);
        defs[1].put("activation", "relu");

        defs[2].put("type", "fc");
        defs[2].put("num_neurons", 20);
        defs[2].put("activation", "sigmoid");

        defs[3].put("type", "regression");
        defs[3].put("num_neurons", 1);

        Net net = new Net();
        net.makeLayers(defs);

        Options opt = new Options();
        opt.put("learning_rate", 0.01);
        opt.put("momentum", 0.0);
        opt.put("batch_size", 1);
        opt.put("l2_decay", 0.001);
        Trainers trainer = new Trainers(net, opt);
    }

    public void regen_data() {
        Random r = new Random();
        int N = 20;
        data = new double[N][1];
        labels = new double[N][1];
        for (int i = 0; i < N; i++) {
            double x = r.nextDouble() * 10 - 5;
            data[i][0] = x;
            labels[i][0] = x * Math.sin(x);
        }
    }

}
