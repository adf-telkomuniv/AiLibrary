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
        int N = 20;
        Options[] defs = new Options[4];
        defs[0] = new Options();
        defs[0].put("type", "input");
        defs[0].put("out_sx", 1);
        defs[0].put("out_sy", 1);
        defs[0].put("out_depth", 1);

        defs[1] = new Options();
        defs[1].put("type", "fc");
        defs[1].put("num_neurons", 20);
        defs[1].put("activation", "relu");

        defs[2] = new Options();
        defs[2].put("type", "fc");
        defs[2].put("num_neurons", 20);
        defs[2].put("activation", "sigmoid");

        defs[3] = new Options();
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

        regen_data(N);

        int max_iter = 1000;
        Vol netx = new Vol(1, 1, 1, 0);
        double avloss = 0.0;
        for (int iters = 0; iters < max_iter; iters++) {
            System.out.println("iter=" + iters);
            for (int ix = 0; ix < N; ix++) {
                netx.w = data[ix];
                Options stats = trainer.train(netx, labels[ix]);
                double loss = (double) stats.getOpt("loss");
                avloss += loss;
            }
        }
        avloss /= N * max_iter;
        System.out.println("avloss = " + avloss);

    }

    public void regen_data(int N) {
        Random r = new Random();
        data = new double[N][1];
        labels = new double[N][1];
        for (int i = 0; i < N; i++) {
            double x = r.nextDouble() * 10 - 5;
            data[i][0] = x;
            labels[i][0] = x * Math.sin(x);
        }
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i][0] + " " + labels[i][0]);

        }
    }

}
